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

public class GettingCylinderData {

	ArrayList<CylinderDTO> listCylinder = new ArrayList<CylinderDTO>();
	ArrayList<ProducerDTO> listProducer = new ArrayList<ProducerDTO>();
	ArrayList<QualityDTO> listQuality = new ArrayList<QualityDTO>();
	ArrayList<OriginDTO> listCylinderOrigin = new ArrayList<OriginDTO>();

	public GettingCylinderData(ArrayList<ProducerDTO> listProducer, ArrayList<QualityDTO> listQuality,
			ArrayList<OriginDTO> listCylinderOrigin) {
		try {
			this.listProducer = listProducer;
			this.listQuality = listQuality;
			this.listCylinderOrigin = listCylinderOrigin;
			for (int i = 0; i < 17; i++) {
				gettingData(i);
				gettingMissingData();
			}
			getCylinder();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gettingData(int index) throws IOException {
		FileInputStream fis = new FileInputStream(ConstantClass.PATH_EXCELROLLER);
		Workbook wb = new HSSFWorkbook(fis);
		DataFormatter dataFormatter = new DataFormatter();
		Sheet sheet = wb.getSheetAt(index + 2);
		// for (int i = 0; i < sheet.getLastRowNum(); i++) {
		for (int i = 0; i < 6; i++) {
			Row row = sheet.getRow(i + 1);
			try {
				if (Methods.isRowEmpty(row, 3)) {
					break;
				} else {
					CylinderDTO dto = new CylinderDTO();
					dto.setOriginId((index + 1) + "");
					dto.setCylinderNr(Methods.cellContent(row.getCell(3)));
					if (dto.getCylinderNr().length() >= 1)
						dto.setCylinderNr(String.valueOf(dto.getCylinderNr().charAt(0)));
					dto.setReferenceId(setQualityId(row.getCell(4)));
					dto.setProducerId(setProducerId(row.getCell(5)));
					dto.setHardPJ(Methods.cellContent(row.getCell(6)));
					dto.setReferenceLength(Methods.cellContent(row.getCell(7)));
					dto.setDate(Methods.cellContent(row.getCell(8)));
					dto.setMaxO(Methods.cellContent(row.getCell(9)));
					dto.setMinO(Methods.cellContent(row.getCell(10)));
					listCylinder.add(dto);
				}
			} catch (Exception e) {
				System.out.println("Spalte leer!");
				e.printStackTrace();
			}
			wb.close();
		}
	}

	private void gettingMissingData() throws IOException {
		FileInputStream fis = new FileInputStream(ConstantClass.PATH_EXCELROLLER);
		Workbook wb = new HSSFWorkbook(fis);
		DataFormatter dataFormatter = new DataFormatter();
		Sheet sheet = wb.getSheetAt(1);
		// for (int i = 0; i < sheet.getLastRowNum(); i++) {
		for (int i = 3; i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			try {
				if (Methods.isRowEmpty(row, 3)) {
					break;
				} else {
					for (CylinderDTO dto : listCylinder) {
						if (isCorrectRoller(row, dto)){
							dto.setRammy(Methods.cellContent(row.getCell(4)));
							dto.setBombage(Methods.cellContent(row.getCell(7)));
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Spalte leer!");
				e.printStackTrace();
			}
			wb.close();
		}
	}
	
	private void getCylinder() {
		try {
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_ROLLER));
			for(CylinderDTO dto : listCylinder) {
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("originId", Integer.parseInt(dto.getOriginId()));
				e1.put("cylinderNr", Integer.parseInt(dto.getCylinderNr()));
				e1.put("preferenceType", Integer.parseInt(dto.getReferenceId()));
				e1.put("producer", Integer.parseInt(dto.getOriginId()));
				e1.put("rammy", dto.getRammy());
				e1.put("hardness", dto.getHardPJ());
				e1.put("preferenceLength", Double.parseDouble(dto.getReferenceLength()));
				e1.put("bombage", dto.getBombage());
				e1.put("newDate", dto.getDate());
				e1.put("maxO", dto.getMaxO());
				e1.put("minO", dto.getMinO());
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String setProducerId(Cell producer) {
		String temp = " ";
		for (int i = 0; i < listProducer.size(); i++) {
			if (listProducer.get(i).getProducer().equals(Methods.cellContent(producer))) {
				temp = listProducer.get(i).getId();
				break;
			}
		}
		return temp;
	}

	private String setQualityId(Cell quality) {
		String temp = " ";
		for (int i = 0; i < listQuality.size(); i++) {
			if (listQuality.get(i).getQuality().equals(Methods.cellContent(quality))) {
				temp = listQuality.get(i).getId();
				break;
			}
		}
		return temp;
	}

	private boolean isCorrectRoller(Row row, CylinderDTO dto){
		 boolean temp = false;
		 if(isCorrectReference(Methods.cellContent(row.getCell(2)), dto.getReferenceId()) 
				 && isCorrectProducer(Methods.cellContent(row.getCell(3)), dto.getProducerId())
				 && Methods.cellContent(row.getCell(5)).equals(dto.getHardPJ()) 
				 && Methods.cellContent(row.getCell(6)).equals(dto.getReferenceLength())
				 && Methods.cellContent(row.getCell(8)).equals(dto.getDate())
				 && Methods.cellContent(row.getCell(9)).equals(dto.getMaxO())
				 && Methods.cellContent(row.getCell(10)).equals(dto.getMinO())){
			 temp = true;
		 }
		 return temp;
	 }

	private boolean isCorrectReference(String name, String id) {
		boolean temp = false;
		for (QualityDTO dto : listQuality) {
			if (dto.getId().equals(id))
				if (dto.getQuality().equals(name)){
					temp = true;
					break;
				}
		}
		return temp;
	}

	private boolean isCorrectProducer(String name, String id) {
		boolean temp = false;
		for (ProducerDTO dto : listProducer) {
			if (dto.getId().equals(id))
				if (dto.getProducer().equals(name)){
					temp = true;
					break;
				}
		}
		return temp;
	}
}
