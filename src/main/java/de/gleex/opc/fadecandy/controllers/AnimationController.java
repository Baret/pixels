package de.gleex.opc.fadecandy.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.gleex.opc.fadecandy.animation.FrameBasedAnimation;
import de.gleex.opc.fadecandy.persistance.AnimationRepository;
import de.gleex.opc.fadecandy.pixel.Frame;
import de.gleex.opc.fadecandy.pixel.Pixel;
import de.gleex.opc.spring.MainConfig;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/animation")
@Slf4j
public class AnimationController {
	@Autowired
	private AnimationRepository repo;
	@Autowired
	private MainConfig config;

	@RequestMapping("")
	public ModelAndView listAnimations() {
		final ModelAndView model = new ModelAndView("list_animations");
		model.addObject("animations", repo.findAll());
		return model;
	}

	@RequestMapping("/new/{name}")
	public String newAnimation(@PathVariable final String name) {
		final FrameBasedAnimation newAnimation = new FrameBasedAnimation(name);
		Random rand = new Random(System.currentTimeMillis());
		for (int i = 0; i < 5; i++) {
			List<Pixel> pixels = new ArrayList<>();
			for (int p = 0; p < config.fadecandy.pixelCount; p++) {
				pixels.add(new Pixel(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
			}
			final Frame frame = new Frame(config.fadecandy.pixelCount);
			frame.setPixels(pixels);
			newAnimation.addFrame(frame);
		}
		repo.save(newAnimation);
		return "redirect:/animation";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView editAnimation(@PathVariable final Long id, final RedirectAttributes redirectAtt) {
		final FrameBasedAnimation animation = repo.findOne(id);
		final ModelAndView model = new ModelAndView("edit_animation");
		if(animation != null) {
			model.addObject("animation", animation);
		} else {
			redirectAtt.addFlashAttribute("error", "No animation found with ID " + id);
			return new ModelAndView("redirect:/animation");
		}
		return model;
	}

	@RequestMapping(value="/update/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public FrameBasedAnimation updateAnimation(@PathVariable final Long id, @RequestBody FrameBasedAnimation animation, final RedirectAttributes redirectAtt) {
		if (repo.exists(id)) {
			log.info("updating animation {}", id);
			animation.setId(id);
			return repo.save(animation);
		} else {
			log.info("Could not update animatipn {} because it does not exist", id);
			return animation;
		}
	}
}
