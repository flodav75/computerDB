package fr.excilys.core;

public enum ECommandeLine {

	ALLCOMPUTERS(1), ALLCOMPANIES(2), DETAILS(3), ADD(4), UPDATE(5), REMOVE(6),DELETE(7), EXIT(8);
	private int value;

	ECommandeLine(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
