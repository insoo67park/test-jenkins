package com.sds.n3dx.conversion.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component(value="transport.message.kafka")
public class KafkaTransport {
	
	@Value("app.kafka.producer.publish.queueName")
	private String responseQueueName;

	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	public KafkaTransport(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate=kafkaTemplate;
	}

	public void send(String json) {
		kafkaTemplate.send(responseQueueName, json);
	}
}
