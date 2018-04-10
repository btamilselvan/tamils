package com.success;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Test {

  
  private static void substring(){
    
 List<String> b = new ArrayList<>();
    System.out.println(Float.compare(10f, 11f));
    b.add("A");
    b.add("B");
    b.add("D");
    b.add("D");
    
    System.out.println("Size = "+b.subList(0, 3).size());
    
    System.out.println(Integer.parseInt(String.valueOf('1')));
    
    String ss = "01234";
    System.out.println(ss.substring(3));
    System.out.println(ss.substring(0,0));
    System.out.println(String.valueOf(new char[]{'a','b','d'}));
  }
  
  private static void queryString(){
    String pattern = "\\{,\\}";
    String qs = "\\{or\\},\\{Testing";
    String[] result = qs.split(pattern);
    for(String r : result){
      System.out.println(r);
    }
  }
  private static void releaseName(){
    String release = null;
    String releaseName = "NextGen CM/ECF Release 1.2 (Revision 1.2.0.1)";
    if (releaseName.indexOf("Revision") != -1 && releaseName.lastIndexOf(")") != -1 && releaseName.indexOf("Revision") + 8 < releaseName.lastIndexOf(")")) {
      release = releaseName.substring(releaseName.indexOf("Revision") + 8, releaseName.lastIndexOf(")"));
    }
    else if (releaseName.indexOf("Release") != -1) {
      release = releaseName.substring(releaseName.indexOf("Release") + 8);
    }
    System.out.println(release);
  }
  
  private static void compareList(){
    releaseName();
    List<String> a = new ArrayList<>();
    List<String> b = new ArrayList<>();
    
    a.add("A");
    a.add("B");
    a.add("D");
    
    
    b.add("B");
    b.add("A");
    b.add("C");
    

    if(a.size()!=b.size() || a.retainAll(b)){ 
      System.out.println("ne");
    }else{
      System.out.println("eq");
    }
    
    
    List<String> c = new ArrayList<>();
    c.add("AS");
    c.add("X");
    if(Collections.disjoint(a, c)){
      System.out.println("ne11");
    }else{
      System.out.println("eq11");
    }
    
    if(a.size()==b.size() ){/*
      if(a.retainAll(b)){
        System.out.println("true 1 ");  
      }else{
        System.out.println("false 1");
      }
      
    */}else{
//      System.out.println("false");
    }
    
  }
  
	private static void andTest(){
		String s1 = null;
		String s2 = "tes";
		if(s1==null & s2==null){
			System.out.println("1");
		}
		if(s1==null && s2==null){
			System.out.println("2");
		}
		s2 = null;
		if(s1==null && s2==null){
			System.out.println("3");
		}
	}
  
  private static void spilt(){
    
    String pattern = "ShowList\\(.*?\\)";
    String eventDescription = "CollectCaseNumbers('Multi');DetailCases('','1');ShowList('multi','mult1');#PickAttorney(1);ShowList('m2ulti','m24ult');EndScreen;ProcessNewDPF('multi');DocketText();CreateDktEntry();";
    eventDescription = eventDescription == null ? "" : eventDescription.replace("#ShowList", "");
   
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(eventDescription);

    while (m.find()) {
      String s = m.group();
      s = s.replace("ShowList(", "");
      s = s.replace(")", "");
      s = s.replace("'", "");
      System.out.println(s); 
    }
  }

	public static boolean isPrimeNumber(int number) {
		if (number == 2 || number == 3) {
			return true;
		}
		if (number % 2 == 0) {
			return false;
		}
		int sqrt = (int) Math.sqrt(number) + 1;
		for (int i = 3; i < sqrt; i += 2) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

  
	private static void regex(){
		String source = "<div>Test</div>";
		String strToRemove = "<";
		String replaceStr = "*";
		regexTest(source, strToRemove, replaceStr);
	}
	private static void regexTest(String source, String strToRemove, String replaceStr){
		String exp = "<+";
		Pattern p = Pattern.compile(exp);
		Matcher m = p.matcher(source);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
		    // For example: transform match to upper case
//		    String replacement = m.group().toUpperCase();
		    m.appendReplacement(sb, replaceStr);
		}
		System.out.println(sb.toString());
	}
	private static void formatDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_WEEK, -3);
		System.out.println(cal.getTime());
		System.out.println(sdf.format(cal.getTime()));
		
		Map<String, String> map = new HashMap<>();
				
		
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.DAY_OF_WEEK, -4);
		System.out.println(cal1.getTime());
		System.out.println(sdf.format(cal1.getTime()));
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		cal2.add(Calendar.DAY_OF_WEEK, 366);
		System.out.println(cal2.getTime());
		System.out.println(sdf.format(cal2.getTime()));
		System.out.println(sdf.format(new Date()));
	}
	private static void formatDatee(){
		
//		System.out.println(StringEscapeUtils.unescapeHtml4("O&#039;Smith"));
		
		Calendar cal = Calendar.getInstance();
		int pollerInterval = 10500;
		  //process entries that are docketed atleast in the last 2 hours
		  if((pollerInterval/60)/60 < 2){
			  pollerInterval = 7200;
		  }
		  int hour = (pollerInterval/60)/60;
		  int minutes = (pollerInterval/60)%60;
		  if(hour>0){
			  cal.add(Calendar.HOUR, -hour);
		  }
		  if(minutes>0){	
			  cal.add(Calendar.MINUTE, -(minutes+20));
		  }
		  System.out.println(cal.getTime());
		  System.out.println(cal.get(Calendar.HOUR_OF_DAY));
		  System.out.println(cal.get(Calendar.MINUTE));
	}
	public static void splitList(){
		
		List<Integer> mainList = new ArrayList<>();
		mainList.add(1);mainList.add(15);mainList.add(16);mainList.add(81);mainList.add(3);
		mainList.add(51);mainList.add(175);mainList.add(196);mainList.add(841);mainList.add(31);
		
		List<List<Integer>> result = chunk(mainList, 14);
		System.out.println(result);
		
		String gpName = "E-Orders Courtroom Deputy".replaceAll("\\s+",""); 
		System.out.println(gpName);
		
	}
	
	
	public static <T> List<List<T>> chunk(List<T> in, int size) {
	    List<List<T>> lists = new ArrayList<>();
	    Iterator<T> i = in.iterator();
	    while (i.hasNext()) {
	        List<T> list = new ArrayList<>();
	        for (int j=0; i.hasNext() && j<size; j++) {
	            list.add(i.next());
	        }
	        lists.add(list);
	    }
	    return lists;
	}
	public static void split1(){
		splitList();
		String str1 = "!=2";
		System.out.println(str1.substring(2));
		String ss1 = "7,8,9";
		ss1 = ss1.replace(",", "|");
		String str = "ABER|ABR1";
		for(char c : ss1.toCharArray()){
			System.out.println((int)c);
		}
		System.out.println(ss1.replace("|", ","));
		for(String ss: ss1.split("\\|")){
			System.out.println(ss);
		}
	}
	public static void ol(boolean flag){
		System.out.println("boolean method");
	}
	public static void ol(Object flag){
		System.out.println("objectmethod");
		new Test().dateCompare();
	}
	private static void tt(){
		Set<Integer> ss = new HashSet<>();
		ss.add(1234);
		ss.add(1235);
		ss.add(null);
		ss.add(1234);
		System.out.println(ss.size());
	} 
	private void dateCompare(){
		String endDateStr = "09/29/2015";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date endDate;
		try {
			endDate = sdf.parse(endDateStr);
			boolean afterend = trimTime(Calendar.getInstance().getTime()).after(trimTime(endDate));
			boolean aaa = Calendar.getInstance().getTime().after(endDate);
			System.out.println(afterend);
			System.out.println(aaa);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removeUnavailableSealedCases() {
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm");
		System.out.println(dateFormat.format(new Date()));
		
		
		String sss = "order";
		String[] as = sss.split(";");
		System.out.println("length -- > "+as.length);
		
		String function = "('motion','multi');CollectCa";
		String doListValueInFunctionShowList = function.substring(2, function.indexOf("'", 2));
		System.out.println(doListValueInFunctionShowList);
		
		List<HeaderInfo> caseEntities = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			HeaderInfo hi = new HeaderInfo();
			hi.caseTitleText = "" + i;
			if (i == 0) {
				hi.caseTitleText = "una";
			}
			caseEntities.add(hi);
		}
		/*for (int i = 0; i < caseEntities.size()  ; i++) {
			if (caseEntities.get(i).caseTitleText.equals("una")) {
				caseEntities.remove(i);
			}
		}*/
		for (int i = caseEntities.size() - 1; i > 0; i--) {
			if (caseEntities.get(i).caseTitleText.equals("una")) {
				caseEntities.remove(i);
			}
		}
		System.out.println(" caseEntities ->"+caseEntities.size());
	}
		private static void fil(){
			try {
				File f = File.createTempFile("fff", ".pdf");
				System.out.println(f.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private static void cap(){
			String one = "ee";
			String two = null;
			one.equalsIgnoreCase(two);
//			System.out.println(entityName("jrpacket_event"));
			
			
			String queryString = "insert into cmecf.entities.bankruptcy.PacketResults (filteredPacketid) select distinct a.jrpJrpacketid";
			System.out.println(queryString.substring(queryString.indexOf("into")+4,queryString.indexOf("select")));
			
		}
		/* private static String entityName(String table) {
			    return WordUtils.uncapitalize(WordUtils.capitalizeFully(table, new char[] { '_' }).replace("_", ""));
			  }*/
		private static void removeSpl(){
			
			
			String fileURL = "https://tjenie.ao.dcn/cms-ecf-cm1d/n/cmecfservices/rest/file/8030138624?Authentication__UserToken=1712506-30942-163437507861471.822602571808688&Authentication__UserIPAddressText=156.119.69.170";
			int index = fileURL.indexOf("?");
		      if(index!=-1){
		    	  String suffix = fileURL.substring(index);
		    	  String prefix = fileURL.substring(0,index);
		    	  prefix = prefix.concat("/info");
		    	  prefix = prefix.concat(suffix);
		    	  System.out.println(prefix);
				}
			
			
			
			
//			String url = "http://woodstock.td.oca.ao.dcn:8080/bk/cmecfservices/rest/file/9100228568";
			String url = "https://tjenie.ao.dcn/cms-ecf-cm1d/n/cmecfservices/rest/file/8030138624?Authentication__UserToken=1712506-30942-163437507861471.822602571808688&Authentication__UserIPAddressText=156.119.69.170";
			index = url.indexOf("?");
			if(index!=-1){
				String suffix = url.substring(index);
				String prefix = url.substring(0,index);
				prefix = prefix.concat("/info");
				prefix = prefix.concat(suffix);
				System.out.println(prefix);
			}else{
				System.out.println(url);
			}
		}
	 public static Date getMonthStartDate(){
		  
		  Date today = new Date();  
	      Calendar calendar = Calendar.getInstance();  
	      calendar.setTime(today);  
//	      calendar.set(Calendar.DAY_OF_MONTH, 1);  
	      calendar.set(Calendar.HOUR_OF_DAY, 0);
	      calendar.set(Calendar.MINUTE, 0);
	      calendar.set(Calendar.SECOND, 0);
	      calendar.set(Calendar.MILLISECOND, 0);
	      return calendar.getTime();
	  }

	 
	 private static void dateee(){
		 SimpleDateFormat  sdf = new SimpleDateFormat("MM/dd/yyyy");
		 
		 try {
			Date hearingDate = sdf.parse(sdf.format(new Date()));
			System.out.println(hearingDate);
			int i = getMonthStartDate().compareTo(hearingDate);
			System.out.println("iiiiiii-->"+i);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	 }
	 
	 
	private static void datee(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
			String dd = sdf.format(new Date());
			System.out.println(dd);
			
			Date date = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa").parse("30-Mar-15 12.21.54.233 PM");
			System.out.println(date);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private static void isWithinCurrentWeek(){
		Calendar givenDateCal = Calendar.getInstance();
		givenDateCal.add(Calendar.DAY_OF_YEAR, 5);
		Date givenDate = givenDateCal.getTime();
		System.out.println("Given date--> "+givenDate);
		  Calendar c = Calendar.getInstance();
		  c.setFirstDayOfWeek(Calendar.SUNDAY);

		  c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		  c.set(Calendar.HOUR, 0);
		  c.set(Calendar.MINUTE, 0);
		  c.set(Calendar.SECOND, 0);
		  c.set(Calendar.MILLISECOND, 0);

		  Date sunday = c.getTime();

		  Date nextSunday= new Date(sunday.getTime()+7*24*60*60*1000);
		  boolean flag = false;
		  flag = (givenDate.after(sunday) && givenDate.before(nextSunday)) || givenDate.equals(sunday);
		  System.out.println("flag --> "+flag);
	  }
	
	
	public static void convertToDate(){
		String dateStr = "10/28/2014";
		  try {
			  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			 System.out.println("converted date --->"+sdf.parse(dateStr));
		} catch (Exception e) {
		}
	  }
	
	private static void isWithinCurrentMonth(){
		Calendar givenDateCal = Calendar.getInstance();
		givenDateCal.set(Calendar.HOUR, 0);
		givenDateCal.set(Calendar.MINUTE, 0);
		givenDateCal.set(Calendar.SECOND, 0);
		givenDateCal.set(Calendar.MILLISECOND, 0);
		givenDateCal.add(Calendar.DAY_OF_YEAR, -17);
		Date givenDate = givenDateCal.getTime();
		System.out.println("Given date--> "+givenDate);
		
		Date today = new Date();  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(today);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        calendar.add(Calendar.DATE, -1); 
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date firstDayOfMonth = calendar.getTime(); 
        System.out.println("firstDayOfMonth date--> "+firstDayOfMonth);
        
		  boolean flag = false;
		  flag = givenDate.after(firstDayOfMonth)  || givenDate.equals(today);
		  System.out.println("flag --> "+flag);
	  }
	private static void readFile(){
	  
	  String abc = null;
	  System.out.println("Hello "+abc);
	  
    BufferedReader br = null;
     
    try {
//      main1(null);
      String sCurrentLine;
      br = new BufferedReader(new FileReader("C:\\Tamil\\WinSCP_Docs\\cm8b\\localhost_access_log.2017-02-23.txt"));
      while ((sCurrentLine = br.readLine()) != null) {
        if(sCurrentLine.contains("/d/cmecfservices/rest/rulescontrollercomposite/rulescheck")){
          System.out.println(sCurrentLine);
        }
      }
 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)br.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
	/*private static void readFile(){
		BufferedReader br = null;
		 
		try {
//			main1(null);
			String sCurrentLine;
			System.out.println("Tes2t");
			br = new BufferedReader(new FileReader("C:\\Tamil\\docs\\Tech_Debt\\ORB_Logs\\catalina_MITRE2.txt"));
			boolean flag = false;
			int i =0;
			while ((sCurrentLine = br.readLine()) != null) {
				if(flag){
					System.out.println(sCurrentLine);
					i++;
				}
				if(i==5){
					System.out.println("\n\n\n");
					i=0;
					flag = false;
				}
				if(!sCurrentLine.contains("edw") && !sCurrentLine.contains("For input string: \"{TBD}\"") && !sCurrentLine.contains("Statement length exceeds maximum.") && 
						!sCurrentLine.contains("Connection has already been closed")
						&& (sCurrentLine.contains("Exception") || sCurrentLine.contains("exception"))){
					System.out.println(sCurrentLine);
					flag = true;
				}
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}*/
	private void escapeUnescapeXML(){
		
		Boolean ss = false;
		
		System.out.println(" ss  "+ "".equals(ss));
		
		/*System.out.println(StringEscapeUtils.escapeXml("bread&butter"));
		System.out.println("Hello   ..");
		System.out.println(StringEscapeUtils.unescapeXml("bread&ampbutter"));*/
	} 
	
	private void insertMiddle(){
		String ss  = "Hearing Set On (RE: related document(s)<ba href='https://ecf.cm1b.aocms.uscourts.gov/doc1/9220337924'>1</a> Chapter 9 Voluntary Petition .";
		int index = ss.indexOf("<a");
		if(index!=-1){
			String begin = ss.substring(0,index+2);
			String end = ss.substring(index+2);
			System.out.println(begin);
			System.out.println(end);
			String finalString = begin.concat(" target='_new' ").concat(end);
			System.out.println(finalString);	
		}
		
	}
	private static void formatCaseNumberDisplay(){
		String SEALED = "*SEALED*";
		String caseNumber = "Test *SEALED* case";
		int index = -1;
		  if(caseNumber.contains(SEALED)){
			  index = caseNumber.indexOf(SEALED);
			  String prefix = caseNumber.substring(0,index);
			  String suffix = caseNumber.substring(index+SEALED.length(), caseNumber.length());
			  caseNumber = prefix + suffix;
		  }		
		System.out.println(caseNumber);
	  }
	private static void trim(){
		String ss = "Test <br/> hello <br/><br> Testing <br/><br>hh<BR>";
		String sss= ss.replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
		System.out.println(sss);
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args)  {
	  substring();
	  queryString();
	  compareList();
		if(true){
			return;
		}
	  readFile();
		readFile();
		spilt();
		
		formatDatee();
    regex();
		formatDate();
		split1();
		ol(true);
		ol(new Object());
		tt();
		List<String> list = new ArrayList<String>();
        list.add("alex");
        list.add("ball");
        list.add("AlEx");
        list.add("alEx");
        list.add("BaLL");
        System.out.println(list);
        Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                        if (o1.toLowerCase().equals(o2.toLowerCase())) {
                                if (o1.toLowerCase().equals(o1)) {
                                        return -1;
                                } else {
                                        return  1;
                                }
                        } else {
                                return o1.toLowerCase().compareTo(o2.toLowerCase());

                        }
                }
        });
        System.out.println(list);
		
		
		
		
		
		
		
		
		
		
		
		new Test().removeUnavailableSealedCases();
		cap();
		fil();
		dateee();
		removeSpl();
		if(true){
			return;
		}
		datee();
		trim();
		convertToDate();
		isWithinCurrentMonth();
		System.out.println(5 + (Math.random() * (10 - 5)));
		Random ran = new Random();
		int x = ran.nextInt(6) + 5;
		System.out.println("XXX==?"+ran.nextInt());
		formatCaseNumberDisplay();
		
		String ss1 = "1.1";
		if(ss1.matches("[0-9]+")){
			System.out.println("Matchesds");
		}
		
		String fileName1 = "C:\\Users\\BalasubramaniamT\\Desktop\\new  8.txt";
		System.out.println(fileName1.substring(fileName1.lastIndexOf("\\")+1));
		
		Date date = null;
		if(date instanceof Date){
			System.out.println("dsa");
		}else{
			System.out.println("No");
		}
		
		String fileName = "text.txt.xlsx";
		System.out.println(fileName.substring(fileName.lastIndexOf(".")+1));
		
		new Test().insertMiddle();
		Map<String, String> aa = new HashMap<String, String>();
		aa.put("AAAAA", "AAAAAAAAAAAAAAAAAA");
		aa.put("BB", "BBBBBBBBBBBB");
		System.out.println(aa);
//		new Test().readFile();
		new Test().escapeUnescapeXML();
		// TODO Auto-generated method stub
		String sss2 = "80128745";
		Integer.parseInt(sss2);
//		new Test().fileTest();
//		new Test().srtingTest();
		System.out.println(new Test().calculateDueDate(40, "Days"));
		System.out.println(new Test().calculateDueDate(10, "Weeks"));
		String sss = "sss";
		System.out.println(sss.split(",").length);
		String ssss = "Hello";
		System.out.println(ssss.toCharArray().length);
		char[] arr = ssss.toCharArray();
		System.out.println(Arrays.asList(arr).size());

		List<String> dataFields = new ArrayList<>();
		dataFields.add("11");dataFields.add("11");dataFields.add("13");
		
		List<String> dataFields1 = new ArrayList<>();
		dataFields1.add("11");dataFields1.add("11");dataFields1.add("13"); dataFields1.add("12");
		
		if(dataFields1.size() > dataFields.size()){
			int size = dataFields1.size()/dataFields.size();
			int mod = dataFields1.size()%dataFields.size();
			if(mod==0){
				size --;
			}
			System.out.println(dataFields1.subList(dataFields.size()*size, dataFields1.size()));
		}
		
		int ii = 10+3*5/(16-4);
		System.out.println("---->"+ii);
		
		new Test().jsonArray();
		formatDate();
	}
	private void parseURL(){
		String tmpURL = "localhost:9090/cmecfservices/rest/composite/packet/859?Services=packet";
		StringBuilder url = new StringBuilder();
		int index = tmpURL.indexOf("/cmecfservices/rest/");
		String suffix  = tmpURL.substring(index, tmpURL.length());
		String url1 = "https://tjenie.ao.dcn/cms-ecf-cm1b/n"+suffix;
		System.out.println(url1);
	}
	private void parse(){
		String response = "[\"Success: {\"signed_orders\":\"\",\"proposed_orders\":\"\"}\"]";
		int firstIndex = response.indexOf("{");
	      int lastIndex = response.lastIndexOf("}");
	      String jsonResponse = response.substring(firstIndex, lastIndex + 1);
	      jsonResponse = jsonResponse.replace("\\", "");
		System.out.println("jsonResponse-->"+jsonResponse);
		
		String ip = "<a href='/cgi-bin/poClerkSign.pl?Y;1:09-bk-01407-NKC;;;;case+number;25' n                                title='Related p";
		String ss = ip.substring(ip.indexOf("'")+1);
		System.out.println(ss);
		String sss = ss.substring(0, ss.indexOf("'"));
		System.out.println(sss);
	}
	
	private void jsonArray(){
		String key = "test";
//		String[] val = new String[]{"111", "2222"};
		JSONArray arr = new JSONArray();
		arr.add("111");
		arr.add("122");
		JSONObject object = new JSONObject();
		object.put(key, arr);
		
		System.out.println(object.toJSONString());
		String ss = object.toJSONString();
		
		
	}
	
	private Date calculateDueDate(int dueDate, String selectedDueDateType){
		Calendar cal = Calendar.getInstance();
		if("Days".equals(selectedDueDateType)){
			cal.add(Calendar.DATE, dueDate);	
		}else{
			cal.add(Calendar.DATE, 7*dueDate);
		}
		return cal.getTime();
	}
	private void split(){
		String ss = "13316|n|y|y";
		int index = ss.indexOf("|");
		System.out.println("Index-->"+index);
		String [] sss = ss.split("\\|");
		for(String s: sss){
			System.out.println(s);
		}
	}
	private void iter(){
		List<String> tt = new ArrayList<>();
		tt.add("1");tt.add("51");tt.add("14");tt.add("21");
		
		Iterator<String> itr = tt.iterator();
		while(itr.hasNext()){
			itr.next();
			itr.remove();
		}
		System.out.println(tt.size());
	}
	
	private void fileTest(){
		File file;
		try {
			file = File.createTempFile("c://tamil//temp", ".txt");
			PrintWriter writer = new PrintWriter(file);
			writer.write("Hello...");
			writer.flush();
			writer.close();
			System.out.println(file.getAbsolutePath());
			System.out.println(file.getName());
			
			File file1= new File(file.getAbsolutePath());
			System.out.println(file1.getName());
			System.out.println(file1.getParent());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private void srtingTest(){
		String subject = null;
		String notes = "H e";
		
		if ("".equals(subject.trim())
				&& !"".equals(notes.trim())) {
			System.out.println("Subject mandatory");
		}
	}
	 class HeaderInfo{
	    	String caseNumber;
	    	String caseTitleText;
	    	String packetName;
	    	Integer caseDocNum;
	    	String filedDate;
	    	String docDescription;
	    	String fileName;
	    	String url;
	    }
	 private Date trimTime(Date date){
	      Calendar c = Calendar.getInstance();
	      c.setTime(date);
	      c.set(Calendar.HOUR_OF_DAY, 0);
	      c.set(Calendar.MINUTE, 0);
	      c.set(Calendar.SECOND, 0);
	      c.set(Calendar.MILLISECOND, 0);
	      return c.getTime();
	  }
	 public static void main1(String[] args) throws Exception
	    {
	        RandomAccessFile raf = new RandomAccessFile("C:\\Tamil\\WinSCP_Docs\\cm2b\\catalina.out", "r");
	        long numSplits = 8; //from user input, extract it from args
	        long sourceSize = raf.length();
	        long bytesPerSplit = sourceSize/numSplits ;
	        long remainingBytes = sourceSize % numSplits;

	        int maxReadBufferSize = 8 * 1024; //8KB
	        for(int destIx=1; destIx <= numSplits; destIx++) {
	            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split."+destIx));
	            if(bytesPerSplit > maxReadBufferSize) {
	                long numReads = bytesPerSplit/maxReadBufferSize;
	                long numRemainingRead = bytesPerSplit % maxReadBufferSize;
	                for(int i=0; i<numReads; i++) {
	                    readWrite(raf, bw, maxReadBufferSize);
	                }
	                if(numRemainingRead > 0) {
	                    readWrite(raf, bw, numRemainingRead);
	                }
	            }else {
	                readWrite(raf, bw, bytesPerSplit);
	            }
	            bw.close();
	        }
	        if(remainingBytes > 0) {
	            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split."+(numSplits+1)));
	            readWrite(raf, bw, remainingBytes);
	            bw.close();
	        }
	            raf.close();
	    }

	    static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException {
	        byte[] buf = new byte[(int) numBytes];
	        int val = raf.read(buf);
	        if(val != -1) {
	            bw.write(buf);
	        }
	    }
}
