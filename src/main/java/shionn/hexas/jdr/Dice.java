package shionn.hexas.jdr;

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

}
