package de.gleex.opc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.gleex.opc.official.OpcClient;
import de.gleex.opc.spring.MainConfig;

@SpringBootApplication
@EnableAutoConfiguration
public class PixelsApplication {

	@Autowired
	private MainConfig config;

	@Autowired
	private OpcClient client;

	private static Logger log = LoggerFactory.getLogger(PixelsApplication.class);

	public static void main(final String[] args) {
		SpringApplication.run(PixelsApplication.class, args);
	}

	@PostConstruct
	public void dumpConfig() {
		log.info("fadecandy.address: {}", config.fadecandy.address);
		log.info("fadecandy.port: {}", config.fadecandy.port);
		log.info("fadecandyserver config:\n{}", client.getConfig());
	}

	@PreDestroy
	public void disconnectFadecandy() {
		log.info("Disconnecting from {}", client.getConfig());
		client.close();
	}
}
