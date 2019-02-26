package fr.excilys.model;

public class Company {

	private long id;
	private String name;

	public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Company(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company id : " + this.id + "Company name: " + this.name;
	}

}
