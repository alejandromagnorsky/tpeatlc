import java.util.ArrayList;
import java.util.List;


public class Potion {

	private List<Element> elements;
	
	public Potion(Element e1, Element e2){
		elements = new ArrayList<Element>();
		if(e1.getId() < e2.getId()){
			this.elements.add(e1);
			this.elements.add(e2);
		} else {
			this.elements.add(e2);
			this.elements.add(e1);
		}
	}
	
	public Element get(int index){
		return elements.get(index);
	}
	
	public boolean has(Element e){
		return elements.contains(e);
	}

	@Override
	public int hashCode() {
		return (int)(Math.pow(2, elements.get(0).getId())*Math.pow(3, elements.get(1).getId()));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Potion other = (Potion) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Potion [" + elements + "]";
	}
}
