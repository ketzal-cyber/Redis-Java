package com.mx.apirest.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import com.mx.apirest.entity.Browser;

@Configuration
public class RestTemplateConfig {

	
	
	// Configuracion de propiedades de CONNECTION REDIS
	@Bean
	RedisStandaloneConfiguration redisConfig() {
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		
				//redisConfig.setDatabase(0);
				redisConfig.setHostName("127.0.0.1");
				redisConfig.setPort(6379);
				//redisConfig.setUsername("redis-server");
				//redisConfig.setPassword("servRJava");
		return redisConfig;
	}
	
	// devuelve una coneccion nativa alservidor REDIS
	@Bean
	  LettuceConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory lettuceConnFact = new LettuceConnectionFactory();
		//lettuceConnFact.getStandaloneConfiguration();
		//lettuceConnFact.setHostName("127.0.0.1"); 	// deprecated
	    //return new LettuceConnectionFactory();	// Connection default
		//return lettuceConnFact;
		return new LettuceConnectionFactory(redisConfig());
	  }
	
	// Para el manejo de la coneccion
//	  @Bean
//	  StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//	    StringRedisTemplate template = new StringRedisTemplate();
//	    template.setConnectionFactory(redisConnectionFactory);
//	    return template;
//	  }
	  
	  // Serializacion de datos de tipo JSON almacenados
	  @Bean(name = "springSessionDefaultRedisSerializer")
	  GenericJackson2JsonRedisSerializer getGenericJackson2JsonRedisserializer() {
		  return new GenericJackson2JsonRedisSerializer();
	  }
	  
	 @Primary
	  @Bean
	  RedisTemplate<String, String> getRedistemplate(LettuceConnectionFactory lettuceConnection){
		  RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		  redisTemplate.setConnectionFactory(lettuceConnection);
		  redisTemplate.setKeySerializer(new StringRedisSerializer());
		  redisTemplate.setHashKeySerializer(new StringRedisSerializer()); 				// una simple serializacion de cadenas 
		  redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());	// serializa  y deserealiza a traves de la clase cargada
		  redisTemplate.setValueSerializer(getGenericJackson2JsonRedisserializer());
		  //redisTemplate.setEnableTransactionSupport(true);	// lleva la cuenta de las  operaciones
		  redisTemplate.afterPropertiesSet();
		  return redisTemplate;
	  }
}
