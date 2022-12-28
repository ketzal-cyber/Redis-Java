package com.mx.apirest.repository;

import java.util.Map;

import com.mx.apirest.entity.Browser;

public interface RedisBrowserRepository {
	
	void save(Browser browser);
	
	Browser find(Long id);
	
	Map<Long, Browser> findAllBrowser();
	
	void delete(Long id);
}
