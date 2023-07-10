package mika32.Save;

import mika32.Main;
import mika32.Utils.Config;
import mika32.Main;
import mika32.GUI.Main_Frame;

import java.awt.*;

public class save {
    static String name;
    static String speicherPath;
    static String ortPlz;
    static String straßeHsNr;

    public static void saveAll(){
        Config config = Main.getConfig1();

        config.getConfig().set("autoEntschuldigung.name", name);
        config.getConfig().set("autoEntschuldigung.path", speicherPath);
        config.getConfig().set("autoEntschuldigung.ortPlz", ortPlz);
        config.getConfig().set("autoEntschuldigung.straßeHsNr", straßeHsNr);
    }

    public static void setAll(Main_Frame frame){
        Config config = Main.getConfig1();

        if(config.getConfig().contains("autoEntschuldigung.name")){
            name = config.getConfig().getString("autoEntschuldigung.name");
        }else {
            name = "";
        }

        if(config.getConfig().contains("autoEntschuldigung.ortPlz")){
            ortPlz = config.getConfig().getString("autoEntschuldigung.ortPlz");
        }else {
            ortPlz = "";
        }

        if(config.getConfig().contains("autoEntschuldigung.straßeHsNr")){
            straßeHsNr = config.getConfig().getString("autoEntschuldigung.straßeHsNr");
        }else {
            straßeHsNr = "";
        }

        if(config.getConfig().contains("autoEntschuldigung.path")){
            speicherPath = config.getConfig().getString("autoEntschuldigung.path");
        }else {
            speicherPath = "";
        }

        frame.setBoxText(name);
        frame.setOrtFieldText(ortPlz);
        frame.setStraßeFieldText(straßeHsNr);
        frame.setSpeichernFiel(speicherPath);
    }

    public static void setStudentname(String s){
        name = s;
    }

    public static void setOrtPlz(String s){
        ortPlz = s;
    }

    public static void setStraßeHsNr(String s){
        straßeHsNr = s;
    }

    public static void setSpeicherPath(String s){
        speicherPath = s;
    }
}
