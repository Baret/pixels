package de.gleex.opc.fadecandy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.gleex.opc.fadecandy.animation.FrameBasedAnimation;
import de.gleex.opc.fadecandy.persistance.AnimationRepository;

@Controller
@RequestMapping("/animation")
public class AnimationController {
	@Autowired
	private AnimationRepository repo;
	
	@RequestMapping("")
	public ModelAndView listAnimations() {
		ModelAndView mav = new ModelAndView("list_animations");
		mav.addObject("animations", repo.findAll());
		return mav;
	}
	
	
	@RequestMapping("/new/{name}")
	public String newAnimation(@PathVariable String name) {
		FrameBasedAnimation newAnimation = new FrameBasedAnimation(name);
		repo.save(newAnimation);
		return "redirect:/animation";
	}
}
