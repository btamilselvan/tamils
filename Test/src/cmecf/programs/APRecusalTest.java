package cmecf.programs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class APRecusalTest {

	private static void extract2() {
		Map<String, Model1> map = new HashMap<>();
		try {
			BufferedReader buff = new BufferedReader(new FileReader("C:\\Tamil\\temp\\211.csv"));
			String line = buff.readLine();
			while (line != null) {
				String[] arr = line.split(",");

				Model1 m1 = map.get(arr[0]);
				if (m1 == null) {
					m1 = new Model1(arr[1], arr[2], arr[0]);
					map.put(arr[0], m1);
				}
				if (arr[1].equals(arr[2])) {
					System.out.println(arr[5] + " " + arr[0]);
					m1.setcname("child");
				} else {
					m1.setpname("parent");
				}

				line = buff.readLine();
			}
			buff.close();
			System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		extract2();
	}
}

class Model1 {
	String prid;
	String cld;
	String rrId;
	String cname;
	String pname;

	public Model1(String prid, String cld, String rrId) {
		super();
		this.prid = prid;
		this.cld = cld;
		this.rrId = rrId;
	}

	public void setcname(String cname) {
		this.cname = cname;
	}

	public void setpname(String pname) {
		this.pname = pname;
	}

	@Override
	public String toString() {
		return "Model1 [prid=" + prid + ", cld=" + cld + ", rrId=" + rrId + ", cname=" + cname + ", pname=" + pname
				+ "]";
	}

}
