package com.mx.apirest.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/redis/consumer")
public class RedisController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired													// definir template de redis
	private StringRedisTemplate redisTemplate;
	
	
	private final String BASE_URL = "https://rickandmortyapi.com/api/character/";
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id){
		
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			
													//iniciar operaciones con redis
			ValueOperations<String, String> valueOperation = redisTemplate.opsForValue();
													// busca en cache si esta lo regresa
			String data = valueOperation.get(getkey(id.toString()));
			if(data != null && !data.isEmpty()) {
				return new ResponseEntity<String>(data, headers, HttpStatus.OK);
			}
			
			ResponseEntity<String> response = restTemplate.exchange(BASE_URL.concat(id.toString()), HttpMethod.GET, null, String.class);
			
			
													// indicar la llave y  el valro a guardar en cache	
			if(response.getStatusCodeValue() == 200) {
				valueOperation.set(getkey(id.toString()), response.getBody(), Duration.ofSeconds(120));
			}
			
			return new ResponseEntity<String>(response.getBody(), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	private String getkey(String id) {
		return "Rick-".concat(id);
	}

}
