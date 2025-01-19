package shionn.hexas.jdr;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import shionn.hexas.bot.BotClient;
import shionn.hexas.jdr.dao.JdrPlayersDao;

@Controller
@RequiredArgsConstructor
public class JdrController {

	private final BotClient bot;

	private final Jdr jdr;

	private final SqlSession session;

	private final Dice dice;

	@GetMapping(path = "/jdr")
	public ModelAndView jdr() {
		List<Player> players = session.getMapper(JdrPlayersDao.class).list();
		List<Player> prioirities = session.getMapper(JdrPlayersDao.class).priorities();
		return new ModelAndView("jdr").addObject("jdr", jdr).addObject("players", players).addObject("prioirities",
				prioirities);
	}

	@GetMapping(path = "/jdr/enable")
	public String jdrEnable() {
		jdr.setEnable(!jdr.isEnable());
		return "redirect:/jdr";
	}

	@PostMapping(path = "/jdr/add-player")
	public String addPlayer(@RequestParam("name") String name, @RequestParam("clazz") String clazz,
			@RequestParam("strenght") int strenght, @RequestParam("dexterity") int dexterity,
			@RequestParam("constitution") int constitution, @RequestParam("intelligence") int intelligence,
			@RequestParam("wisdom") int wisdom, @RequestParam("charisma") int charisma) {
		Player player = Player.builder().name(name).clazz(clazz).strenght(strenght).dexterity(dexterity)
				.constitution(constitution).intelligence(intelligence).wisdom(wisdom).charisma(charisma).build();
		session.getMapper(JdrPlayersDao.class).create(player);
		session.commit();
		return "redirect:/jdr";
	}

	@GetMapping(path = "/jdr/del-player/{name}")
	public String addPlayer(@PathVariable("name") String name) {
		session.getMapper(JdrPlayersDao.class).rm(name);
		session.commit();
		return "redirect:/jdr";
	}

	@GetMapping(path = "/jdr/action/priority")
	public String requestPriority() {
		JdrPlayersDao dao = session.getMapper(JdrPlayersDao.class);
		List<Player> players = dao.list();
		
		players.forEach(player -> {
			int roll = dice.roll(1, 20);
			int mod = jdr.mod(player.getDexterity());
			player.setPriority(roll + mod);
			String message = "/me Priorité : @" + player.getName() + " obtient " + roll;
			if (mod < 0) {
				message += "-";
			} else {
				message += "+";
			}
			message += mod + "=" + (roll + mod);
			bot.sendMessage(message);
			dao.updatePriority(player);
		});
		session.commit();
		return "redirect:/jdr";
	}

}
