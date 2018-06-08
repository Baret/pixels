package de.gleex.opc.fadecandy.controllers;

import de.gleex.opc.fadecandy.animation.FrameBasedAnimation;
import de.gleex.opc.fadecandy.persistence.AnimationRepository;
import de.gleex.opc.official.Animation;
import de.gleex.opc.official.OpcClient;
import de.gleex.opc.official.PixelStrip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class FCServerController {

	Logger log = LoggerFactory.getLogger(FCServerController.class);

	@Autowired
	private OpcClient client;

	@Autowired
	private PixelStrip strip;
	
	@Autowired
	private AnimationRepository animationRepo;

	@RequestMapping(value = "/config", method = { POST, GET })
	public @ResponseBody String displayConfig() {
		log.info("Returning server config.");
		log.info("Pixels on strip: {}", strip.getPixelCount());
		return client.getConfig();
	}

	@RequestMapping(value = "/animate/{id}/{delay}/{seconds}", method = { POST, GET })
	public @ResponseBody String animate(@PathVariable Long id, @PathVariable("delay") Integer delayInMs, @PathVariable(required = false) final Optional<Integer> seconds) {
		final FrameBasedAnimation animation = animationRepo.findOne(id);
		if(animation != null) {
			executeAnimation(seconds, delayInMs, animation);
			client.close();
		}
		return "Started animation for " + seconds + " seconds";
	}

	/**
	 * @param seconds
	 * @param delayInMs 
	 * @param a
	 */
	public void executeAnimation(final Optional<Integer> seconds, Integer delayInMs, final Animation a) {
		strip.setAnimation(a);

		final long millis = seconds.map(s -> s * 1000).filter(s -> s > 0).orElse(1000);

		final long steps = millis / delayInMs;
		log.info("starting animation for {} steps and a delay of {}", steps, delayInMs);
		client.show();
		try {
			Thread.sleep(delayInMs);
			for (int i = 1; i < steps; i++) {
				client.animate();
				Thread.sleep(delayInMs);
			}
		}
		catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("animation ended");
	}
}
