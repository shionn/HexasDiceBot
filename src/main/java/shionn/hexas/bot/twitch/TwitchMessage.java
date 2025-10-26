package shionn.hexas.bot.twitch;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import shionn.hexas.bot.MessageEvent;
import shionn.hexas.bot.Source;

public class TwitchMessage implements MessageEvent {

	private ChannelMessageEvent event;

	public TwitchMessage(ChannelMessageEvent event) {
		this.event = event;
	}

	@Override
	public void answer(String message) {
		String text = message.replaceAll("%USER%", "@" + getPseudo());
		event.getTwitchChat().sendMessage("/me " + event.getChannel().getName(), text);
	}

	@Override
	public Source getApplication() {
		return Source.twitch;
	}

	@Override
	public String getPseudo() {
		return event.getUser().getName();
	}

	@Override
	public String getMessage() {
		return event.getMessage();
	}

}
