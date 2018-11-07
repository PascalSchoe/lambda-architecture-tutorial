package org.mitsubishi.createcylindercsv;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.opencsv.CSVWriter;

public class GettingProducerData {
	ArrayList<ProducerDTO> listProducerTemp = new ArrayList<ProducerDTO>();
	ArrayList<ProducerDTO> listProducer = new ArrayList<ProducerDTO>();

	public GettingProducerData() {
		try {
			gettingData();
			createProducerList();
			createProducerCSV();
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

	private void createProducerCSV() {
		try (Writer writer = Files.newBufferedWriter(Paths.get(ConstantClass.PATH_PRODUCER));

				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			csvWriter.writeNext(new String[] { "ID", "Name" });
			for (ProducerDTO dto : listProducer)
				csvWriter.writeNext(new String[] { dto.getId(), dto.getProducer() });
		} catch (IOException e) {
			e.printStackTrace();
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
	
	public ArrayList<ProducerDTO> getListProducer() {
		return listProducer;
	}
}
