package shionn.hexas.jdr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
public class Dice {
	private Random seed = new Random();

	public int roll(int count, int max) {
		int result = 0;
		while (count > 0) {
			result += seed.nextInt(1, max + 1);
			count--;
		}
		return result;
	}

	public List<Integer> multiDices(int count, int max) {
		List<Integer> dices = new ArrayList<Integer>();
		for (int i = 0; i < count; i++) {
			dices.add(roll(1, max));
		}
		dices.sort(Integer::compare);
		return dices;
	}

}
