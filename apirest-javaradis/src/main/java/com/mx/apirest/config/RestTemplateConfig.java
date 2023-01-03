package com.mx.apirest.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	
	
	// Configuracion de propiedades de CONNECTION REDIS
	@Bean
	RedisStandaloneConfiguration redisConfig() {
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		
				//redisConfig.setDatabase(0);
				redisConfig.setHostName("127.0.0.1");
				redisConfig.setPort(6379);
				//redisConfig.setUsername("");
				//redisConfig.setPassword("servRJava");
		return redisConfig;
	}
	
	// devuelve una coneccion  alservidor REDIS
	@Bean
	  LettuceConnectionFactory redisConnectionFactory() {
		//
		
		LettuceConnectionFactory lettuceConnFact = new LettuceConnectionFactory();
		//lettuceConnFact.getStandaloneConfiguration();
		//lettuceConnFact.setHostName("127.0.0.1"); 	// deprecated
	    //return new LettuceConnectionFactory();	// Connection default
		//return lettuceConnFact;
		return new LettuceConnectionFactory(redisConfig());
	  }
	
	// Para el manejo de los datos
	  @Bean
	  StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

	    StringRedisTemplate template = new StringRedisTemplate();
	    template.setConnectionFactory(redisConnectionFactory);
	    return template;
	  }
}
