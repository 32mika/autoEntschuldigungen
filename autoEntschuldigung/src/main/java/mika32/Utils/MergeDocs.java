package mika32.Utils;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergeDocs {
    public static XWPFDocument merge(ArrayList<String> filePaths) {
        ArrayList<XWPFDocument> docs = new ArrayList<>();
        for (String s : filePaths) {
            try {
                FileInputStream fis = new FileInputStream(s);
                docs.add(new XWPFDocument(fis));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        XWPFDocument mergedDocument = mergeDocuments(docs);
        System.out.println("Documents merged successfully.");
        return mergedDocument;

    }

    private static XWPFDocument mergeDocuments(List<XWPFDocument> documents) {
        XWPFDocument mergedDocument = new XWPFDocument();

        for (XWPFDocument document : documents) {
            copyBodyElements(document, mergedDocument);
            addPageBreak(mergedDocument);
        }

        return mergedDocument;
    }

    private static void copyBodyElements(XWPFDocument sourceDocument, XWPFDocument targetDocument) {
        List<XWPFParagraph> paragraphs = sourceDocument.getParagraphs();
        List<XWPFTable> tables = sourceDocument.getTables();

        for (XWPFParagraph paragraph : paragraphs) {
            XWPFParagraph newParagraph = targetDocument.createParagraph();
            newParagraph.getCTP().set(paragraph.getCTP());
        }

        for (XWPFTable table : tables) {
            XWPFTable newTable = targetDocument.createTable();
            newTable.getCTTbl().set(table.getCTTbl());
        }
    }

    private static void addPageBreak(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addBreak(BreakType.PAGE);
    }
}