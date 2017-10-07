package de.gleex.opc.fadecandy.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.gleex.opc.fadecandy.animation.Knightrider;
import de.gleex.opc.official.Animation;
import de.gleex.opc.official.OpcClient;
import de.gleex.opc.official.PixelStrip;

@Controller
public class FCServerController {

	Logger log = LoggerFactory.getLogger(FCServerController.class);

	@Autowired
	private OpcClient server;

	@Autowired
	private PixelStrip strip;

	@RequestMapping(value = "/config", method = { POST, GET })
	public @ResponseBody String displayConfig() {
		log.info("Returning server config.");
		log.info("Pixels on strip: {}", strip.getPixelCount());
		return server.getConfig();
	}

	@RequestMapping(value = "/animate/{seconds}", method = { POST, GET })
	public @ResponseBody String animate(@PathVariable(required = false) final Optional<Integer> seconds) {
		executeAnimation(seconds, new Knightrider());
		server.close();
		return "Started animation for " + seconds + " seconds";
	}

	/**
	 * @param seconds
	 * @param a
	 */
	public void executeAnimation(final Optional<Integer> seconds, final Animation a) {
		strip.setAnimation(a);

		final long millis = seconds.map(s -> s * 1000).filter(s -> s > 0).orElse(1000);

		final int delay = 500;
		final long steps = millis / delay;
		log.info("starting animation for {} steps and a delay of {}", steps, delay);
		server.show();
		try {
			Thread.sleep(delay);
			for (int i = 1; i < steps; i++) {
				server.animate();
				Thread.sleep(delay);
			}
		}
		catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("animation ended");
	}
}
