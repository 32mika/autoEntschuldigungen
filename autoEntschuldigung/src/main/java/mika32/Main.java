package mika32;

import mika32.GUI.Main_Frame;
import mika32.GUI.openWindow;
import mika32.Save.save;
import mika32.Utils.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static Config config;
    public static boolean safeCon = false;
    public static String date = "27.06.2023";
    public static String straßeNr = "";
    public static String plzOrt = "";
    public static String student_name = "Mika Dobrowolski";
    public static String pathToSave = System.getProperty("user.dir") + "/Entschuldigungen";
    public static ArrayList<String> dates = new ArrayList<>();
    public static XWPFDocument doc;
    public static boolean einzelnesDatum = true;
    public static ArrayList<String> filepaths = new ArrayList<>();
    public static ArrayList<XWPFDocument> docs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        config = new Config();
        Main_Frame frame = new Main_Frame();
        save.setAll(frame);
        frame.frame_done();
    }

    public static void run() throws IOException{
        doc = Read_Edit_docx.readDocx();

        if(einzelnesDatum){
            Read_Edit_docx.editDocx(doc, "(Datum)", date);
            Read_Edit_docx.editDocx(doc, "(Name)", student_name);
            Read_Edit_docx.editDocx(doc, "(StrNr)", straßeNr);
            Read_Edit_docx.editDocx(doc, "(PlzOrt)", plzOrt);
            SaveFile.save(doc, date);
            doc.close();

        }else if(!einzelnesDatum){
            for (String s : dates) {
                XWPFDocument doc2 = Read_Edit_docx.readDocx();

                System.out.println("hier mit dem String " + s);

                Read_Edit_docx.editDocx(doc2, "(Datum)", s);
                Read_Edit_docx.editDocx(doc2, "(Name)", student_name);
                Read_Edit_docx.editDocx(doc2, "(StrNr)", straßeNr);
                Read_Edit_docx.editDocx(doc2, "(PlzOrt)", plzOrt);
                SaveFile.save(doc2, s);
                docs.add(doc2);
            }
        }

        for( XWPFDocument doc : docs){
            doc.close();
        }


        XWPFDocument bigDoc = MergeDocs.merge(filepaths);

        File file = new File(pathToSave +"/Entschuldigungen/print/printable.docx");
        File parentDir = file.getParentFile();


        try{
            if (!parentDir.exists()) {
                boolean created = parentDir.mkdirs();
            }
        }catch (Exception e){
            System.out.println("Throw an " + e + " Exception");
        }


        if(safeCon){
            save.saveAll();
            config.save();
        }


        FileOutputStream fos1 = new FileOutputStream(pathToSave +"/Entschuldigungen/print/printable.docx");
        bigDoc.write(fos1);
        bigDoc.close();
        fos1.close();

        try {
            PrintDocument.save(pathToSave);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        openWindow.sucsess();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.exit(1);
    }

    public static Config getConfig1() {
        //
        return config;
    }
}
