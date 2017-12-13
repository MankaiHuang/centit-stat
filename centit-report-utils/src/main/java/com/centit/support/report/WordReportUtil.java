package com.centit.support.report;

import com.centit.support.algorithm.GeneralAlgorithm;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.Map;

/**
 * Created by codefan on 17-8-10.
 * 模板进能用 Docx
 * 依赖开源项目 https://github.com/opensagres/xdocreport
 *             https://github.com/opensagres/xdocreport.samples
 * 示例代码 https://github.com/opensagres/xdocreport.samples/tree/master/samples ~
 *         /fr.opensagres.xdocreport.samples.docxandfreemarker/src/fr/opensagres/xdocreport/samples/docxandfreemarker
 * */

@SuppressWarnings("unused")
public abstract class WordReportUtil {

    private WordReportUtil() {
        throw new IllegalAccessError("Utility class");
    }

    protected static final Logger logger = LoggerFactory.getLogger(WordReportUtil.class);

    /**
     * 这个可以在linux运行，缺点是转换的不完美 自动编号的转换会有错误，并且只能转换Docx，就是xml格式的我让的文档
     * @param docxFilePath word文件路径
     * @param pdfFilePath pdf文件路径questionAnswer
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


    /**
     * 根据模板导出word文件
     *
     * @param params     ReportData对象为数据对象，里面存储Map 数据
     * @param templateName   模板文件路径
     * @param outputFileName 输出文件路径
     */
    public static void reportDocxWithFreemarker(Map<String, Object> params, String templateName, String outputFileName) {

        try(InputStream in = new FileInputStream(new File(templateName))) {
            // 1) Load ODT file and set Velocity template engine and cache it to the registry

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
                    in, TemplateEngineKind.Freemarker);

            // 2) Create Java model context
            IContext context =  getReportContext(report,params);//new ObjectDocxContext(params);//
            // 输出文件，文件存在则删除
            File outputFile = new File(outputFileName);
            // 文件夹不存在，创建所有文件夹
            File parentFile = outputFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (outputFile.exists()) {
                outputFile.renameTo(new File(outputFileName + "." + new Date().getTime()));
            }
            // 生成新的文件
            try(OutputStream outputStream = new FileOutputStream(outputFileName)) {
                report.process(context, outputStream);
            }
            System.out.println("done!");
        } catch (IOException e) {
            logger.warn("文件流获取失败", e);
        } catch (XDocReportException e) {
            logger.warn("导出失败", e);
        }
    }

    private static IContext getReportContext(IXDocReport report, Map<String, Object> params) throws XDocReportException {

        IContext context = report.createContext();
        //FieldsMetadata metadata = new FieldsMetadata();
        //context.putMap(params);
        for (Map.Entry<String, Object> entry : params.entrySet()){
            context.put(entry.getKey(), GeneralAlgorithm.nvl(entry.getValue(),""));
        }
        //report.setFieldsMetadata(metadata);
        return context;
    }
}
