package shionn.hexas.command;

import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import com.github.twitch4j.chat.ITwitchChat;

import shionn.hexas.DiceMode;
import shionn.hexas.Dices;

@Component
@ApplicationScope
public class DiceCommand {
	private Random seed = new Random();

	@Autowired
	@Value("${twitch.channel}")
	private String channel;

	@Autowired
	private ITwitchChat bot;

	@Autowired
	private Dices dices;

	private int delay = 0;

	public void request2D6() {
		if (dices.getMode() == DiceMode.CLOSED) {
			dices.setMode(DiceMode.TWO_D6);
			bot.sendMessage(channel, "> Vous avez 60 secondes pour lancer des !2D6 <");
			delay = 61;
		}
	}

	public void requestD6() {
		if (dices.getMode() == DiceMode.CLOSED) {
			dices.setMode(DiceMode.D6);
			bot.sendMessage(channel, "> Vous avez 60 secondes pour lancer des !D6 <");
			delay = 61;
		}
	}

	@Scheduled(fixedRate = 1000)
	public void update() {
		if (delay > 0) {
			delay--;
			if (delay ==0 ) {
				Collection<Integer> values = dices.values();
				if (values.isEmpty()) {
					bot.sendMessage(channel, "/me n'as pas eu de lancé ");
				} else {
					Integer dice = values.stream().skip(seed.nextInt(values.size())).findFirst().orElse(0);
					bot.sendMessage(channel, "/me le chat obtiens " + dice);
				}
				dices.close();
			}
		}
	}

}
