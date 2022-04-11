package com.acmeshop.autocomplete.config;

import java.time.Duration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;

@Configuration
public class SpringRedisConfig {

	private Log log = LogFactory.getLog(SpringRedisConfig.class);

	
	@Value("${autocomplete.store.redis.host}")
	private String redisHost;
	@Value("${autocomplete.store.redis.port}")
	private int redisPort;
	@Value("${autocomplete.store.redis.password:#{null}}")
	private String redisPassword;

	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));

        JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));// 60s connection timeout

		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
	}

	// @Bean
	// public RedisTemplate<String, Object> redisTemplate() {
	// RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String,
	// Object>();
	// redisTemplate.setConnectionFactory(connectionFactory());
	// redisTemplate.setKeySerializer(new StringRedisSerializer());
	// return redisTemplate;
	// }
	//
	// @Bean
	// public StringRedisTemplate strRedisTemplate() {
	// StringRedisTemplate redisTemplate = new StringRedisTemplate();
	// redisTemplate.setConnectionFactory(connectionFactory());
	// return redisTemplate;
	// }
}