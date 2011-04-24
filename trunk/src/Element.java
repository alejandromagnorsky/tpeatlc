/*import java.util.ArrayList;
import java.util.List;*/

public class Element implements Comparable<Element>{

	private int id;
	private String name;
	//private DerivedElements derived;

	public Element(int id, String name/*, List<Element> l*/) {
		this.id = id;
		this.name = name;
		//this.derived = new DerivedElements(l);
	}
	
	/*public Element(int id, String name) {
		this.id = id;
		this.name = name;
		this.derived = new DerivedElements();
	}*/

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	/*public List<Element> getDerived(){
		return this.derived.getList();
	}*/
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (id != other.id && name != other.name)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Element [id=" + id + ", name=" + name + "]";
	}

	/*private class DerivedElements {
		List<Element> l;
		
		public DerivedElements(){
			this.l = new ArrayList<Element>();
		}
		
		public DerivedElements(List<Element> l){
			this.l = new ArrayList<Element>();
			this.l.addAll(l);
		}
		
		public List<Element> getList(){
			return this.l;
		}
	}*/

	@Override
	public int compareTo(Element o) {
		return this.getName().compareTo(o.getName());
	}
	
}
