package shionn.hexas.jdr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JdrController {

	private final Jdr jdr;
	
	@GetMapping(path = "/jdr")
	public ModelAndView jdr() {
		return new ModelAndView("jdr").addObject("jdr", jdr);
	}

	@PostMapping(path = "/jdr/add-player")
	public String addPlayer(@RequestParam("name") String name,@RequestParam("clazz") String clazz) {
		jdr.add(Player.builder().name(name).clazz(clazz).build());
		return "redirect:/jdr";
	}

	@GetMapping(path = "/jdr/del-player/{name}")
	public String addPlayer(@PathVariable("name") String name) {
		jdr.rm(name);
		return "redirect:/jdr";
	}


}
