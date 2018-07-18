package com.centit.support.report;

import com.centit.support.common.JavaBeanField;
import com.centit.support.common.JavaBeanMetaData;
import org.apache.commons.lang3.StringUtils;
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
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 只能处理xlsx
 */
public abstract class LargeExcelImportUtil {
    private LargeExcelImportUtil() {
            throw new IllegalAccessError("Utility class");
        }

    protected static final Logger logger = LoggerFactory.getLogger(LargeExcelImportUtil.class);

    public static <T> void parserXSSFSheet(String xlsxFile, String sheetName, int beginRow,
                                        Class<T> beanType,  Map<Integer,String > fieldDesc,
                                        Consumer<T> consumer) throws Exception {

        JavaBeanMetaData metaData = JavaBeanMetaData.createBeanMetaDataFromType(beanType);

        parserXSSFSheet(xlsxFile, sheetName, beginRow, (rowMap) -> {
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
        throws Exception {
        OPCPackage pkg = OPCPackage.open(new FileInputStream(new File(xlsxFile)));
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
        XSSFReader xssfReader = new XSSFReader(pkg);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        InputStream stream = null;
        while (iter.hasNext()) {
            try {
                if(StringUtils.isBlank(sheetName) || StringUtils.equals(sheetName,iter.getSheetName())) {
                    stream = iter.next();

                    XMLReader sheetParser = SAXHelper.newXMLReader();

                    ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings,
                        new XSSFSheetToMapHandler(beginRow, consumer) , new DataFormatter(), false);
                    sheetParser.setContentHandler(handler);
                    sheetParser.parse(new InputSource(stream));
                    return ;
                }
                stream = iter.next();
            } catch (Exception e) {
                logger.error("parserSheetXml error: ", e);
            } finally {
                stream.close();
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

        public XSSFSheetToMapHandler(int beginRow, Consumer<Map<Integer, Object> > consumer){
            this.beginRow = beginRow;
            this.endRow = -1;
            this.rowData= new HashMap<>(100);
            this.consumer = consumer;
        }

        @Override
        public void startRow(int rowNum) {
            rowData.clear();
        }

        @Override
        public void endRow(int rowNum) {
            if(rowNum>=beginRow && (this.endRow <0 || rowNum < this.endRow)) {
                consumer.accept(rowData);
            }
        }


        @Override
        public void cell(String cellReference, String cellValue, XSSFComment comment) {
            /*if (cellReference == null) {
                cellReference = new CellAddress(currentRowNumber, currentColNumber).formatAsString();
            }*/
            int thisCol = (new CellReference(cellReference)).getCol();
            rowData.put(thisCol, cellValue);

        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

    }

}
