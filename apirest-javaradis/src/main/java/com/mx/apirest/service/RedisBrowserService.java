package com.mx.apirest.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mx.apirest.entity.Browser;
import com.mx.apirest.repository.RedisBrowserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RedisBrowserService implements RedisBrowserRepository {
	
	private static final String CACHE_NAME = "browser";
	
	private RedisTemplate<String, Object> redisTemplate;
	
	private HashOperations<String, Long, Browser> hashOperations;
	
	@Autowired
	public RedisBrowserService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void intializeHashOperations() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	

	@Override
	public void save(Browser browser) {
		hashOperations.put(CACHE_NAME, browser.getId(), browser);
		
	}

	@Override
	public Browser find(Long id) {
		
		return hashOperations.get(CACHE_NAME, id);
	}

	@Override
	public Map<Long, Browser> findAllBrowser() {
		
		return hashOperations.entries(CACHE_NAME);
	}

	@Override
	public void delete(Long id) {
		hashOperations.delete(CACHE_NAME, id);
		
	}

}
