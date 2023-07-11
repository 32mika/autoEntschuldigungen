package mika32.Utils;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Read_Edit_docx {
    public static ArrayList<String> text_par = new ArrayList<>();
    public static String txt = "";

    public static XWPFDocument readDocx(){
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current Directory: " + currentDirectory);

        /*
        try {
            FileInputStream fis = new FileInputStream("/src/main/resources/Docs/autoEnt.docx");
            System.out.println("Read the file");
            return new XWPFDocument(fis);

        }catch (Exception e){
            System.out.println("Could not find docx");
        }

         */

        try{
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/classes/Docs/autoEnt.docx");
            System.out.println("Got File");
            return new XWPFDocument(fis);
        }catch (Exception e){
            System.out.println("Failed to read doc");
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

