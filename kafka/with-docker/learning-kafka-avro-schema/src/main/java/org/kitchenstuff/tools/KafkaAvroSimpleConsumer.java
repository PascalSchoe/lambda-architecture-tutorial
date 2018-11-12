package org.kitchenstuff.tools;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaAvroSimpleConsumer {
    private static final String TOPIC = "new-blender";
    private static final String BOOTSTRAP_SERVERS = "http://127.0.0.1:9092";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "BlenderConsumer");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());

        props.setProperty(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");
        props.setProperty(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

        KafkaConsumer<String, Blender> kafkaConsumer = new KafkaConsumer<String, Blender>(props);
        kafkaConsumer.subscribe(Arrays.asList(TOPIC));

        while (true){
            ConsumerRecords<String, Blender> records = kafkaConsumer.poll(100);
            for(ConsumerRecord<String, Blender> record : records){
                Blender b = record.value();
                System.out.printf("Record:%nModell-->%s%nHersteller-->%s%nAnzahl Umdrehungen-->%s%n%n", b.getModel(), b.getMake(), b.getRotations());
            }

            kafkaConsumer.commitSync();
        }

    }
}
