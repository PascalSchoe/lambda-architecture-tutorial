package org.mitsubishi.createcylindercsv;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.text.AbstractDocument.Content;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import com.opencsv.CSVWriter;

public class GettingRollerData {

	ArrayList<RollerDTO> listRoller = new ArrayList<RollerDTO>();
	ArrayList<ProducerDTO> listProducer = new ArrayList<ProducerDTO>();
	ArrayList<QualityDTO> listQuality = new ArrayList<QualityDTO>();
	ArrayList<OriginDTO> listRollerOrigin = new ArrayList<OriginDTO>();

	public GettingRollerData(ArrayList<ProducerDTO> listProducer, ArrayList<QualityDTO> listQuality,
			ArrayList<OriginDTO> listRollerOrigin) {
		try {
			this.listProducer = listProducer;
			this.listQuality = listQuality;
			this.listRollerOrigin = listRollerOrigin;
			for (int i = 0; i < 17; i++) {
				gettingData(i);
				gettingMissingData();
				createRollerCSV();
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
		// for (int i = 0; i < sheet.getLastRowNum(); i++) {
		for (int i = 0; i < 6; i++) {
			Row row = sheet.getRow(i + 1);
			try {
				if (Methods.isRowEmpty(row, 3)) {
					break;
				} else {
					RollerDTO dto = new RollerDTO();
					dto.setOriginId((index + 1) + "");
					dto.setRollerNr(Methods.cellContent(row.getCell(3)));
					if (dto.getRollerNr().length() >= 1)
						dto.setRollerNr(String.valueOf(dto.getRollerNr().charAt(0)));
					dto.setReferenceId(setQualityId(row.getCell(4)));
					dto.setProducerId(setProducerId(row.getCell(5)));
					dto.setHardPJ(Methods.cellContent(row.getCell(6)));
					dto.setReferenceLength(Methods.cellContent(row.getCell(7)));
					dto.setDate(Methods.cellContent(row.getCell(8)));
					dto.setMaxO(Methods.cellContent(row.getCell(9)));
					dto.setMinO(Methods.cellContent(row.getCell(10)));
					listRoller.add(dto);
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
					for (RollerDTO dto : listRoller) {
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

	private void createRollerCSV() {
		try (Writer writer = Files.newBufferedWriter(Paths.get(ConstantClass.PATH_ROLLER));

				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			csvWriter.writeNext(new String[] { "OriginID", "WalzenNr", "Bezugstype", "Bezug/Hersteller", "Schliffgüte/Rammy", "Härte P&J",
					"Bezugslänge", "Bombage", "Neubezug", "MaxO", "MinO" });
			for (RollerDTO dto : listRoller)
				csvWriter.writeNext(new String[] { dto.getOriginId(), dto.getRollerNr(), dto.getReferenceId(),
						dto.getProducerId(), dto.getRammy(), dto.getHardPJ(), dto.getReferenceLength(), dto.getBombage(), dto.getDate(), dto.getMaxO(),
						dto.getMinO() });
		} catch (IOException e) {
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

	private boolean isCorrectRoller(Row row, RollerDTO dto){
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
