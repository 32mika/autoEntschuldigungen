package mika32.Utils;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathValidation {
    public static boolean test(String pathString){
        try {
            Path path = Paths.get(pathString);
            return true;
        } catch (InvalidPathException e) {
            System.out.println("Der Pfad ist ungültig: " + e.getMessage());
            return false;
        }
    }
}
