package shionn.hexas.heroquest.discord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import shionn.hexas.bot.discord.DiscordMessage;
import shionn.hexas.heroquest.HeroQuestMessageConsumer;

@Component
public class HeroQuestEventListener extends ListenerAdapter {

	@Autowired
	private HeroQuestMessageConsumer delegate;

	@Override
	public void onReady(ReadyEvent event) {
		System.out.println("Discord bot readey");
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
//		System.out.println(event.getAuthor().getName() + " :: " + event.getMessage().getContentRaw());
		delegate.conssume(new DiscordMessage(event));
	}

}
