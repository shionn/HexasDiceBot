package shionn.hexas.bot;

import java.util.Random;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;

import shionn.hexas.DiceMode;
import shionn.hexas.Dices;

@Component
@Scope("singleton")
public class ChannelMessageEventConsumer implements Consumer<ChannelMessageEvent> {
	private Random seed = new Random();

	@Autowired
	private Dices dices;

	public ChannelMessageEventConsumer(ITwitchChat bot) {
		bot.getEventManager().onEvent(ChannelMessageEvent.class, this);
	}

	@Override
	public void accept(ChannelMessageEvent event) {
		EventUser user = event.getUser();
		System.out.println(event.getChannel().getName() + ":" + user.getName() + ":" + event.getMessage());
		TwitchChat bot = event.getTwitchChat();
		if ("!test".equals(event.getMessage())) {
			bot.sendMessage(event.getChannel().getName(), "J'ai recu ton test @" + user.getName());
		}
		if ("!2d6".equalsIgnoreCase(event.getMessage())) {

			int dice = D6(seed) + D6(seed);
			doDice(event, dice, DiceMode.TWO_D6);

		}
		if ("!d6".equalsIgnoreCase(event.getMessage())) {
			int dice = D6(seed);
			doDice(event, dice, DiceMode.D6);
		}

	}

	private void doDice(ChannelMessageEvent event, int dice, DiceMode mode) {
		EventUser user = event.getUser();
		TwitchChat bot = event.getTwitchChat();
		switch (dices.getMode()) {
			case CLOSED:
				bot.sendMessage(event.getChannel().getName(),
						"/me @" + user.getName() + " pas de lancer de des en ce moment");
				break;
			case TWO_D6:
			case D6:
				if (mode == dices.getMode()) {
					if (dices.put(user.getName(), dice)) {
						bot.sendMessage(event.getChannel().getName(), "/me @" + user.getName() + " obtiens " + dice);
					} else {
						bot.sendMessage(event.getChannel().getName(),
								"/me @" + user.getName() + " a déjà fait son lancé");
					}
				} else {
					bot.sendMessage(event.getChannel().getName(), "/me @" + user.getName() + " mauvais lancé");
				}
				break;
		}
	}

	private int D6(Random seed) {
		return seed.nextInt(1, 7);
	}

}
