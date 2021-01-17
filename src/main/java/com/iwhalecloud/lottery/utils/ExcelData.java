package com.iwhalecloud.lottery.utils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入工具类
 */
public class ExcelData {
    private final static Logger logger = Logger.getLogger(ExcelData.class);
    private HSSFSheet sheet;
    private List<Map<Integer, String>> rowList;
    private int rowNumOfColumnKey = 1;
    private Map<String, Integer> columnKeyMap;

    private ExcelData() {
    }

    private void parseFile(String filePath) throws Exception {
        InputStream ins = new FileInputStream(new File(filePath));
        this.parseInputStream(ins);
    }

    private void parseInputStream(InputStream ins) throws Exception {
        HSSFWorkbook book = new HSSFWorkbook(new POIFSFileSystem(ins));
        sheet = book.getSheetAt(0);//0页
        int lastRowNum = sheet.getLastRowNum();
        rowList = new ArrayList<Map<Integer, String>>();
        //行从0开始，lastRowNum结束（包含lastRowNum）
        for (int i = 0; i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            Map<Integer, String> rowMap = new HashMap<Integer, String>();
            int lastCellNum = row.getLastCellNum();
            //列从0开始，lastCellNum结束（包含lastCellNum）
            for (short j = 0; j <= lastCellNum; j++) {
                HSSFCell cell = row.getCell(j);
                String cellValue = getCellValue(cell);
                if (cellValue != null) {
                    rowMap.put(new Integer(j), cellValue);
                }
            }

            if (!rowMap.isEmpty()) {
                // 该行存在有效数据
                rowMap.put(-1, String.valueOf(i + 1));
                rowList.add(rowMap);
            }
        }
    }

    /**
     * 设置字段key的行号
     *
     * @param rowNum
     */
    private void setRowNumOfColumnKey(int rowNum) {
        if (rowNum < 1 || rowNum > sheet.getLastRowNum() + 1) {
            rowNum = 1;
        }
        rowNumOfColumnKey = rowNum;
        columnKeyMap = new HashMap<String, Integer>();
        HSSFRow row = sheet.getRow(rowNum - 1);
        if (row == null) {
            return;
        }
        short lastCellNum = row.getLastCellNum();
        for (short i = 0; i < lastCellNum; i++) {
            HSSFCell cell = row.getCell(i);
            if (cell == null) {
                continue;
            }
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            columnKeyMap.put(cell.getStringCellValue(), new Integer(i));
        }
    }

    private List<Map<String, String>> getSheetData() {
        List<Map<String, String>> sheetData = new ArrayList<Map<String, String>>();
        for (int i = rowNumOfColumnKey; i < rowList.size(); i++) {
            Map<Integer, String> rowMap = rowList.get(i);
            //for (Map<Integer, String> rowMap : rowList) {
            Map<String, String> rowData = new HashMap<String, String>();
            for (String key : columnKeyMap.keySet()) {
                Integer columnIndex = columnKeyMap.get(key);
                if (rowMap.containsKey(columnIndex)) {
                    rowData.put(key, rowMap.get(columnIndex));
                }else {
                    rowData.put(key, null);
                }
            }
            rowData.put("_ROW_NO", rowMap.get(-1));
            sheetData.add(rowData);
        }
        System.out.println("sheetData"+sheetData);
        return sheetData;
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    private static String getCellValue(HSSFCell cell) {
        if (cell == null) {
            return null;
        }
        String cellValue = null;
        try {
            //若是数字：有小数则最多保留10位小数
            DecimalFormat df = new DecimalFormat("#.##########");
            int cellType = cell.getCellType();
            switch (cellType) {
                case HSSFCell.CELL_TYPE_BLANK:
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    boolean isDateTimeCell = false;
                    short dataFormat = cell.getCellStyle().getDataFormat();
                    SimpleDateFormat sdf = null;
                    if (dataFormat == 14 || dataFormat == 31 || dataFormat == 57 || dataFormat == 58) {
                        //日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        isDateTimeCell = true;
                    } else if (dataFormat == 20 || dataFormat == 32) {
                        //时间
                        sdf = new SimpleDateFormat("HH:mm");
                        isDateTimeCell = true;
                    } else if (dataFormat == 177) {
                        //日期+时间
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        isDateTimeCell = true;
                    }
                    double value = cell.getNumericCellValue();
                    if (isDateTimeCell) {
                        cellValue = sdf.format(DateUtil.getJavaDate(value));
                    } else {
                        cellValue = df.format(value);
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                    //数字、字符、值错误
                    double dvalue = cell.getNumericCellValue();
                    if (Double.isNaN(dvalue)) {
                        cellValue = cell.getStringCellValue();
                    } else {
                        cellValue = String.valueOf(df.format(dvalue));
                    }
                    break;
            }
        } catch (Exception e) {
            logger.debug("获取单元格值出错", e);
        }
        return cellValue;
    }

    private void clear() {
        this.sheet = null;
        this.rowList = null;
        this.columnKeyMap = null;
    }

    public static List<Map<String, String>> getSheetData(String filePath, int rowNumOfColumnKey) {
        ExcelData excelData = new ExcelData();
        try {
            System.out.println("hahhahahahahahahha");
            excelData.parseFile(filePath);
            excelData.setRowNumOfColumnKey(rowNumOfColumnKey);
            System.out.println("hahhahahahahahahha");
            return excelData.getSheetData();
        } catch (Exception e) {
            System.out.println(e);
            logger.error("获取excel数据失败", e);
        } finally {
            excelData.clear();
            excelData = null;
        }
        System.out.println("lalallalalala");
        return null;
    }

    public static List<Map<String, String>> getSheetData(InputStream ins, int rowNumOfColumnKey) {
        ExcelData excelData = new ExcelData();
        try {
            excelData.parseInputStream(ins);
            excelData.setRowNumOfColumnKey(rowNumOfColumnKey);
            return excelData.getSheetData();
        } catch (Exception e) {
            logger.error("获取excel数据失败", e);
        } finally {
            excelData.clear();
            excelData = null;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "";
        filePath = "F:\\lottery.xls";
        List<Map<String, String>> sheetData = ExcelData.getSheetData(filePath, 2);
        System.out.println("sheetData = " + sheetData);
    }
}
