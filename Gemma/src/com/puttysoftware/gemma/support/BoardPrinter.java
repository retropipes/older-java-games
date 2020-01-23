/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;

/**
 *
 * @author wrldwzrd89
 */
public class BoardPrinter {
    private BoardPrinter() {
        // Do nothing
    }

    public static void printBoard(JFrame j) {
        try {
            Container c = j.getContentPane();
            Dimension d = c.getPreferredSize();
            BufferedImage bi = new BufferedImage(d.width, d.height,
                    BufferedImage.TYPE_INT_ARGB);
            c.paintComponents(bi.createGraphics());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "PNG", baos);
            byte[] data = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
            PrinterJob pj = PrinterJob.getPrinterJob();
            boolean okay = pj.printDialog(pras);
            if (okay) {
                PrintService service = pj.getPrintService();
                DocPrintJob job = service.createPrintJob();
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(bais, flavor, das);
                job.print(doc, pras);
            }
        } catch (IOException | PrintException | NullPointerException ioe) {
            CommonDialogs.showErrorDialog("Printing failed!",
                    "Print GameBoard");
        }
    }
}
