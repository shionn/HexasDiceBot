package shionn.hexas.jdr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

	private int id;
	private String pseudo;
	private String name;
	private String clazz;

	private int strenght, dexterity, constitution, intelligence, wisdom, charisma;
	private int priority;

}
