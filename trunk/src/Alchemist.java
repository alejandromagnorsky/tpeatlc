import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Alchemist {

	private List<Element> elements;
	private HashMap<Potion, Element> magic;
	private SpellBook spellbook;

	public Alchemist(String elements, String magic) {
		this.elements = ElementLoader.load(elements);
		this.magic = MagicLoader.load(elements, magic);
		this.spellbook = SpellBook.getInstance();
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
	
	public void printTerminalElements(){
		List<Element> l = this.spellbook.getTerminalElements(elements, magic);
		Collections.sort(l);
		for (Element e : l)
			System.out.println(e.getName());
	}
	
	public void printDerivedElements(String e){
		Element element = getElement(e);		
		if (element == null){
			System.out.println("/!\\ No existe el elemento '" + e + "'.");
		} else {
			List<Element> l = this.spellbook.getDerivedElements(element, magic);
			if (!l.isEmpty()){
				Collections.sort(l);
				for (Element elem : l)
					System.out.println(elem.getName());
			} else
				System.out.println(e + " es terminal.");
		}			
	}
	
	public void printBasicIngredientsFromElement(String e){
		Element element = getElement(e);		
		if (element == null){
			System.out.println("/!\\ No existe el elemento '" + e + "'.");
		} else {
			List<Element> l = this.spellbook.getBasicIngredientsFromElement(element, magic);
			if (!l.isEmpty()){
				Collections.sort(l);
				for (Element elem : l)
					System.out.println(elem.getName());
			} else
				System.out.println(e + " es básico.");
		}
	}
	
	public void printBasicElements(){
		List<Element> l = this.spellbook.getBasicElements(elements, magic);
		Collections.sort(l);
		for (Element elem : l)
			System.out.println(elem.getName());
	}
	
	private Element getElement(String e){
		for (Element n : elements)
			if (n.getName().equals(e))
				return n;
		return null;
	}
}
