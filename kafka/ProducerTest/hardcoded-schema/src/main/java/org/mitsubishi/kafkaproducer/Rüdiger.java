package org.mitsubishi.kafkaproducer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class R�diger {

		private static final String TOPIC = "daa-pm1-pressure";
		
		public static void main(final String[] args){
			
			final Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
			props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
			String key = "key1";
			KafkaProducer producer = new KafkaProducer(props);
			try {
				Schema schema = new Schema.Parser().parse(new File("./daa-pm1-pressure.2.json"));
				GenericRecord avroRecord = new GenericData.Record(schema);
				avroRecord.put("timestamp", "test");
				avroRecord.put("currentPressureHeadbox", 13.37);
				avroRecord.put("differencePressure5TG", 17.71);
				ProducerRecord<Object, Object> record = new ProducerRecord<Object, Object>(TOPIC, key, avroRecord);
				System.out.println(producer.send(record).get());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.printf("BLABLABLA %s", TOPIC);
		}
}
