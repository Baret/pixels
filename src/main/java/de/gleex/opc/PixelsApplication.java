package de.gleex.opc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.gleex.opc.fadecandy.animation.Knightrider;
import de.gleex.opc.official.OpcClient;
import de.gleex.opc.official.OpcDevice;
import de.gleex.opc.official.PixelStrip;

@SpringBootApplication
@EnableAutoConfiguration
public class PixelsApplication {
	
	private static Logger log = LoggerFactory.getLogger(PixelsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PixelsApplication.class, args);
		
		OpcClient server = new OpcClient("localhost", 7890);
		server.setInterpolation(true);
		OpcDevice fadeCandy = server.addDevice();
		PixelStrip strip1 = fadeCandy.addPixelStrip(0, 9);
		log.info("Fadecandy config: {}", server.getConfig());
		
		strip1.setAnimation(new Knightrider());
		
		log.info("starting animation");
		for (int i=0; i<300; i++) {
			server.animate();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info("animation ended");
		
		server.close();
	}
}
