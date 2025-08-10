package shionn.hexas.heroquest;

import java.util.function.Consumer;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;

import shionn.hexas.db.SessionFactory;
import shionn.hexas.heroquest.dao.HeroQuestDao;
import shionn.hexas.jdr.Dice;

@Component
@Scope("singleton")
public class HeroQuestMessageEventConsumer implements Consumer<ChannelMessageEvent> {

	@Autowired
	private HeroQuest heroQuest;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private Dice dice;

	public HeroQuestMessageEventConsumer(ITwitchChat bot) {
		bot.getEventManager().onEvent(ChannelMessageEvent.class, this);
	}

	@Override
	public void accept(ChannelMessageEvent event) {
		if (heroQuest.isEnable() && event.getMessage().startsWith("!")) {
			EventUser user = event.getUser();
			Player player = retreivePlayer(user);
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
					sendMessage(event, "/me !d6 !2d6 !deplacement !attaque !defense !esprit !perception");
					break;
				case "!help":
					sendMessage(event, "/me !d6 !2d6 !mov !atk !def !mind !perc");
					break;
				case "!test": // nothing to do
					break;
				default:
					doDoesNotUnderstand(event, player);
				}
			}
		}
	}

	private void doD6(ChannelMessageEvent event, Player player) {
		EventUser user = event.getUser();
		int roll = dice.roll(1, 6);
		sendMessage(event, "/me " + player.getName() + " (@" + user.getName() + ") obtient " + roll);
	}

	private void do2D6(ChannelMessageEvent event, Player player) {
		EventUser user = event.getUser();
		int roll = dice.roll(2, 6);
		sendMessage(event, "/me " + player.getName() + " (@" + user.getName() + ") obtient " + roll);
	}

	private void doDoesNotUnderstand(ChannelMessageEvent event, Player player) {
		EventUser user = event.getUser();
		String message = "/me je ne comprend pas " + event.getMessage() + " @" + user.getName();
		sendMessage(event, message);
	}

	private void doSpirit(ChannelMessageEvent event, Player player) {
		EventUser user = event.getUser();
		TwitchChat bot = event.getTwitchChat();
		int roll = dice.roll(1, 6);
		String message;
		if (roll <= player.getMind() + 1) {
			message = "/me " + player.getName() + " (@" + user.getName() + ") TODO";
		} else {
			message = "/me " + player.getName() + " (@" + user.getName() + ") TODO";
		}
		bot.sendMessage(event.getChannel().getName(), message);
	}

	private void doPerception(ChannelMessageEvent event, Player player) {
		EventUser user = event.getUser();
		TwitchChat bot = event.getTwitchChat();
		int roll = dice.roll(1, 12);
		String message;
		if (roll == 12) {
			message = "/me " + player.getName() + " (@" + user.getName() + ") entant un grognement sinistre";
		} else if (roll <= player.getPerc()) {
			message = "/me " + player.getName() + " (@" + user.getName() + ") trouve un trésor";
		} else {
			message = "/me " + player.getName() + " (@" + user.getName() + ") entend un bruit suspect";
		}
		bot.sendMessage(event.getChannel().getName(), message);
	}

	private void doDefense(ChannelMessageEvent event, Player player) {
		EventUser user = event.getUser();
		int count = 0;
		for (int i = 0; i < player.getDefence(); i++) {
			count += dice.roll(1, 6) >= 5 ? 1 : 0;
		}
		String message = "/me " + player.getName() + " (@" + user.getName() + ") obtient " + count + " 🛡";
		sendMessage(event, message);
	}

	private void doAttaque(ChannelMessageEvent event, Player player) {
		EventUser user = event.getUser();
		int count = 0;
		for (int i = 0; i < player.getAttack(); i++) {
			count += dice.roll(1, 6) >= 4 ? 1 : 0;
		}
		String message = "/me " + player.getName() + " (@" + user.getName() + ") obtient " + count + " 💀";
		sendMessage(event, message);
	}

	private void doMove(ChannelMessageEvent event, Player player) {
		EventUser user = event.getUser();
		int roll = dice.roll(2, 6);
		String message = "/me " + player.getName() + " (@" + user.getName() + ") se déplace de " + roll + " cases max";
		sendMessage(event, message);
	}

	private Player retreivePlayer(EventUser user) {
		try (SqlSession session = sessionFactory.open()) {
			HeroQuestDao dao = session.getMapper(HeroQuestDao.class);
			return dao.readByPseudo(user.getName());
		}
	}

	private void sendMessage(ChannelMessageEvent event, String message) {
		event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
	}

}
