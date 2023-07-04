package mika32.Utils;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Read_Edit_docx {
    public static ArrayList<String> text_par = new ArrayList<>();
    public static String txt = "";

    public static XWPFDocument readDocx(){
        try {
            FileInputStream fis = new FileInputStream("C:/Users/Mika/OneDrive/Desktop/schule/Entschuldigungen/allgemein/automat/autoEnt.docx");
            return new XWPFDocument(fis);

        }catch (Exception e){
            System.out.println("Could not find docx");
        }
        return null;
    }

    public static XWPFDocument editDocx(XWPFDocument document, String searchWord, String replacement) {
        String txt = "";

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains(searchWord)) {
                    text = text.replace(searchWord, replacement);
                    run.setText(text, 0);
                }
            }
        }

        return document;
    }
}

