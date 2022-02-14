package cmecf.programs;

import java.io.BufferedReader;
import java.io.FileReader;

public class NonAscii {
	
	
	private static void stringTest() {
		String cacb = "<form method=\"POST\" enctype=\"multipart/form-data\" action=\"https://cms-ecf-cm3b-test.tsso.dcn/d/cmecfservices/rest/relay/adi\">";
		String cmmb = "<form method=\"POST\" enctype=\"multipart/form-data\" action=\"https://cms-ecf-cm3b-test.tsso.dcn/d/cmecfservices/rest/relay/adi\">";
//		cacb.chars().forEach(c -> System.out.println(String.valueOf((char)c) + " "+String.valueOf(c)));
//		System.out.println("==============================================================================");
//		cmmb.chars().forEach(c -> System.out.println(String.valueOf((char)c) + " "+String.valueOf(c)));

		System.out.println(cacb.equals(cmmb));
		System.out.println(cacb.contentEquals(cmmb));
	}

	private static void detectNonAscii() {
		try {

//			BufferedReader reader = new BufferedReader(new FileReader("c:/tamil/temp/wied.xml"));
			BufferedReader reader = new BufferedReader(new FileReader("c:/tamil/temp/flsd_mdl/rib.xml"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				boolean match = line.chars().allMatch(c -> c < 128);
				if (!match) {
					System.out.println(line);
				}
//				System.out.println(line);
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void detectNonAscii(String line) {
		try {
			line.chars().forEach(c -> {
				if(c>128) {
					System.out.println(String.valueOf((char)c));
					System.out.println(String.valueOf(c));
				}
			});
			boolean match = line.chars().allMatch(c -> c < 128);
			if (!match) {
				System.out.println(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		detectNonAscii();
//		detectNonAscii("A Disassociation of Counsel is not an acceptable document to withdraw your appearance.  Pursuant to SDFL LR 11.1(d)(3), no attorney shall withdraw the attorney’s appearance in any action or proceeding except by leave of Court.  Please file the appropriate document in all cases to withdraw your appearance.");
//		stringTest();
	}

}
