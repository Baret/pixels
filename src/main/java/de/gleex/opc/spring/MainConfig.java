package de.gleex.opc.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import de.gleex.opc.official.OpcClient;
import de.gleex.opc.official.OpcDevice;
import de.gleex.opc.official.PixelStrip;

@Configuration
public class MainConfig {

	Logger log = LoggerFactory.getLogger(MainConfig.class);

	@Autowired
	public FadeCandyConfig fadecandy;

	@Bean
	public OpcClient opcClient() {
		log.info("creating opcClient");
		final OpcClient opcClient = new OpcClient(fadecandy.address, fadecandy.port);
		opcClient.setInterpolation(fadecandy.interpolate);
		return opcClient;
	}

	@Bean
	@DependsOn("opcClient")
	public OpcDevice opcDevice() {
		log.info("creating opcDevice");
		return opcClient().addDevice();
	}

	@Bean
	@DependsOn("opcDevice")
	public PixelStrip strip() {
		log.info("creating pixelStrip with pin {} and pixelCount {}", fadecandy.pin, fadecandy.pixelCount);
		return opcDevice().addPixelStrip(fadecandy.pin, fadecandy.pixelCount);
	}
}
