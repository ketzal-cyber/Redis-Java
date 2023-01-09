package com.mx.apirest.controller;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.mx.apirest.util.SerializarObject;

import jakarta.persistence.Tuple;

@RestController
@RequestMapping("/navegador")
public class BrowserController {
	
	
	@Autowired
	private RedisTemplate<String, String> redisTemplateConn;

	
	@Autowired
	BrowserService browserService;
	
	private static final String CACHE_NAME = "navegadores";
	
	@GetMapping
	public ArrayList<Browser> listar(){
		
		// OBTENER VALOR POR KEY
//		String elemento = redisTemplateConn.opsForValue().get("myCache");
//		System.out.println("Elemento Cache");
//		System.out.println(elemento);
		
		//mostar(CACHE_NAME);
		
//		redisTemplateConn.opsForValue().set(CACHE_NAME, "JAVA");
		
		Map<Object, Object> operaciones = redisTemplateConn.opsForHash().entries(CACHE_NAME);
		
		System.out.println("Mapa devuelto");
		System.out.println(operaciones);
		
		return (ArrayList<Browser>) browserService.listar();
	}
	
	// 
	@PostMapping
	public Browser guardar(@RequestBody Browser browser) {
		Browser brow = browserService.save(browser);
		// GUARDAR REDIS
		// value
		//redisTemplateConn.opsForValue().set(CACHE_NAME, brow.toString());
		//guardarRedis(CACHE_NAME, brow.toString());	
		
		// list
//		Long operaciones = redisTemplateConn.opsForList().leftPush(name, email);
//		Long operaciones = redisTemplateConn.opsForList().leftPush(CACHE_NAME, brow.toString());
		// Hash
		//redisTemplateConn.opsForHash().put(CACHE_NAME, brow.getId(), brow);
		guardarHash(CACHE_NAME, brow.getId(), brow);
		
		return brow;
	}
	
//	@PatchMapping("/{id}/{version}")
//	public Browser actualizaPorVersion(@PathVariable Long id, @PathVariable String version) {
	
	// actualizar valor redis
//	String  key = redisTemplateConn.opsForValue().getAndSet("myCache", "Python");
//	System.out.println("Elemento Cache");
//	System.out.println(key);
//actualizar(CACHE_NAME,);
	
//		return this.browserService.updateversion(id, version);
//	}
	
	
	@GetMapping(path = "/{id}")
	//@Cacheable(key="#id", value = "student")
	public Browser obtenerPorId(@PathVariable("id") Long id){
		Browser navegador = browserService.findById(id);
		
		
		// buscar por id en cache
		buscarId(CACHE_NAME,id);
		
		
		return navegador;
		//return this.browserService.findById(id);
	}
	
	
	@GetMapping("/query")
	public ArrayList<Browser> obtenerPorversion(@RequestParam("version") String version){
		return this.browserService.buscaPorVersion(version);
	}
	
	
	@DeleteMapping(path = "/{id}")
	public String eliminarPorId(@PathVariable("id") Long id){
		
		// ELIMINAR VALOR  Y KEY
//		String del = redisTemplateConn.opsForValue().getAndDelete("myCache");
//		System.out.println("Elemento Cache eliminado");
//		System.out.println(del);
		//borrar(CACHE_NAME);
		
		//Hash
		//redisTemplateConn.opsForHash().delete(CACHE_NAME, 2L);
		Long del = redisTemplateConn.opsForHash().delete(CACHE_NAME, id);
		System.out.println("Elemento Cache eliminado");
		System.out.println(del);
		
		boolean ok = this.browserService.delete(id);
		if (ok) {
			return "Se elimino el usuraio con id: "+ id;
			} else {
				return "No se elimino el usuario con id: "+ id;
			}
		}
	
	
	// CRUD REDIS
	public void guardarRedis(String llave, String valor) {
		//redisTemplateConn.opsForValue().set(CACHE_NAME, "JAVA");
//		Map<String, String> mapa = new HashMap<>();
//		mapa.put("1", "Java");
//		mapa.put("2", "Python");
//		mapa.put("3", "JavaScript");
//		//org.javatuples.Pair tupla = new org.javatuples.Pair("Lenguage", "Hola mundo");
//		redisTemplateConn.opsForValue().multiSet(mapa);
		
		
	}
	
	public void guardarHash(String name, Long id, Browser brow) {
		//hash
		redisTemplateConn.opsForHash().put(name, id, brow);
	}
	
	public void agregardato() {
		//String del = redisTemplateConn.opsForValue().append(llave, llave);
	}
	
	public void actualizar(String llave, String valor) {
		String  key = redisTemplateConn.opsForValue().getAndSet(CACHE_NAME, "Python");
		System.out.println("Elemento Cache");
		System.out.println(key);
	}
	
	public void mostar(String llave) {
		Map<String, String> mapa = new HashMap<>();
		mapa.put("1", "Java");
		mapa.put("2", "Python");
		mapa.put("3", "JavaScript");
		
		List<String> keys = Arrays.asList("1","2","3");
		List<String> elemento = redisTemplateConn.opsForValue().multiGet(keys);
		System.out.println("Elemento Cache");
		System.out.println(elemento);
		
//		String elemento = redisTemplateConn.opsForValue().get(CACHE_NAME);
//		System.out.println("Elemento Cache");
//		System.out.println(elemento);
	}
	
	public void buscarId(String name, Long id) {
//		Set<Object> colectionSet = redisTemplateConn.opsForHash().keys(CACHE_NAME); // -> [3 2 1 ]
		System.out.println(redisTemplateConn.opsForHash().get(name, id));
	}
	
	public void borrar(String llave) {
		
		String del = redisTemplateConn.opsForValue().getAndDelete(CACHE_NAME);
		System.out.println("Elemento Cache eliminado");
		System.out.println(del);
	}
	
	
	public String getid() {
		int indice = 0;
		indice++;
		return "-"+indice;
	}
	

}
