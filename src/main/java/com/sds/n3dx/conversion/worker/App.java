package com.sds.n3dx.conversion.worker;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class App implements CommandLineRunner {
	
	@Value("app.kafka.consumer.beanName")
	private String consumerName;
	
	@Value("app.kafka.consumer.listen.queueName")
	private String listenTopicName;
	
	@Value("app.kafka.consumer.client.groupName")
	private String groupName;
	
	@Autowired
	private DrawingConverter drawingConverter;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private AnnotationConfigApplicationContext childContext;
	
//	private Map<String, AnnotationConfigApplicationContext> consumerMap;

	public static void main( String[] args ) {
		log.info("Drawing conversion worker application is starting.");
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("App.run(String... args) is being called...");
		registerKafkerConsumer();
		registerShutdownHook();
	}
	
	private void registerKafkerConsumer() {
		childContext = new AnnotationConfigApplicationContext();
		childContext.setParent(applicationContext);
        Properties props = new Properties();
        props.setProperty("topic", listenTopicName);
        props.setProperty("groupId", groupName);
        props.setProperty("consumerName", consumerName);
        PropertiesPropertySource pps = new PropertiesPropertySource("listenerProps", props);
        childContext.getEnvironment().getPropertySources().addLast(pps);
        childContext.registerBean(
        		consumerName, 
        		ConversionRequestKafkaConsumer.class, 
        		drawingConverter, 
        		new AppTerminationSignalListener() {
					@Override
					public void terminateApplication() {
						log.info("Drawing conversion worker application is being stopped with normal status.");
						System.exit(0);
					}
				}
        	);
        childContext.refresh();
        kafkaListenerEndpointRegistry.setApplicationContext(applicationContext);
//        consumerMap.put(consumerName, childContext);
	}
	
	private void registerShutdownHook() {
		log.info("ShutdownHook is being registered.");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info("Drawing conversion worker application is being destoyed.");
				log.info("Resources including connection for kafka would be released.");
				kafkaListenerEndpointRegistry.getListenerContainer(consumerName).destroy();
				childContext.close();
			}
		});
	}
	
//	private void testCommandExecution()  {
//		String line="D:\\Programs\\MobaXterm_Personal_22.1\\MobaXterm_Personal_22.1.exe";
//		HoopsConverterOptions options=new HoopsConverterOptions();
////		String line="git --version";
//		CommandLine commandLine=CommandLine.parse(line);
//		for (final Map.Entry<String, String> option : options.toMap().entrySet()) {
//	        commandLine.addArgument("--" + option.getKey(), false);
//	        commandLine.addArgument(option.getValue(), false);
//		}
//		
//		DefaultExecutor executor=new DefaultExecutor();
//		int exitValue=1;
//		try {
//			ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
//			executor.setWatchdog(watchdog);
//			boolean append=true;
//			OutputStream fileout = new FileOutputStream(new File("K:\\My files\\result.txt"), append);
//			OutputStream erroout = new FileOutputStream(new File("K:\\My files\\error.txt"), append);
//            
//			executor.setStreamHandler(new ExecuteStreamHandler() {
//
//				@Override
//				public void setProcessInputStream(OutputStream os) throws IOException {
//					System.out.println("ExecuteStreamHandler.setProcessInputStream(OutputStream os) called.");					
//				}
//
//				@Override
//				public void setProcessErrorStream(InputStream is) throws IOException {
//					System.out.println("ExecuteStreamHandler.setProcessErrorStream(InputStream is) called.");
//					erroout.write(is.readAllBytes());
//					erroout.flush();
//				}
//
//				@Override
//				public void setProcessOutputStream(InputStream is) throws IOException {
//					System.out.println("ExecuteStreamHandler.setProcessOutputStream(InputStream is) called.");
//					fileout.write(is.readAllBytes());
//					fileout.flush();
//				}
//
//				@Override
//				public void start() throws IOException {
//					log.debug("ExecuteStreamHandler.start() called.");
//				}
//
//				@Override
//				public void stop() throws IOException {
//					log.debug("ExecuteStreamHandler.stop() called.");
//				}
//			});
//			
//			exitValue=executor.execute(commandLine);
//			System.out.println("RETURN VALUE NORMAL =>" + exitValue);
//			fileout.close();
//			erroout.close();
//			
//			System.exit(0);
//		} catch (Exception e) {
//			System.out.println("RETURN VALUE ABNORM=>" + exitValue);
//			e.printStackTrace();
//			System.exit(0);
//		}
//	}
}
