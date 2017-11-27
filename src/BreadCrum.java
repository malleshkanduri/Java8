import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class BreadCrum {
	
	private static final String DEVIDE = " -> ";
	static Map<String, String> map = new LinkedHashMap<>();
	static Map<String, Detail> link = new LinkedHashMap<>();
	
	public static void main(String[] args) throws Exception {
		
		/**
		 * Home:HomeDetail;home.jsp
		 * Guest;Guesh.jsp
		 * Home|Subscribers:My Subscribers;A.jsp
		 * Home|B;B.jsp
		 * Home|A|1;1.jsp 
		 * Home|A|2;2.jsp
		 * Home|B|11;11.jsp 
		 * Home|B|12;12.jsp
		 * Home|A|1|aa;aa.jsp
		 * Home|A|1|ab;ab.jsp
		 */
		
		Scanner scanner = new Scanner(new File("C:\\dev\\workspace-sts\\dev\\0_dev\\src\\input.txt"));
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			//System.out.println(line);
			
			String[] split = line.split("\\|");
			
			String process = split[split.length - 1];
			//	System.out.println(process);
			//  Split key and URL
			String[] keyAndUrl = process.split(";");
			String url= keyAndUrl[1];
			url = url.trim();
			// check key has description
			String[] keyAndDesc = keyAndUrl[0].split(":");
			String key = keyAndDesc[0];
			String desc = key;
			if (keyAndDesc.length == 2) {
				desc = keyAndDesc[1];
			}
			//System.out.println(key + "  " + desc + " " + url);
			String append = "";
			for (int index = 0; index < split.length -1; index ++) {
				append += split[index] + "|";
			}
			 
			key = append+key;
			
			//System.out.println(key);
			map.put(key, url);
			
			String searchKey; 
			
			if (append.isEmpty()) {
				searchKey = null;
			} else {
				searchKey = append.substring(0, append.lastIndexOf("|"));
			}
			link.put(url, new Detail(desc, searchKey));
		}
		printReferences(map);
		System.out.println("---");
		printUrls(link);
		System.out.println("---");
		scanner.close();
		
		
		getCompleteUlr("Guesh.jsp");
		getCompleteUlr("ab.jsp");
		getCompleteUlr("2.jsp");
		getCompleteUlr("12.jsp");
		getCompleteUlr("ab.jsp");
		getCompleteUlr("12.jsp");
		getCompleteUlr("home.jsp");
		getCompleteUlr("A.jsp");
	}
	
	private static void getCompleteUlr(String string) {
		System.out.println(string + " -> " + getCompleteUlr2(string));
	}




	private static String getCompleteUlr2(String url) {
		url = url.trim();
		String anchor = "<a href='%s'>%s</a>";
		Detail detail = link.get(url);
		String output = detail.getDesc();
		String parent = detail.getReference();
		if (parent == null) {
			return "";
		}
		while (parent != null) {
			String parentUrl = map.get(parent);
			detail = link.get(parentUrl);
			output = String.format(anchor, parentUrl, detail.getDesc()) + DEVIDE + output;
			parent = detail.getReference();
		}
		return output;
	}

	private static void printUrls(Map<String, Detail> map) {
		Set<String> keySet = map.keySet();
		
		for (String key : keySet) {
			Detail detail = map.get(key);
			System.out.println(key + "\t" +  detail.getDesc() + "\t" + detail.getReference());
		}
	
	}
	

	private static void printReferences(Map<String, String> map) {
		Set<String> keySet = map.keySet();
		for(String key : keySet) {
			System.out.println(key + "\t" + map.get(key));
		}
	}

	static class Detail {
		String desc;
		String reference;
		
		public Detail(String desc, String url) {
			super();
			this.desc = desc;
			this.reference = url;
		}
		
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getReference() {
			return reference;
		}

		public void setReference(String reference) {
			this.reference = reference;
		}
	}
}