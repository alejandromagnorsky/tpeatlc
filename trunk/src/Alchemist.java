import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Alchemist {

	private List<Element> elements;
	private HashMap<Potion, Element> magic;
	private SpellBook spellbook;

	public Alchemist(String elements, String magic) {
		this.elements = ElementsLoader.load(elements);
		this.magic = MagicLoader.load(this.elements, elements, magic);
		this.spellbook = SpellBook.getInstance();
	}

	public Element merge(String elementName1, String elementName2) {
		Element e1 = getElement(elementName1);
		Element e2 = getElement(elementName2);
		if(e1 == null || e2 == null)
			return null;
		
		Potion potion = new Potion(e1, e2);
		return magic.get(potion);
	}
	
	public List<Element> getTerminalElements(){
		List<Element> l = this.spellbook.getTerminalElements(elements, magic);
		Collections.sort(l);
		return l;
	}
	
	public List<Element> getDerivedElements(String e){
		Element element = getElement(e);		
		if (element == null)
			return null;
		else {
			List<Element> l = this.spellbook.getDerivedElements(element, magic);
			Collections.sort(l);
			return l;
		}
	}
	
	public List<Element> getBasicIngredientsFromElement(String e){
		Element element = getElement(e);		
		if (element == null)
			return null;
		else {
			List<Element> l = this.spellbook.getBasicIngredientsFromElement(element, magic);
			Collections.sort(l);
			return l;
		}
	}
	
	public List<Element> getBasicElements(){
		List<Element> l = this.spellbook.getBasicElements(elements, magic);
		Collections.sort(l);
		return l;
	}
	
	private Element getElement(String e){
		for (Element n : elements)
			if (n.getName().equals(e))
				return n;
		return null;
	}
}
