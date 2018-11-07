package org.mitsubishi.TipsToHadoop;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;

public class GetTipsReference {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBNAME", "usrname", "pswd");
			Statement stmt = conn.createStatement();
			String query = "select columnname from tablename ;";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			Schema schema = new Schema.Parser().parse(new File("/home/Hadoop/Avro/schema/emp.avsc"));
			while (rs.next()) {
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("runcode", rs.getInt(0));
				e1.put("rollnr", rs.getInt(1));
				e1.put("ordecode", rs.getString(2));
				e1.put("time", rs.getString(3));
				recordList.add(e1);
			}
			DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
			DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
			dataFileWriter.create(schema, new File("/home/Hadoop/Avro_work/without_code_gen/mydata.txt"));
			for(GenericRecord rec : recordList){
				dataFileWriter.append(rec);
			}
			dataFileWriter.close();
			System.out.println("data successfully serialized");
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
