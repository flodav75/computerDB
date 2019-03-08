package fr.excilys.dtos;

public class ComputerDTO {

	private String id;
	private String computerName;
	private String introduced;
	private String discontinued;
	private String companyId;
	private String companyName;

	private ComputerDTO(String id, String name, String introduced, String discontinued, String computerId,
			String companyName) {
		this.id = id;
		this.computerName = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = computerId;
		this.companyName = companyName;
	}

	private ComputerDTO() {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((computerName == null) ? 0 : computerName.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ComputerDTO)) {
			return false;
		}
		ComputerDTO other = (ComputerDTO) obj;
		if (companyId == null) {
			if (other.companyId != null) {
				return false;
			}
		} else if (!companyId.equals(other.companyId)) {
			return false;
		}
		if (companyName == null) {
			if (other.companyName != null) {
				return false;
			}
		} else if (!companyName.equals(other.companyName)) {
			return false;
		}
		if (computerName == null) {
			if (other.computerName != null) {
				return false;
			}
		} else if (!computerName.equals(other.computerName)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", computerName=" + computerName + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
	}

	public static class ComputerDTOBuilder {
		private String id = "0";
		private String computerName ="";
		private String introduced;
		private String discontinued;
		private String companyId;
		private String companyName = "";

		/**
		 * Build a Computer from the ComputerBuilder instance and returns it.
		 * 
		 * @return The created Computer object
		 */
		public ComputerDTO build() {
			ComputerDTO computer = new ComputerDTO();

			computer.setId(this.id);
			computer.setComputerName(this.computerName);
			computer.setIntroduced(this.introduced);
			computer.setDiscontinued(this.discontinued);
			computer.setCompanyId(this.companyId);
			computer.setCompanyName(this.companyName);

			return computer;
		}

		/**
		 * @param id The computer id
		 * @return The ComputerBuilder instance
		 */
		public ComputerDTOBuilder setId(String id) {
			this.id = id;
			return this;
		}

		/**
		 * @param name The computer name
		 * @return The ComputerBuilder instance
		 */
		public ComputerDTOBuilder setName(String name) {
			this.computerName = name;
			return this;
		}

		/**
		 * @param introduced The computer introduction date
		 * @return The ComputerBuilder instance
		 */
		public ComputerDTOBuilder setIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		/**
		 * @param discontinued The computer discontinution date
		 * @return The ComputerBuilder instance
		 */
		public ComputerDTOBuilder setDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		/**
		 * @param company The computer company
		 * @return The ComputerBuilder instance
		 */
		public ComputerDTOBuilder setCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}

		public ComputerDTOBuilder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
	}

}
