/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;


/**
 * @author Corinna
 */

/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.File;


/**
 * @author Corinna
 */

public class PdfCreator {
    
    public String createPdf(String path, String ingredients, String recipeTitle, String recipeDescription) throws DocumentException, IOException 
    {
        path+="shoppingList_"+recipeTitle+".pdf";
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        String[] ings = ingredients.split(";");

        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf = BaseFont.createFont();
                cb.beginText();
        Paragraph p = new Paragraph("Ingredients you'll need to buy:", new Font(FontFamily.HELVETICA, 20, Font.BOLD, new BaseColor(38,165,154)));
        p.add(Chunk.NEWLINE);
        document.add(p);

        cb.moveText(36, 750);
        cb.setFontAndSize(bf, 15);
        cb.endText();

        p = new Paragraph();
        for (String str : ings) {
            p.add("• ");
            p.add(str);
            p.add(Chunk.NEWLINE);
        }
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        document.add(p);

        p = new Paragraph(recipeTitle, new Font(FontFamily.HELVETICA, 20, Font.BOLD, new BaseColor(38, 165, 154)));
        p.add(Chunk.NEWLINE);
        document.add(p);
        p = new Paragraph(recipeDescription, new Font(bf, 12));
        p.add(Chunk.NEWLINE);
        document.add(p);

        document.close();
        return path;
    }
    }

