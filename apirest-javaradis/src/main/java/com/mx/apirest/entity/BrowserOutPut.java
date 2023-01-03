package com.mx.apirest.entity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BrowserOutPut {
	
	private FileOutputStream file;
	private ObjectOutputStream outPut;
	
	public void abrir() throws IOException {
		file = new FileOutputStream("/Users/angelmartinezmoreno/javaFile/browser.ser");
		outPut = new ObjectOutputStream(file);
	}
	
	public void cerrar() throws IOException {
		if(outPut != null) {
			outPut.close();
		}
	}
	
	public void escribir(Browser browser) throws IOException {
		if(outPut != null) {
			outPut.writeObject(browser);
		}
	}

}
