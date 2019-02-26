package fr.excilys.dtos;

public class ComputerDTO {

	private String id;
	private String computerName;
	private String introduced;
	private String discontinued;
	private String companyId;
	private String companyName;

	public ComputerDTO(String id, String name, String introduced, String discontinued, String computerId,
			String companyName) {
		this.id = id;
		this.setComputerName(name);
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = computerId;
		this.setCompanyName(companyName);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String computerId) {
		this.companyId = computerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

}
