package mika32.Utils;

import mika32.Main;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFile {
    public static void save(XWPFDocument doc, String date) {
        try {
            FileOutputStream fos = new FileOutputStream(Main.pathToSave + "/Entschuldigungen/Entschuldigung vom " + date + ".docx");
            Main.filepaths.add(Main.pathToSave + "/Entschuldigungen/Entschuldigung vom " + date + ".docx");
            doc.write(fos);

        }catch (Exception e){
           System.out.println("Fehler Pfard nicht gefunden");
           return;
        }
        System.out.println("File succesfully saved!");
    }
}
