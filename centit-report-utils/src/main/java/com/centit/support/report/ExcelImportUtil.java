package com.centit.support.report;

import com.centit.support.algorithm.NumberBaseOpt;
import com.centit.support.common.JavaBeanField;
import com.centit.support.common.JavaBeanMetaData;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by codefan on 17-9-20.
 * @author codefan@sina.com
 */
@SuppressWarnings("unused")
public abstract class ExcelImportUtil {
    private ExcelImportUtil() {
        throw new IllegalAccessError("Utility class");
    }

    protected static final Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

    private static void setObjectFieldValue(Object object, JavaBeanField field , HSSFCell cell){
        switch (field.getFieldJavaType()) {
            case "int":
            case "Integer":
                field.setObjectFieldValue(object,
                        NumberBaseOpt.castObjectToInteger(
                            cell.getNumericCellValue()));
                break;
            case "long":
            case "Long":
                field.setObjectFieldValue(object,
                        NumberBaseOpt.castObjectToLong(
                                cell.getNumericCellValue()
                        ));
                break;
            case "float":
            case "Float":
            case "double":
            case "Double":
                field.setObjectFieldValue(object,cell.getNumericCellValue());
                break;

            case "byte[]":
                field.setObjectFieldValue(object, cell.getStringCellValue().getBytes());
                break;
            case "BigDecimal":
                field.setObjectFieldValue(object,
                        NumberBaseOpt.castObjectToBigDecimal(cell.getNumericCellValue()));
                break;
            case "BigInteger":
                field.setObjectFieldValue(object,
                        NumberBaseOpt.castObjectToBigInteger(cell.getNumericCellValue()));
                break;
            case "String":
                field.setObjectFieldValue(object,cell.getStringCellValue());
                break;
            case "Date":
            case "Timestamp":
                field.setObjectFieldValue(object,cell.getDateCellValue());
                break;
            case "boolean":
            case "Boolean":
                field.setObjectFieldValue(object,cell.getBooleanCellValue());
                break;
            default:
                field.setObjectFieldValue(object, cell.getStringCellValue());
                break;
        }
    }

    private static <T>  List<T> loadObjectFromExcelSheet(HSSFSheet sheet, Class<T> beanType,
                                                       Map<Integer,String > fieldDesc, int beginRow, int endRow)
            throws IllegalAccessException, InstantiationException {

        if(sheet == null)
            return null;

        JavaBeanMetaData metaData = JavaBeanMetaData.creatBeanMetaDataFromType(beanType);

        List<T> datas = new ArrayList<>(endRow-beginRow+1);

        for(int row =beginRow; row<endRow; row ++ ) {

            HSSFRow excelRow = sheet.getRow(row);
            if(excelRow==null)
                continue;
            int i=0;
            T rowObj = beanType.newInstance();

            //excelRow.getFirstCellNum()
            for(Map.Entry<Integer,String> ent : fieldDesc.entrySet() ){
                HSSFCell cell = excelRow.getCell(ent.getKey());
                JavaBeanField field = metaData.getFiled(ent.getValue());
                if(cell!=null && field !=null ){
                    setObjectFieldValue(rowObj,field,cell);
                }
            }

            datas.add(rowObj);
        }

        return datas;
    }

    public static <T>  List<T> loadObjectFromExcel(InputStream excelFile, String sheetName,
                    Class<T> beanType, Map<Integer,String > fieldDesc, int beginRow, int endRow)
            throws IllegalAccessException, InstantiationException, IOException {

            HSSFWorkbook wb = new HSSFWorkbook(excelFile);
            HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                    wb.getSheetAt(0) : wb.getSheet(sheetName);

            return loadObjectFromExcelSheet(sheet,beanType,fieldDesc,  beginRow,  endRow);
    }

    public static <T>  List<T> loadObjectFromExcel(InputStream excelFile, String sheetName,
                  Class<T> beanType, Map<Integer,String > fieldDesc, int beginRow)
            throws IllegalAccessException, InstantiationException, IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);

        return loadObjectFromExcelSheet(sheet,beanType,fieldDesc,  beginRow,  sheet.getLastRowNum());
    }


    public static <T>  List<T> loadObjectFromExcel(InputStream excelFile,  int sheetIndex,
                   Class<T> beanType, Map<Integer,String > fieldDesc, int beginRow, int endRow)
            throws IllegalAccessException, InstantiationException, IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        return loadObjectFromExcelSheet(sheet,beanType,fieldDesc,  beginRow,  endRow);
    }

    public static <T>  List<T> loadObjectFromExcel(InputStream excelFile, int sheetIndex,
                 Class<T> beanType, Map<Integer,String > fieldDesc, int beginRow)
            throws IllegalAccessException, InstantiationException, IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        return loadObjectFromExcelSheet(sheet,beanType,fieldDesc,  beginRow, sheet.getLastRowNum());
    }


    private static List<String[]> loadDataFromExcelSheet(HSSFSheet sheet,
                                                   int[] columnList, int[] rowList){
        if(sheet == null)
            return null;

        //int minRow = sheet.getFirstRowNum();
        //int maxRow = sheet.getLastRowNum();
        List<String[]> datas = new ArrayList<>(rowList.length+1);
        for(int row : rowList) {
            String[] rowObj = new String[columnList.length];
            HSSFRow excelRow = sheet.getRow(row);
            if(excelRow==null){
                datas.add(null);
            }else {
                int i = 0;
                //excelRow.getFirstCellNum()
                for (int col : columnList) {
                    HSSFCell cell = excelRow.getCell(col);
                    rowObj[i++] = cell == null ? null : cell.getStringCellValue();
                }
                datas.add(rowObj);
            }
        }

        return datas;
    }

    /**
     *
     * @param excelFile excel 文件流
     * @param sheetName 读取的页面名称
     * @param columnList 读取的列
     * @param rowList 读取的行
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, String sheetName,
                                                   int[] columnList, int[] rowList)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);

        return loadDataFromExcelSheet(sheet,columnList,rowList);
    }

    /**
     *
     * @param excelFile excel 文件流
     * @param sheetIndex 读取的页面序号 0 base
     * @param columnList 读取的列
     * @param rowList 读取的行
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, int sheetIndex,
                                                   int[] columnList, int[] rowList)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        return loadDataFromExcelSheet(sheet,columnList,rowList);
    }

    private static List<String[]> loadDataFromExcelSheet(HSSFSheet sheet,
                                                   int beginCol, int endCol, int beginRow, int endRow){
        if(sheet == null)
            return null;

        List<String[]> datas = new ArrayList<>(endRow-beginRow+1);
        for(int row =beginRow; row<endRow; row ++ ) {

            HSSFRow excelRow = sheet.getRow(row);
            if(excelRow==null)
                continue;
            int i=0;
            String[] rowObj = new String[endCol-beginCol+1];
            //excelRow.getFirstCellNum()
            for(int col =beginCol; col < endCol; col++ ){
                HSSFCell cell = excelRow.getCell(col);
                rowObj[i++] = cell == null ? null : cell.getStringCellValue();
            }
            datas.add(rowObj);
        }

        return datas;
    }

    /**
     * 所有的行列都是 0 Base的
     * @param excelFile excel 文件流
     * @param sheetIndex 读取的页面序号 0 base
     * @param beginCol 起始列  包含 beginCol
     * @param endCol 终止列 不包含 endCol
     * @param beginRow 起始行 包含 beginRow
     * @param endRow 起始行 不包含 endRow
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, int sheetIndex,
                                                   int beginCol, int endCol, int beginRow, int endRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        return loadDataFromExcelSheet(sheet, beginCol,  endCol, beginRow, endRow);
    }

    /**
     * 所有的行列都是 0 Base的
     * @param excelFile excel 文件流
     * @param sheetName 读取的页面名称 , 如果为空则 读取第一个页面
     * @param beginCol 起始列  包含 beginCol
     * @param endCol 终止列 不包含 endCol
     * @param beginRow 起始行 包含 beginRow
     * @param endRow 起始行 不包含 endRow
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, String sheetName,
                                                   int beginCol, int endCol, int beginRow, int endRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);

        return loadDataFromExcelSheet(sheet, beginCol,  endCol, beginRow, endRow);
    }

    /**
     * 所有的行列都是 0 Base的
     * @param excelFile excel 文件流
     * @param sheetIndex 读取的页面序号 0 base
     * @param beginCol 起始列  包含 beginCol
     * @param endCol 终止列 不包含 endCol
     * @param beginRow 起始行 包含 beginRow
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, int sheetIndex,
                                                   int beginCol, int endCol, int beginRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        return loadDataFromExcelSheet(sheet, beginCol,  endCol, beginRow, sheet.getLastRowNum() );
    }
    /**
     * 所有的行列都是 0 Base的
     * @param excelFile excel 文件流
     * @param sheetName 读取的页面名称
     * @param beginCol 起始列  包含 beginCol
     * @param endCol 终止列 不包含 endCol
     * @param beginRow 起始行 包含 beginRow
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, String sheetName,
                                                   int beginCol, int endCol, int beginRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);

        return loadDataFromExcelSheet(sheet, beginCol,  endCol, beginRow, sheet.getLastRowNum());
    }

    private static List<String[]> loadDataFromExcelSheet( HSSFSheet sheet,
                                                   int beginCol,  int beginRow)
            throws IOException {

        if(sheet == null)
            return null;
        int maxRow = sheet.getLastRowNum();
        List<String[]> datas = new ArrayList<>(maxRow-beginRow+1);
        for(int row =beginRow; row<maxRow; row ++ ) {

            HSSFRow excelRow = sheet.getRow(row);
            if(excelRow==null)
                continue;

            int endCol = excelRow.getLastCellNum();
            String[] rowObj = new String[endCol-beginCol+1];
            int i=0;
            //excelRow.getFirstCellNum()
            for(int col = beginCol; col < endCol; col++ ){
                HSSFCell cell = excelRow.getCell(col);
                rowObj[i++] = cell == null ? null : cell.getStringCellValue();
            }
            datas.add(rowObj);
        }

        return datas;
    }

        /**
         * 所有的行列都是 0 Base的
         * @param excelFile excel 文件流
         * @param sheetIndex 读取的页面序号 0 base
         * @param beginCol 起始列  包含 beginCol
         * @param beginRow 起始行 包含 beginRow
         * @return 返回二维数组
         */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, int sheetIndex,
                                                   int beginCol,  int beginRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        return loadDataFromExcelSheet(sheet, beginCol, beginRow);
    }

    /**
     * 所有的行列都是 0 Base的
     * @param excelFile excel 文件流
     * @param sheetName 读取的页面名称
     * @param beginCol 起始列  包含 beginCol
     * @param beginRow 起始行 包含 beginRow
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, String sheetName,
                                                   int beginCol,  int beginRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);

        return loadDataFromExcelSheet(sheet, beginCol, beginRow);
    }
}
