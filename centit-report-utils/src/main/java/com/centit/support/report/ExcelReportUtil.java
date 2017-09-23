package com.centit.support.report;

import com.centit.support.algorithm.ReflectionOpt;
import com.centit.support.algorithm.StringBaseOpt;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 生成基本EXCEL工具类
 *
 * @author codefan@sina.com
 * 2013-6-25
 */
@SuppressWarnings("unused")
public abstract class ExcelReportUtil {

    private ExcelReportUtil() {
        throw new IllegalAccessError("Utility class");
    }

    protected static final Logger logger = LoggerFactory.getLogger(ExcelReportUtil.class);

    public static InputStream generateExcel(List<? extends Object> objLists) {
        return generateExcel(objLists,null,null);
    }


    /**
     * 生成Excel字节流
     *
     * @param objLists 对象集合
     * @param header   Excel页头
     * @param property 需要显示的属性
     * @return InputStream excel 文件流
     */
    public static InputStream generateExcel(List<? extends Object> objLists, String[] header, String[] property) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        if(header!=null && header.length>0) {
            generateHeader(sheet, header);
        }

        try {
            if(property!=null && property.length>0) {
                generateText(sheet, objLists, property);
            }else{
                generateObjText(sheet, objLists);
            }
            sheet.getWorkbook().write(baos);
        } catch (IOException | InvocationTargetException | NoSuchMethodException
                | IllegalAccessException | NoSuchFieldException e) {
            throw new StatReportException(e);
        }

        return new ByteArrayInputStream(baos.toByteArray());
    }

    /*
     * 生成Excel字节流
     *
     * @param objLists 对象集合
     * @param property 需要显示的属性
     * @return InputStream excel 文件流
     */

    /*public static InputStream generateExcel(List<? extends Object> objLists, String[] property) {
        return generateExcel(objLists,null,property);
    }*/

    /**
     * 生成Excel字节流
     *
     * @param objLists 对象数组集合
     * @param header   Excel页头
     * @return InputStream excel 文件流
     */
    public static InputStream generateExcel(List<Object[]> objLists, String[] header) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        generateHeader(sheet, header);
        generateText(sheet, objLists);

        try {
            sheet.getWorkbook().write(baos);
        } catch (IOException e) {
            throw new StatReportException(e);
        }

        return new ByteArrayInputStream(baos.toByteArray());

    }





    private static void generateHeader(HSSFSheet sheet, String[] header) {
        HSSFRow headerRow = sheet.createRow(0);
        HSSFCellStyle cellStyle = getDefaultCellStyle(sheet.getWorkbook());
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            setCellStyle(cell, cellStyle);

            cell.setCellValue(header[i]);
        }
    }

    @SuppressWarnings("unchecked")
    private static void generateText(HSSFSheet sheet, List<? extends Object> objLists, String[] property) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HSSFCellStyle cellStyle = getDefaultCellStyle(sheet.getWorkbook());

        for (int i = 0; i < objLists.size(); i++) {
            HSSFRow textRow = sheet.createRow(i + 1);
            for (int j = 0; j < property.length; j++) {
                HSSFCell cell = textRow.createCell(j);
                setCellStyle(cell, cellStyle);

                //List中是Map类型
                if (objLists.get(i) instanceof Map) {
                    Object val = ((Map<String,Object>) objLists.get(i)).get(property[j]);
                    cell.setCellValue(null == val ? "" : val.toString());
                    continue;
                }
                //List中是普通Po类型
                Method method = ReflectionOpt.getGetterMethod(objLists.get(i).getClass(), property[j]);
                if(method == null)
                    method = ReflectionOpt.getBooleanGetterMethod(objLists.get(i).getClass(), property[j]);

                Object val = method.invoke(objLists.get(i));

                cell.setCellValue(null == val ? "" : val.toString());
            }
        }
    }

    private static void generateObjText(HSSFSheet sheet, List<? extends Object> objLists) throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < objLists.size(); i++) {
            HSSFRow textRow = sheet.createRow(i);

            List<Method> getMethods = ReflectionOpt.getAllGetterMethod(objLists.get(i).getClass());
            HSSFCellStyle cellStyle = getDefaultCellStyle(sheet.getWorkbook());
            //Field[] fields = objLists.get(i).getClass().getDeclaredFields();
            int errorLen = 0;
            for (int j = 0; j < getMethods.size(); j++) {

                HSSFCell cell = textRow.createCell(j - errorLen);
                setCellStyle(cell, cellStyle);

                Object val = getMethods.get(j).invoke(objLists.get(i));
                cell.setCellValue(null == val ? "" : val.toString());
            }
        }
    }

    private static void generateText(HSSFSheet sheet, List<Object[]> objLists) {
        for (int i = 0; i < objLists.size(); i++) {
            HSSFRow textRow = sheet.createRow(i + 1);
            HSSFCellStyle cellStyle = getDefaultCellStyle(sheet.getWorkbook());
            for (int j = 0; j < objLists.get(i).length; j++) {
                HSSFCell cell = textRow.createCell(j);

                setCellStyle(cell, cellStyle);

                cell.setCellValue(null == objLists.get(i)[j] ? "" : objLists.get(i)[j].toString());
            }
        }
    }

    private static void setCellStyle(HSSFCell cell, HSSFCellStyle cellStyle) {
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(cellStyle);
    }

    /*
     * 设置单元格默认样式
     *
     */
    private static HSSFCellStyle getDefaultCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();

        // 指定单元格居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 指定单元格垂直居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        cellStyle.setWrapText(true);// 指定单元格自动换行

        // 设置单元格字体
        HSSFFont font = wb.createFont();
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
//        font.setFontHeight((short) 300);
        cellStyle.setFont(font);

        return cellStyle;
    }

    public static void saveObjectsToExcelSheet( HSSFSheet sheet, List<Object> objects,
                                          Map<Integer,String > fieldDesc, int beginRow, boolean createRow) {
        int nRowCount = objects.size();
        HSSFCellStyle cellStyle = getDefaultCellStyle(sheet.getWorkbook());
        for (int i = 0; i < nRowCount; i++) {
            HSSFRow excelRow = createRow ? sheet.createRow(beginRow + i) : sheet.getRow(beginRow + i);
            Object rowObj = objects.get(i);
            if (rowObj != null && excelRow != null) {

                for (Map.Entry<Integer, String> ent : fieldDesc.entrySet()) {
                    HSSFCell cell = null;
                    if (!createRow) {
                        cell = excelRow.getCell(ent.getKey());
                    }

                    if (cell == null) {
                        cell = excelRow.createCell(ent.getKey());
                        setCellStyle(cell, cellStyle);
                    }

                    cell.setCellValue(
                            StringBaseOpt.objectToString(
                                    ReflectionOpt.attainExpressionValue(rowObj, ent.getValue())));
                }

            }
        }
        //return 0;
    }

    public static void saveObjectsToExcelSheet( HSSFSheet sheet, List<Object[]> objects,
                                           int beginCol, int beginRow, boolean createRow) {
        int nRowCount = objects.size();
        HSSFCellStyle cellStyle = getDefaultCellStyle(sheet.getWorkbook());
        for (int i = 0; i < nRowCount; i++) {
            HSSFRow excelRow = createRow ? sheet.createRow(beginRow + i) : sheet.getRow(beginRow + i);

            Object[] rowObj = objects.get(i);

            if (rowObj != null && excelRow != null) {

                for (int j = 0; j<rowObj.length; j++ ) {
                    HSSFCell cell = null;
                    if (!createRow) {
                        cell = excelRow.getCell(beginCol+j);
                    }

                    if (cell == null) {
                        cell = excelRow.createCell(beginCol+j);
                        setCellStyle(cell, cellStyle);
                    }

                    cell.setCellValue(
                            StringBaseOpt.objectToString(rowObj[j]));
                }

            }
        }
        //return 0;
    }

    /**
     * 保存对象到 Excel 文件
     * @param excelFile 文件
     * @param sheetName sheet名称
     * @param objects 对象数组
     * @param fieldDesc 列和字段对应关系
     * @param beginRow 写入起始行
     * @param createRow 是否 创建（插入）行 还是直接覆盖
     * @return 成功还是失败
     * @throws IOException 文件存储异常
     */
    public static boolean saveObjectsToExcel(File excelFile, String sheetName,
                                          List<Object> objects,
                                          Map<Integer,String > fieldDesc, int beginRow, boolean createRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));

        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);
        if(sheet == null)
            return false;

        saveObjectsToExcelSheet(sheet, objects,  fieldDesc, beginRow, createRow);

        wb.write(excelFile);
        return true;

    }

    /**
     * 保存对象到 Excel 文件
     * @param excelFile 文件
     * @param sheetIndex sheet 索引
     * @param objects 对象数组
     * @param fieldDesc 列和字段对应关系
     * @param beginRow 写入起始行
     * @param createRow 是否 创建（插入）行 还是直接覆盖
     * @return 成功还是失败
     * @throws IOException 文件存储异常
     */
    public static boolean saveObjectsToExcel( File excelFile, int sheetIndex,
                                           List<Object> objects,
                                           Map<Integer,String > fieldDesc, int beginRow, boolean createRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
        HSSFSheet sheet =  wb.getSheetAt(sheetIndex);
        if(sheet == null)
            return false;

        saveObjectsToExcelSheet(sheet, objects,  fieldDesc, beginRow, createRow);

        wb.write(excelFile);
        return true;
    }

    /**
     * 保存二维数组到 Excel 文件
     * @param excelFile 文件
     * @param sheetName sheet 名称
     * @param objects 二维数组
     * @param beginCol 写入起始列
     * @param beginRow 写入起始行
     * @param createRow 是否 创建（插入）行 还是直接覆盖
     * @return 成功还是失败
     * @throws IOException 文件存储异常
     */
    public static boolean saveObjectsToExcel(File excelFile, String sheetName,
                                             List<Object[]> objects,
                                             int beginCol, int beginRow, boolean createRow)
            throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);
        if(sheet == null)
            return false;

        saveObjectsToExcelSheet(sheet, objects,  beginCol, beginRow, createRow);

        wb.write(excelFile);
        return true;

    }

    /**
     * 保存二维数组到 Excel 文件
     * @param excelFile 文件
     * @param sheetIndex sheet 索引
     * @param objects 二维数组
     * @param beginCol 写入起始列
     * @param beginRow 写入起始行
     * @param createRow 是否 创建（插入）行 还是直接覆盖
     * @return 成功还是失败
     * @throws IOException 文件存储异常
     */
    public static boolean saveObjectsToExcel(File excelFile, int sheetIndex,
                                             List<Object[]> objects,
                                             int beginCol, int beginRow, boolean createRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
        HSSFSheet sheet =  wb.getSheetAt(sheetIndex);
        if(sheet == null)
            return false;

        saveObjectsToExcelSheet(sheet, objects,  beginCol, beginRow, createRow);

        wb.write(excelFile);
        return true;
    }

}
