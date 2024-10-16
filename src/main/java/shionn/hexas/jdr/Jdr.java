package shionn.hexas.jdr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import shionn.hexas.bot.BotClient;

@Component
@ApplicationScope
@RequiredArgsConstructor
public class Jdr {

	private final BotClient bot;
	@Getter
	@Setter
	private boolean enable;
	@Getter
	private List<Player> players = new ArrayList<Player>();
	
	public void add(Player player) {
		players.add(player);
		bot.sendMessage(player.getName()+" est enregistrer comme "+player.getClazz());
	}

	public void rm(String name) {
		if (players.removeIf(p->p.getName().equals(name))) {
			bot.sendMessage(name+" n'est plus un joueur");
		}
	}

	public Player getPlayer(String name) {
		return players.stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
	}
	
}
