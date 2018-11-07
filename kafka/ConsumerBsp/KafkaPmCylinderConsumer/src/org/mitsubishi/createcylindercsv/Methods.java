package org.mitsubishi.createcylindercsv;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Methods {

	public static String cellContent(Cell cell) throws IllegalStateException {
		String ret = "";
		int type = cell.getCellType();
		if (cell != null) {
			if (cell.getCellType() == 2)
				type = cell.getCachedFormulaResultType();

			switch (type) {
			case 4:
				ret = Boolean.toString(cell.getBooleanCellValue());
				break;
			case 0:
				if (HSSFDateUtil.isCellDateFormatted(cell))
					ret = cell.getDateCellValue().toString();
				else
					ret = cell.getNumericCellValue() + "";
				break;
			case 1:
				ret = cell.getStringCellValue();
				break;
			case 3:
				ret = "";
				break;
			case 5:
				ret = "ERROR";
				break;

			// CELL_TYPE_FORMULA will never occur
			case 2:
				ret = "";
				break;
			}
		}
		return ret;
	}
	
	public static boolean isRowEmpty(Row row, int add) {
		boolean temp = true;
		for (int i = 0; i < 8; i++) {
			if (row.getCell(i + add).getCellType() != 3){
				temp = false;
				break;
			}
		}
		return temp;
	}
	
}
