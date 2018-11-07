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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mitsubishi.cylinder.ConstantClass;

public class GettingProcedureData {
	ArrayList<OrderDTO> listOrder = new ArrayList<OrderDTO>();
	ArrayList<QualityDTO> listQuality = new ArrayList<QualityDTO>();

	public GettingProcedureData(ArrayList<QualityDTO> listQuality) {
		try {
			for (int i = 0; i < 17; i++) {
				this.listQuality = listQuality;
				gettingData(i);
				getProd();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gettingData(int index) throws IOException {
		FileInputStream fis = new FileInputStream(ConstantClass.PATH_EXCELROLLER);
		Workbook wb = new HSSFWorkbook(fis);
		DataFormatter dataFormatter = new DataFormatter();
		Sheet sheet = wb.getSheetAt(index + 2);
		for (int i = 9; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			try {
				if (Methods.isRowEmpty(row, 0)) {
					break;
				} else {
					OrderDTO dto = new OrderDTO();
					dto.setOriginId(Integer.toString(index + 1));
					dto.setRollerNr(Methods.cellContent(row.getCell(0)));
					if (dto.getRollerNr().length() >= 1)
						dto.setRollerNr(String.valueOf(dto.getRollerNr().charAt(0)));
					//dto.setRollerNr(String.valueOf(Methods.cellContent(row.getCell(0)).charAt(0)));
					dto.setBuildDate(Methods.cellContent(row.getCell(1)));
					dto.setEvaluation(setQualityId(row.getCell(4)));
					dto.setDiameter(Methods.cellContent(row.getCell(2)));
					dto.setRollTime(Methods.cellContent(row.getCell(3)));
					dto.setComment(Methods.cellContent(row.getCell(5)));
					dto.setDebuildDate(dataFormatter.formatCellValue(row.getCell(8)));
					dto.setDebuildReason(Methods.cellContent(row.getCell(9)));
					listOrder.add(dto);
				}
			} catch (Exception e) {
				System.out.println("Spalte leer!");
				e.printStackTrace();
			}
			wb.close();
		}
	}
	
	private void getProd() {
		try {
			ArrayList<GenericRecord> recordList = new ArrayList<GenericRecord>();
			// Schema wird eingelesen
			Schema schema = new Schema.Parser().parse(new File(ConstantClass.SCHEMA_PROCEDURE));
			for(OrderDTO dto : listOrder) {
				//Daten werden eingelesen
				GenericRecord e1 = new GenericData.Record(schema);
				e1.put("originId", Integer.parseInt(dto.getOriginId()));
				e1.put("cylinderNr", Integer.parseInt(dto.getRollerNr()));
				e1.put("buildinDate", dto.getBuildDate());
				e1.put("diameter(mm)", Double.parseDouble(dto.getDiameter()));
				e1.put("runtime", dto.getRollTime());
				e1.put("quality", Integer.parseInt(dto.getEvaluation()));
				e1.put("evaluation", dto.getComment());
				e1.put("buildoutDate", dto.getDebuildDate());
				e1.put("buildoutReason", dto.getDebuildReason());
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
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
