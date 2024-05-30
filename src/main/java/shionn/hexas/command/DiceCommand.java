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
	private static final int DELAY = 46;

	private Random seed = new Random();

	@Autowired
	@Value("${twitch.channel}")
	private String channel;

	@Autowired
	private ITwitchChat bot;

	@Autowired
	private Dices dices;

	private int delay = 0;

	private int heroHab;

	private int monsterHab;

	public void request2D6() {
		if (dices.getMode() == DiceMode.CLOSED) {
			dices.setMode(DiceMode.TWO_D6);
			bot.sendMessage(channel, "> Vous avez " + (DELAY - 1) + " secondes pour lancer des !2D6 <");
			delay = DELAY;
		}
	}

	public void requestD6() {
		if (dices.getMode() == DiceMode.CLOSED) {
			dices.setMode(DiceMode.D6);
			bot.sendMessage(channel, "> Vous avez " + (DELAY - 1) + " secondes pour lancer des !D6 <");
			delay = DELAY;
		}
	}

	public void requestBattleDice(int heroHab, int monsterHab) {
		if (dices.getMode() == DiceMode.CLOSED) {
			this.heroHab = heroHab;
			this.monsterHab = monsterHab;
			dices.setMode(DiceMode.BATTLE);
			bot.sendMessage(channel, "> Une passe d'arme commence, lancer des !2D6 <");
			delay = DELAY;
		}
	}

	@Scheduled(fixedRate = 1000)
	public void update() {
		if (delay > 0) {
			delay--;
			if (delay ==0 ) {
				Collection<Integer> values = dices.values();
				switch (dices.getMode()) {
					case D6, TWO_D6:
						if (values.isEmpty()) {
							bot.sendMessage(channel, "/me n'as pas eu de lancé ");
						} else {
							Integer dice = values.stream().skip(seed.nextInt(values.size())).findFirst().orElse(0);
							bot.sendMessage(channel, "/me le chat obtiens " + dice);
						}
						break;
					case BATTLE:
						if (values.size() < 2) {
							bot.sendMessage(channel, "/me n'as pas eu assez de lancé ");
						} else {
							Integer hero = values.stream().skip(seed.nextInt(values.size())).findFirst().orElse(0);
							values.remove(hero);
							Integer monster = values.stream().skip(seed.nextInt(values.size())).findFirst().orElse(0);
							StringBuilder message = new StringBuilder().append("Le héro obtiens ")
									.append(hero)
									.append("+")
									.append(heroHab)
									.append("=")
									.append(hero + heroHab)
									.append(" :: ")
									.append("Le monstre obtiens ")
									.append(monster)
									.append("+")
									.append(monsterHab)
									.append("=")
									.append(monster + monsterHab)
									.append(" >> ");
							if (hero + heroHab > monster + monsterHab) {
								message.append("Victoire");
							} else if (hero + heroHab < monster + monsterHab) {
								message.append("Défaite");
							} else {
								message.append("Egalité");
							}
							bot.sendMessage(channel, message.toString());
						}
						break;
					default:
						throw new IllegalArgumentException("illegal value <" + dices.getMode() + ">");
				}

				dices.close();
			}
		}
	}

}
