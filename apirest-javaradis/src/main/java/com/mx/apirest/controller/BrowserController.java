package com.mx.apirest.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.apirest.entity.Browser;
import com.mx.apirest.service.BrowserService;

@RestController
@RequestMapping("/navegador")
public class BrowserController {
	
	@Autowired													// definir template de redis
	private StringRedisTemplate redisTemplate;

	
	@Autowired
	BrowserService browserService;
	
//	@GetMapping
//	public ArrayList<Browser> listar(){
//		return (ArrayList<Browser>) browserService.listar();
//	}
	@GetMapping
	public ArrayList<Browser> listar(){
		//iniciar operaciones con redis
		ValueOperations<String, String> valueOperation = redisTemplate.opsForValue();
		
		ArrayList<Browser> arrayToHashSet = browserService.listar();
		
		HashSet<Browser> hashSet = new HashSet<>(arrayToHashSet);
		valueOperation.set("listBrowser", hashSet.toString(), Duration.ofSeconds(100));
		
		return (ArrayList<Browser>) browserService.listar();
	}
	
	
	// 
	@PostMapping
	public Browser guardar(@RequestBody Browser browser) {
		return this.browserService.save(browser);
	}
	
//	@PatchMapping("/{id}/{version}")
//	public Browser actualizaPorVersion(@PathVariable Long id, @PathVariable String version) {
//		return this.browserService.updateversion(id, version);
//	}
	
	@GetMapping(path = "/{id}")
	public Optional<Browser> obtenerPorId(@PathVariable("id") Long id){
		return this.browserService.findById(id);
	}
	
	@GetMapping("/query")
	public ArrayList<Browser> obtenerPorversion(@RequestParam("version") String version){
		return this.browserService.buscaPorVersion(version);
	}
	
	@DeleteMapping(path = "/{id}")
	public String eliminarPorId(@PathVariable("id") Long id){
		boolean ok = this.browserService.delete(id);
		if (ok) {
			return "Se elimino el usuraio con id: "+ id;
			} else {
				return "No se elimino el usuario con id: "+ id;
			}
		}
	

}
