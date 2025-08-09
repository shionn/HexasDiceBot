package shionn.hexas.heroquest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import shionn.hexas.heroquest.dao.HeroQuestDao;

@Controller
@RequiredArgsConstructor
public class HeroQuestController {
	private final HeroQuest heroQuest;
	private final SqlSession session;

	@GetMapping(path = "/heroquest")
	public ModelAndView openHeroQuest() {
		HeroQuestDao dao = session.getMapper(HeroQuestDao.class);
		return new ModelAndView("heroquest").addObject("hq", heroQuest).addObject("players", dao.listPlayers());
	}

	@GetMapping(path = "/heroquest/enable")
	public String toggleEnable() {
		heroQuest.setEnable(!heroQuest.isEnable());
		return "redirect:/heroquest";
	}

	@GetMapping(path = "/heroquest/edit-player/{id}")
	public ModelAndView editPlayer(@PathVariable("id") int id) {
		HeroQuestDao dao = session.getMapper(HeroQuestDao.class);
		return new ModelAndView("heroquest")
				.addObject("hq", heroQuest)
				.addObject("players", dao.listPlayers())
				.addObject("player", dao.readPlayer(id));
	}

	@PostMapping(path = "/heroquest/add-player")
	public String addPlayer(@RequestParam("pseudo") String pseudo, @RequestParam("name") String name,
			@RequestParam("attack") int attack, @RequestParam("defence") int defence, @RequestParam("body") int body,
			@RequestParam("mind") int mind, @RequestParam("perc") int perc) {
		session
				.getMapper(HeroQuestDao.class)
				.createPlayer(Player
						.builder()
						.pseudo(pseudo)
						.name(name)
						.attack(attack)
						.defence(defence)
						.body(body)
						.mind(mind)
						.perc(perc)
						.build());
		session.commit();
		return "redirect:/heroquest";
	}

	@PostMapping(path = "/heroquest/edit-player/{id}")
	public String addPlayer(@PathVariable("id") int id, @RequestParam("pseudo") String pseudo,
			@RequestParam("name") String name, @RequestParam("attack") int attack, @RequestParam("defence") int defence,
			@RequestParam("body") int body, @RequestParam("mind") int mind, @RequestParam("perc") int perc) {
		session
				.getMapper(HeroQuestDao.class)
				.editPlayer(Player
						.builder()
						.id(id)
						.pseudo(pseudo)
						.name(name)
						.attack(attack)
						.defence(defence)
						.body(body)
						.mind(mind)
						.perc(perc)
						.build());
		session.commit();
		return "redirect:/heroquest";
	}

	@GetMapping(path = "/heroquest/enable-player/{id}")
	public String enablePlayer(@PathVariable("id") int id) {
		HeroQuestDao dao = session.getMapper(HeroQuestDao.class);
		Player player = dao.readPlayer(id);
		player.setEnable(!player.isEnable());
		dao.updateEnable(player);
		session.commit();
		return "redirect:/heroquest";
	}

}
