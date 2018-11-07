package org.mitsubishi.createcylindercsv;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import com.opencsv.CSVWriter;

public class GettingOriginData {

	ArrayList<OriginDTO> listRollerOrigin = new ArrayList<OriginDTO>();
	ArrayList<QualityDTO> listQuality = new ArrayList<QualityDTO>();

	public GettingOriginData(ArrayList<QualityDTO> listQuality) {
		try {
			this.listQuality = listQuality;
			gettingData();
			createOriginCSV();
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

	private void createOriginCSV() {
		try (Writer writer = Files.newBufferedWriter(Paths.get(ConstantClass.PATH_ORIGIN));

				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			csvWriter.writeNext(new String[] { "ID", "Name" });
			for (OriginDTO dto : listRollerOrigin)
				csvWriter.writeNext(new String[] { dto.getOriginId(), dto.getOriginName() });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<OriginDTO> getOriginList() {
		return listRollerOrigin;
	}
}
