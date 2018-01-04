package main.java.com.hiveview.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class ExcelUtil {
	/**
	 * 获取Cell的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getValue(Cell cell) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("#");
		int cellType = cell.getCellType();
		String cellValue = null;
		switch (cellType) {
		case Cell.CELL_TYPE_STRING: // 文本
			cellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC: // 数字、日期
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = simpleDateFormat.format(cell.getDateCellValue()); // 日期型
			}
			else {
				cellValue = decimalFormat.format(cell.getNumericCellValue()); // 数字
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN: // 布尔型
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK: // 空白
			cellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_ERROR: // 错误
			cellValue = "错误";
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = "错误";
			break;
		default:
			cellValue = "错误";
		}
		return cellValue;
	}
}
