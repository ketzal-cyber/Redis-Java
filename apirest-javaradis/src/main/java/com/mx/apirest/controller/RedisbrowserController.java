package com.mx.apirest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.apirest.entity.Browser;
import com.mx.apirest.service.RedisBrowserService;

@RestController
@RequestMapping("/radis/browser")
public class RedisbrowserController {

	@Autowired
	private RedisBrowserService redisBrowserService;
	
	@PostMapping
	public String saveBrwoser(@RequestBody Browser browser) {
		redisBrowserService.save(browser);
		return "Successfuly added "+ browser.getName() + " Browser";
	}
	
	@Cacheable(key = "#id", value = "browser" )
	@GetMapping(path = "/{id}")
	public Browser findBrowserId(@PathVariable("id") Long id) {
		return redisBrowserService.find(id);
	}
	
	@CacheEvict(key = "#id", value = "browser")
	@DeleteMapping(path = "/{id}")
	public String deleteBrowser(@PathVariable("id") Long id) {
		redisBrowserService.delete(id);
		return "Successfully removed #Browser with id: "+ id;
	}
	
	@Cacheable(key = "#id", value = "browser" )
	@GetMapping
	public Map<Long,Browser> findAllBrowser() {
		return redisBrowserService.findAllBrowser();
	}
	
	
//	// CachePut -> update a cache
//	@CachePut(key = "#id", cacheNames = "browser" )
//	@PutMapping(path = "/{id}")
//	public String updateBrowser(@PathVariable("id") Long id, @RequestBody Browser browser) {
//		return redisBrowserService.update(id, browser);
//		return "successfully update  Browser  with id . "+ id;
//	}
	
}
