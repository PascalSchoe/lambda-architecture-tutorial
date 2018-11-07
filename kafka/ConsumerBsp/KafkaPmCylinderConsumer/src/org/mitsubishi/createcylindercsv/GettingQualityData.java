package org.mitsubishi.createcylindercsv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mitsubishi.cylinder.ConstantClass;

public class GettingQualityData {
	ArrayList<QualityDTO> listQualityTemp = new ArrayList<QualityDTO>();
	ArrayList<QualityDTO> listQuality = new ArrayList<QualityDTO>();

	public GettingQualityData() {
		try {
			gettingData();
			createQualityList();
			getQuality();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gettingData() throws IOException {
		FileInputStream fis = new FileInputStream(ConstantClass.PATH_EXCELROLLER);
		Workbook wb = new HSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(1);
		int id = 1;
		for (int i = 3; i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			try {
				QualityDTO dto = new QualityDTO(id + "", Methods.cellContent(row.getCell(2)));
				listQualityTemp.add(dto);
				id++;
			} catch (Exception e) {
				System.out.println("Spalte leer!");
			}
			wb.close();
		}
	}

	private void createQualityList() {
		int id = 1;
		for (QualityDTO qualDto : listQualityTemp) {
			if (isNew(qualDto)) {
				QualityDTO dto = new QualityDTO(id + "", qualDto.getQuality());
				listQuality.add(dto);
				id++;
			}
		}
	}

	private boolean isNew(QualityDTO qualDto) {
		boolean temp = true;
		if (!listQuality.isEmpty()) {
			for (QualityDTO dto : listQuality) {
				if (dto.getQuality().equals(qualDto.getQuality())) {
					temp = false;
					break;
				}
			}
		}
		return temp;
	}
	
	public ArrayList<QualityDTO> getListQuality() {
		return listQuality;
	}
	
	private void getQuality() {
		try {
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_QUALITY));
			for(QualityDTO dto : listQuality){
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("qualityId", Integer.parseInt(dto.getId()));
				e1.put("qualityName", dto.getQuality());
				recordList.add(e1);
			}
			DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
			DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
			dataFileWriter.create(schema, new File(ConstantClass.SAVE_QUALITY));
			for (GenericRecord rec : recordList) {
				dataFileWriter.append(rec);
			}
			dataFileWriter.close();
			System.out.println("data successfully serialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
