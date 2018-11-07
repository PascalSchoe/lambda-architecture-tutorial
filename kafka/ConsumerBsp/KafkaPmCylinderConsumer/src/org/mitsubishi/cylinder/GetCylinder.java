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

public class GetCylinder {
	public static void getCylinder() {
		try {
			CSVReader reader = new CSVReader(new FileReader(ConstantClass.CYLINDER_ROLLER));
			String [] nextLine;
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_ROLLER));
			while ((nextLine = reader.readNext()) != null) {
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("originId", Integer.parseInt(nextLine[0]));
				e1.put("cylinderNr", Integer.parseInt(nextLine[1]));
				e1.put("preferenceType", Integer.parseInt(nextLine[2]));
				e1.put("producer", Integer.parseInt(nextLine[3]));
				e1.put("rammy", nextLine[4]);
				e1.put("hardness", nextLine[5]);
				e1.put("preferenceLength", Double.parseDouble(nextLine[6]));
				e1.put("bombage", nextLine[7]);
				e1.put("newDate", nextLine[8]);
				e1.put("maxO", nextLine[9]);
				e1.put("minO", nextLine[10]);
				recordList.add(e1);
			}
			DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
			DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
			dataFileWriter.create(schema, new File(ConstantClass.SAVE_ROLLER));
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
