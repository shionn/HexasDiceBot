package shionn.hexas.heroquest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import com.github.twitch4j.chat.ITwitchChat;

@Controller
@SessionScope
public class HeroQuestController {
	@Autowired
	private HeroQuest heroQuest;

	@Autowired
	private ITwitchChat bot;
	@Autowired
	@Value("${twitch.channel}")
	private String channel;

	@PostMapping("/heroquest/atk")
	public String requestAtk(@RequestParam("character") CharacterClass character, @RequestParam("atk") int atk,
			@RequestParam("monster") String monster, @RequestParam("def") int def) {
		heroQuest.setActive(character);
		heroQuest.getActivePlayer().setAtk(atk);
		heroQuest.getMonster().setPlayer(monster);
		heroQuest.getMonster().setDef(def);
		bot.sendMessage(channel, "> " + character.getDisplayName() + " attaque avec !" + atk + "D6 un " + monster
				+ " contre avec !" + def + "D6 <");
		return "redirect:/";
	}

	@PostMapping("/heroquest/def")
	public String requestDef(@RequestParam("character") CharacterClass character, @RequestParam("atk") int atk,
			@RequestParam("monster") String monster, @RequestParam("def") int def) {
		heroQuest.setActive(character);
		heroQuest.getActivePlayer().setDef(def);
		heroQuest.getMonster().setPlayer(monster);
		heroQuest.getMonster().setAtk(atk);
		bot.sendMessage(channel, "> " + monster + " attaque avec !" + atk + "D6 " + character.getDisplayName()
				+ " contre avec !" + def + "D6 <");
		return "redirect:/";
	}

	@PostMapping("/heroquest/configuration")
	public String configuration(@RequestParam("barbarian") String barbarian, @RequestParam("dwarf") String dwarf,
			@RequestParam("elf") String elf, @RequestParam("magician") String magician) {
		heroQuest.getBarbarian().setPlayer(barbarian);
		heroQuest.getDwarf().setPlayer(dwarf);
		heroQuest.getElf().setPlayer(elf);
		heroQuest.getMagician().setPlayer(magician);
		return "redirect:/";
	}
}
