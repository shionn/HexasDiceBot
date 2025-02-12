package shionn.hexas.jdr;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@ApplicationScope
@RequiredArgsConstructor
public class Jdr {

	@Getter
	@Setter
	private boolean enable;

	public int mod(int stat) {
		if (stat <=1) return -5;
		if (stat <=3) return -4;
		if (stat <=5) return -3;
		if (stat <=7) return -2;
		if (stat <=9) return -1;
		if (stat >=20) return 5;
		if (stat >=18) return 4;
		if (stat >=16) return 3;
		if (stat >=14) return 2;
		if (stat >=12) return 1;
		return 0;
	}

	public String strmod(int stat) {
		int mod = mod(stat);
		if (mod < 0) {
			return Integer.toString(mod);
		}
		return "+" + Integer.toString(mod);
	}

	public String laStatName(String mod) {
		return switch (mod) {
		case "for" -> "la force";
		case "str" -> "la force";
		case "dex" -> "la déxterité";
		case "con" -> "la constitution";
		case "int" -> "l'intéligence";
		case "sag" -> "la sagesse";
		case "wis" -> "la sagesse";
		case "cha" -> "la charisme";
		default -> throw new IllegalArgumentException("Unexpected value: " + mod);
		};
	}

}
