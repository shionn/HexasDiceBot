package shionn.hexas.ldvelh;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
public class Dices {

	private DiceMode mode = DiceMode.CLOSED;

	private Map<String, Integer> dices = new HashMap<>();

	public boolean put(String name, int dice) {
		return dices.putIfAbsent(name, dice) == null;
	}


	public Collection<Integer> values() {
		return dices.values();
	}

	public void close() {
		mode = DiceMode.CLOSED;
		dices.clear();
	}

	public void setMode(DiceMode mode) {
		this.mode = mode;
	}

	public DiceMode getMode() {
		return mode;
	}

}
