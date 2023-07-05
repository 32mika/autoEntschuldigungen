package mika32.Utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PrintDocument {

    public static void save(String path) throws Exception {
        // Set the document file path
        System.out.println(path);
        openExplorer(path);

    }

    public static void openExplorer(String directoryPath) {
        File directory = new File(directoryPath);

        if (Desktop.isDesktopSupported() && directory.exists()) {
            try {
                Desktop.getDesktop().open(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
