package fr.excilys.dtos;

public class ComputerDTO {
	
	
	private long id;
	private String name;
	private String introduced;
	private String discontinued;
	private long computerId;
	
	public ComputerDTO(long id, String name,String introduced, String discontinued, long computerId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.computerId = computerId;
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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public long getComputerId() {
		return computerId;
	}

	public void setComputerId(long computerId) {
		this.computerId = computerId;
	}


}
