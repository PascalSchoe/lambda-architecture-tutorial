package org.mitsubishi.cylinder;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;

import com.opencsv.CSVReader;

public class GetProducer {
	public static void getProducer() {
		try {
			CSVReader reader = new CSVReader(new FileReader(ConstantClass.CYLINDER_PRODUCER));
			String [] nextLine;
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_PRODUCER));
			while ((nextLine = reader.readNext()) != null) {
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("producerId", Integer.parseInt(nextLine[0]));
				e1.put("producerName", nextLine[1]);
				recordList.add(e1);
			}
			DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
			DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
			dataFileWriter.create(schema, new File(ConstantClass.SAVE_PRODUCER));
			for (GenericRecord rec : recordList) {
				dataFileWriter.append(rec);
			}
			dataFileWriter.close();
			System.out.println("data successfully serialized");
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
