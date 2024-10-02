package shionn.hexas.heroquest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;

@Component
@SessionScope
@Getter
public class HeroQuest {

	@Setter
	private CharacterClass active;
	private Character barbarian = new Character();
	private Character dwarf = new Character();
	private Character elf = new Character();
	private Character magician = new Character();
	private Character monster = new Character();

	public Character getActivePlayer() {
		if (active == null)
			return null;
		return switch (active) {
			case barbarian -> barbarian;
			case dwarf -> dwarf;
			case elf -> elf;
			case magician -> magician;
			default -> throw new IllegalArgumentException("Unexpected value: " + active);
		};
	}

}
