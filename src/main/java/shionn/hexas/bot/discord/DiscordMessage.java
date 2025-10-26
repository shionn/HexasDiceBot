package shionn.hexas.bot.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shionn.hexas.bot.MessageEvent;
import shionn.hexas.bot.Source;

public class DiscordMessage implements MessageEvent {

	private MessageReceivedEvent event;

	public DiscordMessage(MessageReceivedEvent event) {
		this.event = event;
	}

	@Override
	public void answer(String message) {
		String text = message.replaceAll("%USER%", "<@" + event.getAuthor().getId() + ">");
		event.getMessage().reply(text).queue();
	}

	@Override
	public Source getApplication() {
		return Source.discord;
	}

	@Override
	public String getPseudo() {
		return event.getAuthor().getName();
	}

	@Override
	public String getMessage() {
		return event.getMessage().getContentRaw();
	}

}
