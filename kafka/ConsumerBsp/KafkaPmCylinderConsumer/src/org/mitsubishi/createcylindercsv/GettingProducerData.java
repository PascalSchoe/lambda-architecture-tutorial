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

public class GettingProducerData {
	ArrayList<ProducerDTO> listProducerTemp = new ArrayList<ProducerDTO>();
	ArrayList<ProducerDTO> listProducer = new ArrayList<ProducerDTO>();

	public GettingProducerData() {
		try {
			gettingData();
			createProducerList();
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
				ProducerDTO dto = new ProducerDTO(id + "", Methods.cellContent(row.getCell(3)));
				listProducerTemp.add(dto);
				id++;
			} catch (Exception e) {
				e.printStackTrace();
			}
			wb.close();
		}
	}

	private void createProducerList() {
		int id = 1;
		for (ProducerDTO prodDto : listProducerTemp) {
			if (isNew(prodDto)) {
				ProducerDTO dto = new ProducerDTO(id + "", prodDto.getProducer());
				listProducer.add(dto);
				id++;
			}
		}
	}

	private boolean isNew(ProducerDTO qualDto) {
		boolean temp = true;
		if (!listProducer.isEmpty()) {
			for (ProducerDTO dto : listProducer) {
				if (dto.getProducer().equals(qualDto.getProducer())) {
					temp = false;
					break;
				}
			}
		}
		return temp;
	}
	
	public void getProducer() {
		try {
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_PRODUCER));
			for(ProducerDTO dto : listProducer) {
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("producerId", Integer.parseInt(dto.getId()));
				e1.put("producerName", dto.getProducer());
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ProducerDTO> getListProducer() {
		return listProducer;
	}
}
