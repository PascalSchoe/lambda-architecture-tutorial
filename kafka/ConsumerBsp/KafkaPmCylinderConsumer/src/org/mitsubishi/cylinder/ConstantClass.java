package org.mitsubishi.cylinder;

import java.util.Arrays;
import java.util.List;

public class ConstantClass {
	
	public static final String CYLINDER_PROCEDURE = "C:/DataStreaming/PM/Walzen/Walzen_Order.csv";
	public static final String CYLINDER_ROLLER = "C:/DataStreaming/PM/Walzen/Walzen_Roller.csv";
	public static final String CYLINDER_ORIGIN = "C:/DataStreaming/PM/Walzen/Walzen_Origin.csv";
	public static final String CYLINDER_PRODUCER = "C:/DataStreaming/PM/Walzen/Walzen_Producer.csv";
	public static final String CYLINDER_QUALITY = "C:/DataStreaming/PM/Walzen/Walzen_Quality.csv";
	
	public static final String SCHEMA_PROCEDURE = "C:/DataStreaming/AvroSchema/PmCylinderProcedure.avsc";
	public static final String SCHEMA_ROLLER = "C:/DataStreaming/AvroSchema/PmCylinder.avsc";
	public static final String SCHEMA_PRODUCER = "C:/DataStreaming/AvroSchema/PmCylinderProducer.avsc";
	public static final String SCHEMA_QUALITY = "C:/DataStreaming/AvroSchema/PmCylinderQuality.avsc";
	public static final String SCHEMA_ORIGIN = "C:/DataStreaming/AvroSchema/PmCylinderOrigin.avsc";
	
	public static final String SAVE_PROCEDURE = "/home/Hadoop//Machines/PM/PM1/Excel/input/cyliner_procedure.avro";
	public static final String SAVE_ROLLER = "/home/Hadoop//Machines/PM/PM1/Excel/input/cyliner_procedure.avro";
	public static final String SAVE_ORIGIN = "/home/Hadoop//Machines/PM/PM1/Excel/input/cyliner_procedure.avro";
	public static final String SAVE_PRODUCER = "/home/Hadoop//Machines/PM/PM1/Excel/input/cyliner_procedure.avro";
	public static final String SAVE_QUALITY = "/home/Hadoop//Machines/PM/PM1/Excel/input/cyliner_procedure.avro";
	
	public static final List<String> KAFKA_TOPIC = Arrays.asList("CylinderTopic");
	public static final String KAFKA_BROKERS = "localhost:9092,localhost:9093,localhost:9094";
}
