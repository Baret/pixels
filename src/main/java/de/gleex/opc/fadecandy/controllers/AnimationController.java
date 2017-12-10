package de.gleex.opc.fadecandy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.gleex.opc.fadecandy.animation.FrameBasedAnimation;
import de.gleex.opc.fadecandy.persistance.AnimationRepository;
import de.gleex.opc.fadecandy.pixel.Frame;
import de.gleex.opc.spring.MainConfig;

@Controller
@RequestMapping("/animation")
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
		newAnimation.addFrame(new Frame(config.fadecandy.pixelCount));
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
}
