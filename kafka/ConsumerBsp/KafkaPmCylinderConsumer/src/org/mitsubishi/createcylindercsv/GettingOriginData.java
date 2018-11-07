package org.mitsubishi.createcylindercsv;

import java.io.*;
import java.util.ArrayList;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.mitsubishi.cylinder.ConstantClass;

public class GettingOriginData {

	ArrayList<OriginDTO> listRollerOrigin = new ArrayList<OriginDTO>();

	public GettingOriginData() {
		try {
			gettingData();
			getOrigin();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void gettingData() throws IOException {
		FileInputStream fis = new FileInputStream(ConstantClass.PATH_EXCELROLLER);
		Workbook wb = new HSSFWorkbook(fis);
		int id = 1;
		for (int i = 0; i < 17; i++) {
			Sheet sheet = wb.getSheetAt(i + 2);
			Row row = sheet.getRow(0);
			try {
				if (!row.getCell(0).getStringCellValue().equals("")) {
					OriginDTO dto = new OriginDTO();
					dto.setOriginId(Integer.toString(id));
					dto.setOriginName(row.getCell(0).getStringCellValue());
					dto.setActiveRollId(Methods.cellContent(sheet.getRow(9).getCell(0)));
					listRollerOrigin.add(dto);
					id++;
				}
			} catch (Exception e) {
				System.out.println("Spalte leer!");
			}
			wb.close();
		}
	}
	
	private void getOrigin() {
		try {
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_ORIGIN));
			for(OriginDTO dto : listRollerOrigin) {
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("originId", Integer.parseInt(dto.getOriginId()));
				e1.put("originName", dto.getOriginName());
				recordList.add(e1);
			}
			DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
			DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
			dataFileWriter.create(schema, new File(ConstantClass.SAVE_ORIGIN));
			for (GenericRecord rec : recordList) {
				dataFileWriter.append(rec);
			}
			dataFileWriter.close();
			System.out.println("data successfully serialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<OriginDTO> getOriginList() {
		return listRollerOrigin;
	}
}
