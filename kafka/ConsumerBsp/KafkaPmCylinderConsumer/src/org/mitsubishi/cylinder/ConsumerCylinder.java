package org.mitsubishi.cylinder;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.mitsubishi.createcylindercsv.GetCSV;

public class ConsumerCylinder {

	public static void main(String[] args) {
		subscribe();
	}

	private static void subscribe() {
		Consumer consumer = createConsumer();
		consumer.subscribe(ConstantClass.KAFKA_TOPIC);

		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord cr : records) {
				System.out.printf("offset = %d, key = %s, value %s%n", cr.offset(), cr.key(), cr.value());
			}
			GetCSV.getCsv();
			// Fehlt noch ein Wait o.ä. damit die CSV rechtzeitig erstellt sind
			start();
		}
	}

	private static Consumer<String, String> createConsumer() {
		Properties props = new Properties();

		props.put("bootstrap.servers", ConstantClass.KAFKA_BROKERS);
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

		return consumer;
	}

	private static void start() {
		GetProcedure.getProd();
		GetCylinder.getCylinder();
		GetOrigin.getOrigin();
		GetProducer.getProducer();
		GetQuality.getQuality();
	}

}
