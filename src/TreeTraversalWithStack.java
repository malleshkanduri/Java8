import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/*
 * Travers tree without recurssion, using STACK.
 */

public class TreeTraversalWithStack {

	public static void main(String[] args) {
		Map<String, Reference> map = new HashMap();
		List<Reference> bread = new LinkedList<>();
		
		/**
		 * Home;home.jsp
		 * Home|A;A.jsp
		 * Home|B;B.jsp
		 * Home|A|1;1.jsp 
		 * Home|A|2;2.jsp
		 * Home|B|11;11.jsp 
		 * Home|B|12;12.jsp
		 * Home|A|1|aa;aa.jsp
		 * Home|A|1|ab;ab.jsp
		 *  
		 */

		Reference aa = new Reference();
		aa.setName("aa");
		aa.setJspPage("aa.jsp");
		aa.setChild(null);
		
		Reference ab = new Reference();
		ab.setName("ab");
		ab.setJspPage("ab.jsp");
		ab.setChild(null);
		
		bread.add(aa);
		bread.add(ab);
		
		Reference a1= new Reference();
		a1.setName("1");
		a1.setJspPage("1.jsp");
		a1.setChild(bread);
		
		Reference a2= new Reference();
		a2.setName("2");
		a2.setJspPage("2.jsp");
		a2.setChild(null);
		
		bread = new LinkedList<>();
		bread.add(a1);
		bread.add(a2);
		
		Reference A = new Reference();
		A.setName("A");
		A.setJspPage("A.jsp");
		A.setChild(bread);
		
		
		Reference b11= new Reference();
		b11.setName("11");
		b11.setJspPage("11.jsp");
		b11.setChild(null);
		
		Reference b12= new Reference();
		b12.setName("12");
		b12.setJspPage("12.jsp");
		b12.setChild(null);
		
		bread = new LinkedList<>();
		bread.add(b11);
		bread.add(b12);
		
		Reference B = new Reference();
		B.setName("B");
		B.setJspPage("B.jsp");
		B.setChild(bread);
		
		List<Reference> HomeList  = new LinkedList();
		HomeList.add(A);
		HomeList.add(B);
		
		Reference home = new Reference();
		home.setName("Home");
		home.setJspPage("home.jsp");
		home.setChild(HomeList);
		
		bread = new LinkedList<>();
		bread.add(home);

		lprintValues(home);
	}
	
	
	
	private static void lprintValues(Reference home) {
		Stack<Object> stack = new Stack<>();
		
		printValue(home);
		
		List<Reference> child = home.getChild();

		if (child != null && !child.isEmpty()) {
				stack.push(child);
				stack.push(0);
		}
		
		while(!stack.isEmpty()) {
			int index = (Integer)stack.pop();
			child = (List<Reference>)stack.pop();
			if (child.size() - 1 > index) {
				stack.push(child);
				stack.push(index + 1);
			}
			
			Reference ref = child.get(index);
			printValue(ref);

			child = ref.getChild();
			if (child != null && !child.isEmpty()) {
				stack.push(child);
				stack.push(0);
			}
		}
		
	 
	}
	
 	private static void printValue(Reference ref) { 
 		System.out.println(ref.getName() + "  " + ref.getJspPage());
 	}



	static class Reference {
		
		public List<Reference> getChild() {
			return child;
		}
		public void setChild(List<Reference> child) {
			this.child = child;
		}
		String name;
		String jspPage;
		List<Reference> child;
		
		public String getJspPage() {
			return jspPage;
		}
		public void setJspPage(String jspPage) {
			this.jspPage = jspPage;
		}
		Reference nextElement;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Reference getNextElement() {
			return nextElement;
		}
		public void setNextElement(Reference nextElement) {
			this.nextElement = nextElement;
		}
	}

}
