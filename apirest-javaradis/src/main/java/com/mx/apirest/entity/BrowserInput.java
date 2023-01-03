package com.mx.apirest.entity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BrowserInput {
	
	private FileInputStream file;
	private ObjectInputStream input;
	
	public void abrir() throws IOException {
		file = new FileInputStream("/Users/angelmartinezmoreno/javaFile/browser.ser");
		input = new ObjectInputStream(file);
	}
	
	public void cerrar() throws IOException {
		if(input != null) {
			input.close();
		}
	}
	
	public Browser leer() throws IOException, ClassNotFoundException {
		Browser browser = null;
		if(input != null) {
			try {
				browser = (Browser) input.readObject();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return  browser;
	}

}
