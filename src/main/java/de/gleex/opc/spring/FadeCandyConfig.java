package de.gleex.opc.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FadeCandyConfig {
	@Value("${fadecandy.address}")
	public String address;

	@Value("${fadecandy.port}")
	public int port = 7890;

	@Value("${fadecandy.pin}")
	public int pin = 0;

	@Value("${fadecandy.pixelCount}")
	public int pixelCount = 512;

	@Value("${fadecandy.interpolate}")
	public boolean interpolate;
}
