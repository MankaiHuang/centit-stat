package com.centit.support.report;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by codefan on 17-8-10.
 * 模板进能用 Docx
 * 依赖开源项目 https://github.com/opensagres/xdocreport
 */

@SuppressWarnings("unused")
public abstract class WordReportUtil {

    /**
     * 这个可以在linux运行，缺点是转换的不完美 自动编号的转换会有错误，并且只能转换Docx，就是xml格式的我让的文档
     * @param docxFilePath word文件路径
     * @param pdfFilePath pdf文件路径
     * @throws Exception 转换异常
     */
    public static void convertDocxToPdf(String docxFilePath,String pdfFilePath) throws Exception{

        // 1) Load docx with POI XWPFDocument
        XWPFDocument document = new XWPFDocument( new FileInputStream( new File(docxFilePath)));

        // 2) Convert POI XWPFDocument 2 PDF with iText
        File outFile = new File( pdfFilePath);
        outFile.getParentFile().mkdirs();

        OutputStream out = new FileOutputStream( outFile );
        PdfOptions options = PdfOptions.create()/*.fontEncoding( "UTF-8" )*/;
        PdfConverter.getInstance().convert( document, out, options );
    }


}
