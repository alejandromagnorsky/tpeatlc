import java.util.HashMap;
import java.util.List;

public class Alchemist {

	private List<Element> elements;
	private HashMap<Potion, Element> magic;

	public Alchemist(String elements, String magic) {
		this.elements = ElementLoader.load(elements);
		this.magic = MagicLoader.load(elements, magic);
	}

	public Element merge(String elementName1, String elementName2) {
		Element e1 = null, e2 = null;
		for(int i = 0; (e1 == null || e2 == null) && i < elements.size(); i++){
			if(elements.get(i).getName().equals(elementName1))
				e1 = elements.get(i);
			if(elements.get(i).getName().equals(elementName2))
				e2 = elements.get(i);
		}
		if(e1 == null || e2 == null)
			return null;
		
		Potion potion = new Potion(e1, e2);
		return magic.get(potion);
	}
}
