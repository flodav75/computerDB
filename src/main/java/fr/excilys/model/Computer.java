package fr.excilys.model;

import java.util.Date;

public class Computer {

	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;

	public Computer(long id, String name, Date introduced, Date discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	public Computer() {
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		String company = "marche pas";
		if(this.company != null) {
			company = this.company.toString();
		}
		return "Computer id: " + this.id + " name" + this.name + " introduced: " + this.introduced + " discontinued: "
				+ this.discontinued+" company :"+this.company.getName();
	}

}
