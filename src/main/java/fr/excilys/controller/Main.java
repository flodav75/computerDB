package fr.excilys.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import src.main.java.config.SpringConfiguration;

public class Main {
	

	public static void main(String[] args) {
		 @SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

//		 Controller controller = context.getBean(Controller.class);
//		controller.start();
	}

}