package com.naveen.test.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws FileNotFoundException, IOException{

		String rootdirectory = System.getProperty("user.dir");
		
// 		Reading config.properties file		
		Properties config = new Properties();
		FileInputStream fis = new FileInputStream(rootdirectory+"\\src\\test\\resources\\properties\\config.properties");
		config.load(fis);		
	//	config.forEach((key, value)->System.out.println(key+" ---> "+value));

// 		Reading OR.properties file		
		Properties or = new Properties();
		or.load(new FileInputStream(rootdirectory+"\\src\\test\\resources\\properties\\OR.properties"));
	//	or.forEach((k,v)->System.out.println(k+" == "+v));
		

		
	}

}
