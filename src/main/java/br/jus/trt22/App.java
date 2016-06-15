package br.jus.trt22;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {

        File file = new File(args[0]);
        InputStream is = new FileInputStream(file);

        org.jsoup.nodes.Document document = Jsoup.parse(file,"utf-8");
        document.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml); //This will ensure the validity
        document.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
        Reader br = new StringReader(document.toString());


        Tidy tidy = new Tidy();
        tidy.setXmlTags(true);
        tidy.setShowWarnings(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        Document tidyDocument = tidy.parseDOM(br,null);


        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(tidyDocument,null);
        doRenderToPDF(renderer, args[1]);
    }

    private String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader (file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    private static void doRenderToPDF(ITextRenderer renderer, String pdf) throws Exception {
        OutputStream os = null;
        try {
            os = new FileOutputStream(pdf);
            renderer.layout();
            renderer.createPDF(os);

            os.close();
            os = null;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
