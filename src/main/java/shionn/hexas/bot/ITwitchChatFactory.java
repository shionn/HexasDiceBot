package shionn.hexas.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;

@Component
public class ITwitchChatFactory {

	private static final String ALWAYS_TWITCH = "twitch";

	@Autowired
	@Value("${twitch.token}")
	private String token;
	@Autowired
	@Value("${twitch.channel}")
	private String channel;

	@Bean(destroyMethod = "close")
	@Scope("singleton")
	public ITwitchChat bot() {
		OAuth2Credential credential = new OAuth2Credential(ALWAYS_TWITCH, token);
		ITwitchChat client = TwitchChatBuilder.builder().withChatAccount(credential).build();
		client.joinChannel(channel);
//		client.getEventManager().onEvent(ChannelMessageEvent.class, messageEventConsumer);
		return client;
	}


}
