package de.gleex.opc.fadecandy.controllers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.gleex.opc.fadecandy.animation.FrameBasedAnimation;
import de.gleex.opc.fadecandy.persistance.AnimationRepository;
import de.gleex.opc.fadecandy.pixel.Frame;
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
		for (int i = 0; i < 3; i++) {
			newAnimation.addFrame(new Frame(config.fadecandy.pixelCount));
		}
		System.out.println("pixelCount = " + config.fadecandy.pixelCount);
		System.out.println("Saving animation: " + newAnimation.getName());
		newAnimation.getFrames().forEach(frame -> System.out.println(frame));
		System.out.println("frames finished");
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

	@RequestMapping("/update/{id}/{frames}")
	public ModelAndView updateAnimation(@PathVariable final Long id, @MatrixVariable(pathVar="frames") Map<String, List<String>> frames, final RedirectAttributes redirectAtt) {
		log.info("updating animation {}", id);
		final FrameBasedAnimation animation = repo.findOne(id);
		final ModelAndView model = new ModelAndView("edit_animation");
		if(animation != null) {
			model.addObject("animation", animation);
		} else {
			redirectAtt.addFlashAttribute("error", "No animation found with ID " + id);
			return new ModelAndView("redirect:/animation");
		}

		// test von matrixvariable
		log.info("got {} frames", frames.size());
		for (Entry<String, List<String>> frame : frames.entrySet()) {
			log.info("frame entry: {} - {}", frame.getKey(), StringUtils.join(frame.getValue()));
		}
		return model;
	}
}
