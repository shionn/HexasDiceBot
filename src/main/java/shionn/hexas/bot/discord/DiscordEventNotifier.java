package shionn.hexas.bot.discord;

import org.springframework.stereotype.Component;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
public class DiscordEventNotifier extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		System.out.println(event);
	}
}
