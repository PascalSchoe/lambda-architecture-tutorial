package org.mitsubishi.createcylindercsv;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.opencsv.CSVWriter;

public class GettingQualityData {
	ArrayList<QualityDTO> listQualityTemp = new ArrayList<QualityDTO>();
	ArrayList<QualityDTO> listQuality = new ArrayList<QualityDTO>();

	public GettingQualityData() {
		try {
			gettingData();
			createQualityList();
			createQualityCSV();
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

	private void createQualityCSV() {
		try (Writer writer = Files.newBufferedWriter(Paths.get(ConstantClass.PATH_QUALITY));

				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			csvWriter.writeNext(new String[] { "ID", "Name" });
			for (QualityDTO dto : listQuality)
				csvWriter.writeNext(new String[] { dto.getId(), dto.getQuality() });
		} catch (IOException e) {
			e.printStackTrace();
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
}
