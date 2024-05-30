package shionn.hexas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import shionn.hexas.command.DiceCommand;

@Controller
@SessionScope
public class CombatController {

	@Autowired
	private DiceCommand diceCommand;
	private int monsterHab;
	private int heroHab;

	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index").addObject("heroHab", heroHab).addObject("monsterHab", monsterHab);
	}

	@GetMapping("/dice/2D6")
	public String request2D6() {
		diceCommand.request2D6();
		return "redirect:/";
	}

	@GetMapping("/dice/1D6")
	public String requestD6() {
		diceCommand.requestD6();
		return "redirect:/";
	}

	@PostMapping("/dice/battle")
	public String requestBattle(@RequestParam("monster-hab") int monsterHab, @RequestParam("hero-hab") int heroHab) {
		this.monsterHab = monsterHab;
		this.heroHab = heroHab;
		diceCommand.requestBattleDice(heroHab, monsterHab);
		return "redirect:/";
	}

}
