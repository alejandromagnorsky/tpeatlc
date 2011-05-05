package logic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SpellBook {
	private static SpellBook spellbook = new SpellBook();

	private SpellBook() {
	}

	public static SpellBook getInstance() {
		return spellbook;
	}

	/* Spell: get derived elements */
	public List<Element> getDerivedElements(Element e,
			HashMap<Potion, Element> magic) {
		List<Element> derived = new ArrayList<Element>();
		Iterator<Map.Entry<Potion, Element>> it = magic.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Potion, Element> pair = it.next();
			if (pair.getKey().has(e))
				if (!derived.contains(pair.getValue()))
					derived.add(pair.getValue());
		}
		return derived;
	}

	/* Spell: get terminal elements */
	public List<Element> getTerminalElements(List<Element> elements,
			HashMap<Potion, Element> magic) {
		List<Element> terminal = new ArrayList<Element>();
		for (Element e : elements) {
			boolean flag = false;
			Iterator<Potion> potions = magic.keySet().iterator();
			while (potions.hasNext()) {
				Potion p = potions.next();
				if (p.has(e)) {
					flag = true;
					break;
				}
			}
			if (!flag)
				terminal.add(e);
		}
		return terminal;
	}

	/* Spell: get basic elements required to form a given element */
	public List<Element> getBasicIngredientsFromElement(Element e,
			HashMap<Potion, Element> magic) {
		List<Element> required = new ArrayList<Element>();
		Iterator<Map.Entry<Potion, Element>> it = magic.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Potion, Element> pair = it.next();
			if (pair.getValue().equals(e)) {
				Potion p = pair.getKey();
				if (!magic.containsValue(p.get(0))
						&& !required.contains(p.get(0)))
					required.add(p.get(0));
				if (!magic.containsValue(p.get(1))
						&& !required.contains(p.get(1)))
					required.add(p.get(1));
			}
		}
		return required;
	}

	public List<Element> getBasicElements(List<Element> elements,
			HashMap<Potion, Element> magic) {
		List<Element> ans = new ArrayList<Element>();
		for (Element e : elements)
			if (!magic.values().contains(e))
				ans.add(e);
		return ans;
	}
}
