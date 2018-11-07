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

public class GetProcedure {

	public static void getProd() {
		try {
			// csv wird eingelesen CYLINDER_PROCEDURE = "C:/DataStreaming/PM/Walzen/Walzen_Order.csv"
			CSVReader reader = new CSVReader(new FileReader(ConstantClass.CYLINDER_PROCEDURE));
			String [] nextLine;
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			// Schema wird eingelesen
			Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_PROCEDURE));
			while ((nextLine = reader.readNext()) != null) {
				//Daten werden eingelesen
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("originId", Integer.parseInt(nextLine[0]));
				e1.put("cylinderNr", Integer.parseInt(nextLine[1]));
				e1.put("buildinDate", nextLine[2]);
				e1.put("diameter(mm)", Double.parseDouble(nextLine[3]));
				e1.put("runtime", nextLine[4]);
				e1.put("quality", Integer.parseInt(nextLine[5]));
				e1.put("evaluation", nextLine[6]);
				e1.put("buildoutDate", nextLine[7]);
				e1.put("buildoutReason", nextLine[8]);
				recordList.add(e1);
			}
			DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
			DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
			dataFileWriter.create(schema, new File(ConstantClass.SAVE_PROCEDURE));
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
