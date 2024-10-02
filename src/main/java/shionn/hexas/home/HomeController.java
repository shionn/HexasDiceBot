package shionn.hexas.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import shionn.hexas.heroquest.HeroQuest;
import shionn.hexas.ldvelh.Ldvelh;

@Controller
public class HomeController {

	@Autowired
	private Ldvelh ldvelh;
	@Autowired
	private HeroQuest heroQuest;

	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index").addObject("ldvelh", ldvelh).addObject("heroQuest", heroQuest);
	}

}
