package mika32;

import mika32.GUI.Main_Frame;
import mika32.Utils.MergeDocs;
import mika32.Utils.PrintDocument;
import mika32.Utils.Read_Edit_docx;
import mika32.Utils.SaveFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static String date = "27.06.2023";
    public static String student_name = "Mika Dobrowolski";
    public static String pathToSave = "/Desktop/schule/Entschuldigungen/allgemein/automat/done/";
    public static ArrayList<String> dates = new ArrayList<>();
    public static XWPFDocument doc;
    public static boolean einzelnesDatum = true;
    public static ArrayList<String> filepaths = new ArrayList<>();
    public static ArrayList<XWPFDocument> docs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Main_Frame frame = new Main_Frame();
        frame.frame_done();
    }

    public static void run() throws IOException{
        doc = Read_Edit_docx.readDocx();

        if(einzelnesDatum){
            Read_Edit_docx.editDocx(doc, "(Datum)", date);
            Read_Edit_docx.editDocx(doc, "(Name)", student_name);
            SaveFile.save(doc, date);
            doc.close();

        }else if(!einzelnesDatum){
            for (String s : dates) {
                XWPFDocument doc2 = Read_Edit_docx.readDocx();

                System.out.println("hier mit dem String " + s);

                Read_Edit_docx.editDocx(doc2, "(Datum)", s);
                Read_Edit_docx.editDocx(doc2, "(Name)", student_name);
                SaveFile.save(doc2, s);
                docs.add(doc2);
            }
        }

        for( XWPFDocument doc : docs){
            doc.close();
        }


        XWPFDocument bigDoc = MergeDocs.merge(filepaths);

        File file = new File(pathToSave +"/print/printable.docx");
        File parentDir = file.getParentFile();

        if (!parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new IOException("Failed to create parent directories.");
            }
        }

        FileOutputStream fos1 = new FileOutputStream(pathToSave +"/print/printable.docx");
        bigDoc.write(fos1);
        bigDoc.close();
        fos1.close();

        try {
            PrintDocument.print(pathToSave);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}