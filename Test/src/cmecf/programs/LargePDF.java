package cmecf.programs;

import java.io.File;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

public class LargePDF {

	private static int rows = 50000;

	private static void createPdf() {

		try {

			File file = new File("c:/tmp/large.pdf");
			PdfWriter writer = new PdfWriter(file);
			PdfDocument pdfDoc = new PdfDocument(writer);
			Document doc = new Document(pdfDoc);
			
			Table table = new Table(20);
			while(rows > 0) {
				rows --;
				table.addCell("Name "+rows);
				table.addCell("Address "+rows);
				table.addCell("Phone "+rows);
				table.addCell("State "+rows);
				table.addCell("Zip "+rows);
				table.addCell("Name "+rows);
				table.addCell("Address "+rows);
				table.addCell("Phone "+rows);
				table.addCell("State "+rows);
				table.addCell("Zip "+rows);
				table.addCell("Name "+rows);
				table.addCell("Address "+rows);
				table.addCell("Phone "+rows);
				table.addCell("State "+rows);
				table.addCell("Zip "+rows);
				table.addCell("Name "+rows);
				table.addCell("Address "+rows);
				table.addCell("Phone "+rows);
				table.addCell("State "+rows);
				table.addCell("Zip "+rows);
			}
			doc.add(table);
			doc.close();
			System.out.println(file.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		createPdf();
	}

}
