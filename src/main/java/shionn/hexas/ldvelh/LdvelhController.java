package shionn.hexas.ldvelh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shionn.hexas.command.DiceCommand;

@Controller
public class LdvelhController {

	@Autowired
	private DiceCommand diceCommand;

	@Autowired
	private Ldvelh ldvelh;

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
		ldvelh.setHeroHab(heroHab);
		ldvelh.setMonsterHab(monsterHab);
		diceCommand.requestBattleDice(heroHab, monsterHab);
		return "redirect:/";
	}

}
