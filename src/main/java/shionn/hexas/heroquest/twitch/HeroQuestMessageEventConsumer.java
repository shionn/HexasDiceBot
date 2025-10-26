package shionn.hexas.heroquest.twitch;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import shionn.hexas.bot.twitch.TwitchMessage;
import shionn.hexas.heroquest.HeroQuestMessageConsumer;

@Component
@Scope("singleton")
public class HeroQuestMessageEventConsumer implements Consumer<ChannelMessageEvent> {

	@Autowired
	private HeroQuestMessageConsumer delegate;

	public HeroQuestMessageEventConsumer(ITwitchChat bot) {
		bot.getEventManager().onEvent(ChannelMessageEvent.class, this);
	}

	@Override
	public void accept(ChannelMessageEvent event) {
		delegate.conssume(new TwitchMessage(event));
	}

}
