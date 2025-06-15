package shionn.hexas.bot;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.twitch4j.chat.ITwitchChat;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BotClient implements DisposableBean {

	@Autowired
	@Value("${twitch.channel}")
	private String channel;

	private final ITwitchChat bot;

	public void sendMessage(String message) {
		bot.sendMessage(channel, message);
	}

	@Override
	public void destroy() throws Exception {
		bot.close();
	}
	
}
