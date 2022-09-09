package cmecf.programs;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SwingHelloWorld {

  public static void main(String[] args) {

    JFrame frame = new JFrame("Hello World Java Swing");

    // set frame site
    frame.setMinimumSize(new Dimension(800, 600));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // center the JLabel
    JLabel lblText = new JLabel("Hello World!", SwingConstants.CENTER);

    // add JLabel to JFrame
    frame.getContentPane().add(lblText);

    JButton button = new JButton("  Print ");
    button.addActionListener((a) -> {
      System.out.println("Clicked");
      print();
    });
    frame.getContentPane().add(button);

    // display it
    frame.pack();
    frame.setVisible(true);

  }

  static void print() {
    try {
      System.out.println("print11...");
      PrinterJob pJob = PrinterJob.getPrinterJob();
      PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
      if (!pJob.printDialog(pras)) {
        System.out.println("close print dialog");
        return;
      }
      System.out.println("open print dialog");
      PrintService service = pJob.getPrintService();
      System.out.println("print service obtained");
      printFile("C:\\temp\\abc.txt", service, pras);
      System.out.println("print done");
    } catch (Exception e) {
      e.fillInStackTrace();
    }
  }

  static void printFile(String fileName, PrintService service, PrintRequestAttributeSet pras)
      throws FileNotFoundException, PrintException {
    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
    if (service == null) {
      PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
      PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

      if (defaultService == null) {
        System.out.println("No default service can be found!");
        if ((printService == null) || (printService.length == 0)) {
          System.out.println("No print service can be found!");
        }

        else {
          defaultService = printService[0];
        }
      }
      int index = -1;

      for (int i = 0; i < printService.length; i++) {
        if (printService[i] == defaultService) {
          index = i;
          break;
        }
      }
      if (index < 0)
        defaultService = printService[0];

      // later we may need to construct this object.
      GraphicsConfiguration gc = null;
      // service = defaultService;
      service = ServiceUI.printDialog(gc, 200, 200, printService, defaultService, flavor, pras);
    }
    if (service != null) {
      DocPrintJob job = service.createPrintJob();
      File fileFile = new File(fileName);
      FileInputStream fis = new FileInputStream(fileFile);
      DocAttributeSet das = new HashDocAttributeSet();
      Doc doc = new SimpleDoc(fis, flavor, das);
      job.print(doc, pras);
    }
  }
}
