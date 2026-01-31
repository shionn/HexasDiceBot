package shionn.hexas.heroquest;

import java.util.List;
import java.util.function.Predicate;

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
				String[] commands = event.getMessage().trim().split(" ");
				switch (commands[0].toLowerCase()) {
				case "!d6":
				case "!1d6":
					doD6(event, player);
					break;
				case "!2d6":
					do2D6(event, player);
					break;
				case "!1d12":
					do1D12(event, player);
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
					doPerception(event, commands, player);
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

	private void do1D12(MessageEvent event, Player player) {
		int roll = dice.roll(1, 12);
		sendMessage(event, player.getName() + " (%USER%) obtient " + roll);
	}

	private void doDoesNotUnderstand(MessageEvent event, Player player) {
		String message = "je ne comprend pas " + event.getMessage() + " %USER%";
		sendMessage(event, message);
	}

	private void doSpirit(MessageEvent event, Player player) {
		int roll = dice.roll(1, 6);
		String message;
		if (roll <= player.getMind()) {
			message = player.getName() + " (%USER%) Réussite " + roll + "/" + player.getMind();
		} else {
			message = player.getName() + " (%USER%) Echec " + roll + "/" + player.getMind();
		}
		sendMessage(event, message);
	}

	private void doPerception(MessageEvent event, String[] commands, Player player) {
		List<Integer> dices = dice.multiDices(player.getPerc(), 6);
		String message;
		if (count(dices, d -> d == 6) >= 2) {
			if (count(dices, d -> d == 1) >= 2) {
				message = player.getName()
						+ " (%USER%) trouve un magnifique trésors mais entant un grognement sinistre ";
			} else {
				message = player.getName() + " (%USER%) trouve un magnifique trésors ";
			}
		} else if (count(dices, d -> d == 1) >= 2) {
			message = player.getName() + " (%USER%) entant un grognement sinistre ";
		} else if (count(dices, d -> d >= 5) >= 1) {
			message = player.getName() + " (%USER%) trouve un quelque chose ";
		} else {
			message = player.getName() + " (%USER%) trouve rien ";
		}
		message += "(";
		for (int d : dices) {
			message += Icons.Dices[d - 1];
		}
		message += ")";
		sendMessage(event, message);
	}

	private long count(List<Integer> dices, Predicate<Integer> condition) {
		return dices.stream().filter(condition).count();
	}

	private void doDefense(MessageEvent event, Player player) {
		int count = 0;
		for (int i = 0; i < player.getDefence(); i++) {
			count += dice.roll(1, 6) >= 5 ? 1 : 0;
		}
		String message = player.getName() + " (%USER%) obtient " + count + " " + Icons.Shield;
		sendMessage(event, message);
	}

	private void doAttaque(MessageEvent event, Player player) {
		int count = 0;
		for (int i = 0; i < player.getAttack(); i++) {
			count += dice.roll(1, 6) >= 4 ? 1 : 0;
		}
		String message = player.getName() + " (%USER%) obtient " + count + " " + Icons.Skull;
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
