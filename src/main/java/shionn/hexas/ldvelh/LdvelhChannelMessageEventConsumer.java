package shionn.hexas.ldvelh;

import java.util.Random;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;

@Component
@Scope("singleton")
public class LdvelhChannelMessageEventConsumer implements Consumer<ChannelMessageEvent> {

	private Random seed = new Random();

	@Autowired
	private Dices dices;

	@Autowired
	private Ldvelh ldvelh;

	public LdvelhChannelMessageEventConsumer(ITwitchChat bot) {
		bot.getEventManager().onEvent(ChannelMessageEvent.class, this);
	}

	@Override
	public void accept(ChannelMessageEvent event) {
		EventUser user = event.getUser();
		if (ldvelh.isEnable()) {
			TwitchChat bot = event.getTwitchChat();
			if ("!2d6".equalsIgnoreCase(event.getMessage())) {
				switch (dices.getMode()) {
				case CLOSED:
					bot.sendMessage(event.getChannel().getName(),
							"/me @" + user.getName() + " pas de lancer de des en ce moment");
					break;
				case D6:
					bot.sendMessage(event.getChannel().getName(), "/me @" + user.getName() + " mauvais lancé");
					break;
				case TWO_D6:
				case BATTLE:
					int dice = D6(seed) + D6(seed);
					if (dices.put(user.getName(), dice)) {
						bot.sendMessage(event.getChannel().getName(), "/me @" + user.getName() + " obtiens " + dice);
					} else {
						bot.sendMessage(event.getChannel().getName(),
								"/me @" + user.getName() + " a déjà fait son lancé");
					}
					break;
				}
			}
			if ("!d6".equalsIgnoreCase(event.getMessage())) {
				switch (dices.getMode()) {
				case CLOSED:
					bot.sendMessage(event.getChannel().getName(),
							"/me @" + user.getName() + " pas de lancer de des en ce moment");
					break;
				case TWO_D6:
				case BATTLE:
					bot.sendMessage(event.getChannel().getName(), "/me @" + user.getName() + " mauvais lancé");
					break;
				case D6:
					int dice = D6(seed);
					if (dices.put(user.getName(), dice)) {
						bot.sendMessage(event.getChannel().getName(), "/me @" + user.getName() + " obtiens " + dice);
					} else {
						bot.sendMessage(event.getChannel().getName(),
								"/me @" + user.getName() + " a déjà fait son lancé");
					}
					break;
				}
			}
		}

	}

	private int D6(Random seed) {
		return seed.nextInt(1, 7);
	}

}
