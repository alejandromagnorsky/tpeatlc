public class Element implements Comparable<Element>{

	private int id;
	private String name;

	public Element(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
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
	
	@Override
	public int compareTo(Element o) {
		return this.getName().compareTo(o.getName());
	}
	
}
