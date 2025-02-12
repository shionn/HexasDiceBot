package shionn.hexas.jdr;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;
import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;

import shionn.hexas.db.SessionFactory;
import shionn.hexas.jdr.dao.JdrPlayersDao;

@Component
@Scope("singleton")
public class JdrChannelMessageEventConsumer implements Consumer<ChannelMessageEvent> {

	private static final List<Integer> DICES = Arrays.asList(4, 6, 8, 10, 12, 20, 100);
	private static final Pattern ONE_DICE = Pattern.compile("!1?[dD](\\d{1,3})( [a-z]{3})?$");
	private static final Pattern MULTI_DICE = Pattern.compile("!(\\d{1,2})[dD](\\d{1,3})$");

	@Autowired
	private Dice dice;

	@Autowired
	private Jdr jdr;

	@Autowired
	private SessionFactory sessionFactory;

	public JdrChannelMessageEventConsumer(ITwitchChat bot) {
		bot.getEventManager().onEvent(ChannelMessageEvent.class, this);
	}

	@Override
	public void accept(ChannelMessageEvent event) {
		if (jdr.isEnable()) {
			EventUser user = event.getUser();
			Player player = retreivePlayer(user);
			if (player != null) {
				Matcher m = ONE_DICE.matcher(event.getMessage());
				if (m.find()) {
					rollOneDice(player, Integer.parseInt(m.group(1)), m.group(2), event);
				} else {
					m = MULTI_DICE.matcher(event.getMessage());
					if (m.find()) {
						rollMultiDice(player, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), event);
					}
				}
			}
		}
	}

	private void rollOneDice(Player player, int dice, String mod, ChannelMessageEvent event) {
		EventUser user = event.getUser();
		TwitchChat bot = event.getTwitchChat();
		if (isValid(1, dice)) {
			int roll = Dice(1, dice);
			String message;
			if (mod != null) {
				try {
					int stat = player.getStat(mod.trim());
					int total = roll + jdr.mod(stat);
					message = "/me " + player.getName() + " (@" + user.getName() + ") obtient " + roll
							+ jdr.strmod(stat) + "=" + total + " sur son D" + dice + " sur "
							+ jdr.laStatName(mod.trim());
				} catch (IllegalArgumentException e) {
					message = "/me @" + user.getName() + " je ne comprend pas : " + mod;
				}
			} else {
				message = "/me " + player.getName() + " (@" + user.getName() + ") obtient " + roll + " sur son D"
						+ dice;
			}
			bot.sendMessage(event.getChannel().getName(), message);
		}
	}

	private void rollMultiDice(Player player, int count, int dice, ChannelMessageEvent event) {
		EventUser user = event.getUser();
		TwitchChat bot = event.getTwitchChat();
		if (isValid(count, dice)) {
			int roll = Dice(count, dice);
			String message = "/me " + player.getPseudo() + " (@" + user.getName() + ") obtient " + roll + " sur ces "
					+ count + "D" + dice;
			bot.sendMessage(event.getChannel().getName(), message);
		}
	}

	private boolean isValid(int count, int dice) {
		return count >= 1 && count <= 99 && isDiceValid(dice);
	}

	private boolean isDiceValid(int dice) {
		return DICES.contains(dice);
	}

	private int Dice(int count, int max) {
		return dice.roll(count, max);
	}

	private Player retreivePlayer(EventUser user) {
		try (SqlSession session = sessionFactory.open()) {
			JdrPlayersDao dao = session.getMapper(JdrPlayersDao.class);
			return dao.readByPseudo(user.getName());
		}
	}

}
