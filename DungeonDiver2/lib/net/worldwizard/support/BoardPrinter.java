/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.worldwizard.support;

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

import net.worldwizard.commondialogs.CommonDialogs;

/**
 *
 * @author wrldwzrd89
 */
public class BoardPrinter {
    private BoardPrinter() {
        // Do nothing
    }

    public static void printBoard(final JFrame j) {
        try {
            final Container c = j.getContentPane();
            final Dimension d = c.getPreferredSize();
            final BufferedImage bi = new BufferedImage(d.width, d.height,
                    BufferedImage.TYPE_INT_ARGB);
            c.paintComponents(bi.createGraphics());
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "PNG", baos);
            final byte[] data = baos.toByteArray();
            final ByteArrayInputStream bais = new ByteArrayInputStream(data);
            final PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            final DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
            final PrinterJob pj = PrinterJob.getPrinterJob();
            final boolean okay = pj.printDialog(pras);
            if (okay) {
                final PrintService service = pj.getPrintService();
                final DocPrintJob job = service.createPrintJob();
                final DocAttributeSet das = new HashDocAttributeSet();
                final Doc doc = new SimpleDoc(bais, flavor, das);
                job.print(doc, pras);
            }
        } catch (final IOException ioe) {
            CommonDialogs
                    .showErrorDialog("Printing failed!", "Print GameBoard");
        } catch (final PrintException pe) {
            CommonDialogs
                    .showErrorDialog("Printing failed!", "Print GameBoard");
        } catch (final NullPointerException npe) {
            CommonDialogs
                    .showErrorDialog("Printing failed!", "Print GameBoard");
        }
    }
}
