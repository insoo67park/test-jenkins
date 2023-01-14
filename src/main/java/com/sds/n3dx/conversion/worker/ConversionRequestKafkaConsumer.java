package com.sds.n3dx.conversion.worker;

import java.beans.PropertyChangeEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConversionRequestKafkaConsumer {
	
	@Autowired
	private KafkaTransport kafkaTransport;
	
	private DrawingConverter converter;
	
	private AppTerminationSignalListener appTerminator;
	
	public ConversionRequestKafkaConsumer(DrawingConverter converter, AppTerminationSignalListener appTerminator) {
		this.converter=converter;
		this.appTerminator=appTerminator;
	}

	@KafkaListener(id="${consumerName}", topics="${topic}", concurrency="1", groupId="${groupId}")
	public void listen(String json) {
		log.debug("ConversionRequestKafkaConsumer.listen(String json) is called.");
		log.debug("New conversion request message arrived[{}]", json);
		ConversionRequest req=parseMessage(json);
		ConversionWorkflowChain chain=buildWorkflowChain(req);
		ConversionResponse res=new ConversionResponse();
		doWorkflowChain(chain, req, res);
		
		// Worker는 K8S JOB으로 구동되며, Conversion Request 1건을 처리한 후에 Application과 JOB은 종료 되어야 함 
		appTerminator.terminateApplication();
	}
	
	private ConversionRequest parseMessage(String json) {
		log.debug("ConversionRequestKafkaConsumer.parseMessage(String json) is called.");
		return new Gson().fromJson(json, ConversionRequest.class);
	}
	
	private ConversionWorkflowChain buildWorkflowChain(ConversionRequest req) {
		log.debug("ConversionRequestKafkaConsumer.buildWorkflowChain(ConversionRequest req) is called.");
		ConversionActivityListener listener=new ConversionActivityListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				log.debug("ConversionActivityListener.propertyChange(PropertyChangeEvent evt) is called.");
				log.debug("Event=[{}]", evt.toString());
				kafkaTransport.send(null);
			}
		};
			
		ConversionWorkflowChain chain=null;
		switch(req.getConversionMode()) {
		case "MONOLITHIC":
			chain=buildMonolithicWorkflowChain(req, listener);
			break;
		case "SHATTERED":
			chain=buildShatteredWorkflowChain(req, listener);
			break;
		default:
			chain=buildShatteredWorkflowChain(req, listener);
		}
		return chain;
	}
	
	private ConversionWorkflowChain buildMonolithicWorkflowChain(ConversionRequest req, ConversionActivityListener listener) {
		ConversionActivity conversionCompletionActivity=new ConversionCompletionActivity("conversionCompletionActivity");
		conversionCompletionActivity.addPropertyChangeListener("status", listener);
		
		ConversionActivity masterScCreationActivity=new MasterScCreationActivity("masterScCreationActivity");
		masterScCreationActivity.setConverter(converter);
		masterScCreationActivity.addPropertyChangeListener("status", listener);

		ConversionActivity shatteredSymbolicLinkActivity=new ShatteredSymbolicLinkActivity("shatteredSymbolicLinkActivity");
		shatteredSymbolicLinkActivity.addPropertyChangeListener("status", listener);
		
		ConversionActivity thumbnailXmlExtractionActivity=new ThumbnailMasterXmlExtractionActivity("thumbnailXmlExtractionActivity");
		thumbnailXmlExtractionActivity.setConverter(converter);
		thumbnailXmlExtractionActivity.addPropertyChangeListener("status", listener);
		
		ConversionActivity drawingSlinkActivity=new DrawingSymbolicLinkActivity("drawingSlinkActivity");
		drawingSlinkActivity.addPropertyChangeListener("status", listener);
		
		ConversionActivity partConversionActivity=new PartConversionActivity("partConversionActivity");
		partConversionActivity.setConverter(converter);
		partConversionActivity.addPropertyChangeListener("status", listener);
		
		ConversionActivity tempSlinkActivity=new TempSymbolicLinkActivity("tempSlinkActivity");
		tempSlinkActivity.addPropertyChangeListener("status", listener);
		
		ConversionActivity startActivity=new ConversionStartActivity("startActivity");
		startActivity.addPropertyChangeListener("status", listener);
		
		masterScCreationActivity.setNext(conversionCompletionActivity);
		shatteredSymbolicLinkActivity.setNext(masterScCreationActivity);
		thumbnailXmlExtractionActivity.setNext(shatteredSymbolicLinkActivity);
		partConversionActivity.setNext(drawingSlinkActivity);
		tempSlinkActivity.setNext(partConversionActivity);
		startActivity.setNext(tempSlinkActivity);
		
		ConversionWorkflowChain chain=new ConversionWorkflowChain();
		chain.setNext(startActivity);
		
		return chain;
	}
	
	private ConversionWorkflowChain buildShatteredWorkflowChain(ConversionRequest req, ConversionActivityListener listener) {
		ConversionActivity conversionCompletionActivity=new ConversionCompletionActivity("conversionCompletionActivity");
		conversionCompletionActivity.addPropertyChangeListener("status", listener);

		ConversionActivity partConversionActivity=new PartConversionActivity("partConversionActivity");
		partConversionActivity.setConverter(converter);
		partConversionActivity.addPropertyChangeListener("status", listener);

		ThumbnailExtractionActivity thumbnailExtractionActivity=new ThumbnailExtractionActivity("thumbnailExtractionActivity");
		partConversionActivity.setConverter(converter);
		thumbnailExtractionActivity.addPropertyChangeListener("status", listener);
		
		ConversionActivity tempSlinkActivity=new TempSymbolicLinkActivity("tempSlinkActivity");
		tempSlinkActivity.addPropertyChangeListener("status", listener);
		
		ConversionActivity startActivity=new ConversionStartActivity("startActivity");
		startActivity.addPropertyChangeListener("status", listener);
		
		partConversionActivity.setNext(conversionCompletionActivity);
		thumbnailExtractionActivity.setNext(partConversionActivity);
		tempSlinkActivity.setNext(thumbnailExtractionActivity);
		startActivity.setNext(tempSlinkActivity);
		
		ConversionWorkflowChain chain=new ConversionWorkflowChain();
		chain.setNext(startActivity);
		
		return chain;
	}
	
	private void doWorkflowChain(ConversionWorkflowChain chain, ConversionRequest req, ConversionResponse res) {
		log.debug("ConversionRequestKafkaConsumer.doWorkflowChain(ConversionWorkflowChain chain, ConversionRequest req, ConversionResponse res) is called.");
		chain.doChain(req, res);
	}
}
