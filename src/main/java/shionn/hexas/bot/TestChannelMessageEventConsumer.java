package shionn.hexas.bot;

import java.util.function.Consumer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;

@Component
@Scope("singleton")
public class TestChannelMessageEventConsumer implements Consumer<ChannelMessageEvent> {

	public TestChannelMessageEventConsumer(ITwitchChat bot) {
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

	}

}
