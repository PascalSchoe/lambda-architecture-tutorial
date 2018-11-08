package org.mitsubishi.kafkaproducer;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.IntStream;

import org.mitsubishi.pm1.fl.daa.Pressure;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;

public class TestProducer {
	 private static Producer<Long, Pressure> createProducer() {
	        Properties props = new Properties();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	        props.put(ProducerConfig.CLIENT_ID_CONFIG, "AvroProducer");
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	                LongSerializer.class.getName());

	        // Configure the KafkaAvroSerializer.
	       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	                KafkaAvroSerializer.class.getName());

	        // Schema Registry location.
	        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
	                "http://tron");

	        return new KafkaProducer<>(props);
	    }

	    private final static String TOPIC = "new-employees";

	    public static void main(String... args) {

	        Producer<Long, GenericRecord> producer = createProducer();

//			try {
//				ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
//				// Schema wird eingelesen
				Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_PROCEDURE));
//				for(OrderDTO dto : listOrder) {
//					//Daten werden eingelesen
					GenericRecord e1 = new GenericData.Record(schema);
//					e1.put("originId", Integer.parseInt(dto.getOriginId()));
//					e1.put("cylinderNr", Integer.parseInt(dto.getRollerNr()));
//					e1.put("buildinDate", dto.getBuildDate());
//					e1.put("diameter(mm)", Double.parseDouble(dto.getDiameter()));
//					e1.put("runtime", dto.getRollTime());
//					e1.put("quality", Integer.parseInt(dto.getEvaluation()));
//					e1.put("evaluation", dto.getComment());
//					e1.put("buildoutDate", dto.getDebuildDate());
//					e1.put("buildoutReason", dto.getDebuildReason());
//					recordList.add(e1);
//				}
//				DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
//				DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
//				dataFileWriter.create(schema, new File(ConstantClass.SAVE_PROCEDURE));
//				for (GenericRecord rec : recordList) {
//					dataFileWriter.append(rec);
//				}
//				dataFileWriter.close();
//				System.out.println("data successfully serialized");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
	        
	        IntStream.range(1, 100).forEach(index->{
	            producer.send(new ProducerRecord<>(TOPIC, 1L * index, e1));

	        });

	        producer.flush();
	        producer.close();
	    }
}
