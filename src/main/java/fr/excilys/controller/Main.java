package fr.excilys.controller;

public class Main {

	public static void main(String[] args) {
		//Controller.getInstance();
		System.out.println(getPageNumberMax(781,10));
	}
	private static int getPageNumberMax(int rows,int limit) {
		return (int) Math.ceil((1.0*rows)/limit);
	}

}
