package fr.excilys.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import src.main.java.config.SpringConfiguration;

public class Main {
	static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

	static Controller controller = context.getBean(Controller.class);

	public static void main(String[] args) {

		controller.start();
	}

}