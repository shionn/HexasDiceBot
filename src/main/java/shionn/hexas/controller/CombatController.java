package shionn.hexas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import shionn.hexas.command.DiceCommand;

@Controller
public class CombatController {

	@Autowired
	private DiceCommand diceCommand;

	@GetMapping("/")
	public String home() {
		return "index";
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
}
