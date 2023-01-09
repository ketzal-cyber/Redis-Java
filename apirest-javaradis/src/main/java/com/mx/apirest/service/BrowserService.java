package com.mx.apirest.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.apirest.entity.Browser;
import com.mx.apirest.repository.BrowserRepository;

@Service
public class BrowserService {
	
	@Autowired
	private BrowserRepository browserRepo;
	
	public Browser save(Browser browser) {
		return browserRepo.save(browser);
	}
	
	// sin uso
	public Browser update(Browser browser) {
		return browserRepo.save(browser);
	}
	
	// Actualizar version 
//	public Browser updateversion(Long id, String version) {
//		int respuesta = this.browserRepo.updateVersion(id, version);
//		return respuesta > 0 ? this.browserRepo.findById(id).orElse(null): null;
//	}
	
	public ArrayList<Browser> listar(){
		return (ArrayList<Browser>) browserRepo.findAll();
		
	}
	
	public Browser findById(Long id){
		Optional<Browser> brow = browserRepo.findById(id);
		Browser navegador = brow.get();
		return navegador;
	}
	
	
	public ArrayList<Browser> buscaPorVersion(String version){
		return browserRepo.findByVersion(version);
	}
	
	
	public boolean delete(Long id) {
		try {
			browserRepo.deleteById(id);
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
	}
	
	

}
