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
	private OpcClient server;

	private static Logger log = LoggerFactory.getLogger(PixelsApplication.class);

	public static void main(final String[] args) {
		SpringApplication.run(PixelsApplication.class, args);
		//
		// final OpcClient server = new OpcClient("noteblubb", 7890);
		// server.setInterpolation(true);
		// final OpcDevice fadeCandy = server.addDevice();
		// final PixelStrip strip1 = fadeCandy.addPixelStrip(0, 9);
		// log.info("Fadecandy config:\n{}", server.getConfig());
		//
		// strip1.setAnimation(new Knightrider());
		//
		// log.info("starting animation");
		// for (int i = 0; i < 300; i++) {
		// server.animate();
		// try {
		// Thread.sleep(100);
		// }
		// catch (final InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// log.info("animation ended");
		//
		// server.close();
	}

	@PostConstruct
	public void dumpConfig() {
		log.info("fadecandy.address: {}", config.fadecandy.address);
		log.info("fadecandy.port: {}", config.fadecandy.port);
		log.info("server config:\n{}", server.getConfig());
	}

	@PreDestroy
	public void disconnectFadecandy() {
		log.info("Disconnecting from {}", server.getConfig());
		server.close();
	}
}
