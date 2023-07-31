package com.aliat.alm.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class app {
	
	public static void main(String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");
	}

}
