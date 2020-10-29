package com.success.websocket.utils;

/*import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
*/
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//import org.xhtmlrenderer.pdf.ITextRenderer;

//import com.lowagie.text.DocumentException;

public class PDFThymeleafExample {

  /*public static void main(String[] args) throws IOException, DocumentException {
          PDFThymeleafExample thymeleaf2Pdf = new PDFThymeleafExample();
          String html = thymeleaf2Pdf.parseThymeleafTemplate();
          thymeleaf2Pdf.generatePdfFromHtml(html);
      }

      public void generatePdfFromHtml(String html) throws IOException, DocumentException {
      	String name = "c:/tamil/temp/pdfs/test"+new Random().nextInt()+".pdf";
          OutputStream outputStream = new FileOutputStream(name);

          ITextRenderer renderer = new ITextRenderer();
          renderer.setDocumentFromString(html);
          renderer.layout();
          renderer.createPDF(outputStream);

          outputStream.close();
      }

  */ private String parseThymeleafTemplate() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("to", "<b>Testing</b>");
        context.setVariable("title", "Pavo asado al horno con limón y hierbas");

        return templateEngine.process("thymeleaf_template", context);
    }
}