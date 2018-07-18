package com.centit.support.report;

import com.centit.support.common.JavaBeanField;
import com.centit.support.common.JavaBeanMetaData;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 只能处理xlsx(xml格式的)
 */
public abstract class LargeExcelImportUtil {
    private LargeExcelImportUtil() {
            throw new IllegalAccessError("Utility class");
        }

    protected static final Logger logger = LoggerFactory.getLogger(LargeExcelImportUtil.class);

    public static <T> void parserXSSFSheet(String xlsxFile, String sheetName, int beginRow,
                                           Class<T> beanType,  Map<Integer,String > fieldDesc,
                                           Consumer<T> consumer)
        throws IOException, OpenXML4JException, SAXException  {
        parserXSSFSheet(xlsxFile, sheetName, beginRow, -1, beanType, fieldDesc, consumer);
    }
    public static <T> void parserXSSFSheet(String xlsxFile, String sheetName, int beginRow, int endRow,
                                        Class<T> beanType,  Map<Integer,String > fieldDesc,
                                        Consumer<T> consumer)
        throws IOException, OpenXML4JException, SAXException  {

        JavaBeanMetaData metaData = JavaBeanMetaData.createBeanMetaDataFromType(beanType);
        parserXSSFSheet(xlsxFile, sheetName, beginRow, endRow, (rowMap) -> {
            try {
                T rowObj = beanType.newInstance();
                for(Map.Entry<Integer,String> ent : fieldDesc.entrySet() ){
                    Object value = rowMap.get(ent.getKey());
                    if(value != null){
                        JavaBeanField field = metaData.getFiled(ent.getValue());
                        if(field != null) {
                            field.setObjectFieldValue(rowObj, value);
                        }
                    }
                }
                consumer.accept(rowObj);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static void parserXSSFSheet(String xlsxFile, String sheetName, int beginRow, Consumer<Map<Integer, Object>> consumer)
        throws IOException, OpenXML4JException, SAXException  {
        parserXSSFSheet(xlsxFile, sheetName, beginRow, -1, consumer);
    }

    public static void parserXSSFSheet(String xlsxFile, String sheetName, int beginRow, int endRow, Consumer<Map<Integer, Object>> consumer)
        throws IOException, OpenXML4JException, SAXException {
        OPCPackage pkg = OPCPackage.open(new FileInputStream(new File(xlsxFile)));
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
        XSSFReader xssfReader = new XSSFReader(pkg);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        InputStream stream = null;
        int sheetIndex=0;
        while (iter.hasNext()) {
            try {
                stream = iter.next();
                String currSheetName = iter.getSheetName();
                if( StringUtils.isBlank(sheetName)
                    || StringUtils.equals(sheetName,currSheetName)
                    || StringUtils.equals(sheetName,Integer.toString(sheetIndex))) {
                    //stream = iter.next();
                    XMLReader sheetParser = SAXHelper.newXMLReader();
                    ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings,
                        new XSSFSheetToMapHandler(beginRow, endRow, consumer) , new DataFormatter(), false);
                    sheetParser.setContentHandler(handler);
                    sheetParser.parse(new InputSource(stream));
                    return ;
                }
                sheetIndex ++;
            } catch (Exception e) {
                logger.error("parserSheetXml error: ", e);
            } finally {
                if(stream!=null) {
                    stream.close();
                }
            }
        }
    }

    private static class XSSFSheetToMapHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
        private Map<Integer, Object> rowData;
        private int beginRow;
        private int endRow;
        private Consumer<Map<Integer, Object>> consumer;

        public XSSFSheetToMapHandler(){
            beginRow = -1;
            endRow = -1;
            rowData= new HashMap<>(100);
        }

        public XSSFSheetToMapHandler(int beginRow, int endRow, Consumer<Map<Integer, Object> > consumer){
            this.beginRow = beginRow;
            this.endRow = endRow;
            this.rowData= new HashMap<>(100);
            this.consumer = consumer;
        }

        @Override
        public void startRow(int rowNum) {
            rowData.clear();
        }

        @Override
        public void endRow(int rowNum) {
            if(rowNum >= beginRow && (this.endRow < 0 || rowNum < this.endRow)) {
                consumer.accept(rowData);
            }
        }

        @Override
        public void cell(String cellReference, String cellValue, XSSFComment comment) {
            int thisCol = (new CellReference(cellReference)).getCol();
            rowData.put(thisCol, cellValue);
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }
    }
}
