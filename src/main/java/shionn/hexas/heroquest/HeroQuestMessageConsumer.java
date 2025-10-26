package shionn.hexas.heroquest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import shionn.hexas.bot.MessageEvent;
import shionn.hexas.bot.Source;
import shionn.hexas.db.SessionFactory;
import shionn.hexas.heroquest.dao.HeroQuestDao;
import shionn.hexas.jdr.Dice;

@Component
public class HeroQuestMessageConsumer {

	@Autowired
	private HeroQuest heroQuest;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private Dice dice;

	public void conssume(MessageEvent event) {
		if (heroQuest.isEnable() && event.getMessage().startsWith("!")) {
			Player player = retreivePlayer(event);
			if (player != null) {
				switch (event.getMessage().trim().toLowerCase()) {
				case "!d6":
				case "!1d6":
					doD6(event, player);
					break;
				case "!2d6":
					do2D6(event, player);
					break;
				case "!deplacement":
				case "!mov":
					doMove(event, player);
					break;
				case "!attaque":
				case "!atk":
					doAttaque(event, player);
					break;
				case "!defense":
				case "!def":
					doDefense(event, player);
					break;
				case "!esprit":
				case "!mind":
					doSpirit(event, player);
					break;
				case "!perception":
				case "!perc":
					doPerception(event, player);
					break;
				case "!aide":
					sendMessage(event, "!d6 !2d6 !deplacement !attaque !defense !esprit !perception");
					break;
				case "!help":
					sendMessage(event, "!d6 !2d6 !mov !atk !def !mind !perc");
					break;
				case "!test": // nothing to do
					break;
				default:
					doDoesNotUnderstand(event, player);
				}
			}
		}
	}

	private void doD6(MessageEvent event, Player player) {
		int roll = dice.roll(1, 6);
		sendMessage(event, player.getName() + " (%USER%) obtient " + roll);
	}

	private void do2D6(MessageEvent event, Player player) {
		int roll = dice.roll(2, 6);
		sendMessage(event, player.getName() + " (%USER%) obtient " + roll);
	}

	private void doDoesNotUnderstand(MessageEvent event, Player player) {
		String message = "je ne comprend pas " + event.getMessage() + " %USER%";
		sendMessage(event, message);
	}

	private void doSpirit(MessageEvent event, Player player) {
		int roll = dice.roll(1, 6);
		String message;
		if (roll <= player.getMind() + 1) {
			message = player.getName() + " (%USER%) TODO";
		} else {
			message = player.getName() + " (%USER%) TODO";
		}
		sendMessage(event, message);
	}

	private void doPerception(MessageEvent event, Player player) {
		int roll = dice.roll(1, 12);
		String message;
		if (roll == 12) {
			message = player.getName() + " (%USER%) entant un grognement sinistre";
		} else if (roll <= player.getPerc()) {
			message = player.getName() + " (%USER%) trouve un trésor";
		} else {
			message = player.getName() + " (%USER%) entend un bruit suspect";
		}
		sendMessage(event, message);
	}

	private void doDefense(MessageEvent event, Player player) {
		int count = 0;
		for (int i = 0; i < player.getDefence(); i++) {
			count += dice.roll(1, 6) >= 5 ? 1 : 0;
		}
		String message = player.getName() + " (%USER%) obtient " + count + " 🛡";
		sendMessage(event, message);
	}

	private void doAttaque(MessageEvent event, Player player) {
		int count = 0;
		for (int i = 0; i < player.getAttack(); i++) {
			count += dice.roll(1, 6) >= 4 ? 1 : 0;
		}
		String message = player.getName() + " (%USER%) obtient " + count + " 💀";
		sendMessage(event, message);
	}

	private void doMove(MessageEvent event, Player player) {
		int roll = dice.roll(2, 6);
		String message = player.getName() + " (%USER%) se déplace de " + roll + " cases max";
		sendMessage(event, message);
	}

	private void sendMessage(MessageEvent event, String message) {
		event.answer(message);
	}

	private Player retreivePlayer(MessageEvent message) {
		try (SqlSession session = sessionFactory.open()) {
			HeroQuestDao dao = session.getMapper(HeroQuestDao.class);
			if (message.getApplication() == Source.discord) {
				return dao.readByDiscordPseudo(message.getPseudo());
			}
			return dao.readByPseudo(message.getPseudo());
		}
	}

}
