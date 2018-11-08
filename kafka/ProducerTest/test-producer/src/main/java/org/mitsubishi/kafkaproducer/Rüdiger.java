package org.mitsubishi.kafkaproducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class Rüdiger {

		private static final String TOPIC = "daa-pm1-pressure";
		
		public static void main(final String[] args){
			
			final Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "tron:9092");
			props.put(ProducerConfig.ACKS_CONFIG, "all");
			props.put(ProducerConfig.RETRIES_CONFIG, 0);
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
			props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://tron:8081");
			
			try (KafkaProducer<String, Pressure> producer = new KafkaProducer<String, Pressure>(props)) {
				for (int i = 0; i < 10; i++) {
					final String orderId = "id" + Long.toString(i);
					final Pressure pressure = new Pressure();
					final ProducerRecord<String, Pressure> record = new ProducerRecord<String, Pressure>(TOPIC, value);
					producer.send(record);
					Thread.sleep(1000L);
				}
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("BLABLABLA %s", TOPIC);
		}
}
