package org.mitsubishi.createcylindercsv;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.opencsv.CSVWriter;

public class GettingOrderData {
	ArrayList<OrderDTO> listOrder = new ArrayList<OrderDTO>();
	ArrayList<QualityDTO> listQuality = new ArrayList<QualityDTO>();

	public GettingOrderData(ArrayList<QualityDTO> listQuality) {
		try {
			for (int i = 0; i < 17; i++) {
				this.listQuality = listQuality;
				gettingData(i);
				createOrderCSV();
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
					//dto.setBuildDate(Methods.cellContent(row.getCell(1)));
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

	private void createOrderCSV() {
		try (Writer writer = Files.newBufferedWriter(Paths.get(ConstantClass.PATH_ORDER));

				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			csvWriter.writeNext(new String[] { "OriginID", "WalzenNr", "Einbaudatum", "Durchm.[mm]", "Laufzeit",
					"Qualität", "Beurteilung", "Ausbaudatum", "Grund für Ausbau" });
			for (OrderDTO dto : listOrder)
				csvWriter.writeNext(
						new String[] { dto.getOriginId(), dto.getRollerNr(), dto.getBuildDate(), dto.getDiameter(),
								dto.getRollTime(), dto.getEvaluation(), dto.getComment(), dto.getDebuildDate(), dto.getDebuildReason() });
		} catch (IOException e) {
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
