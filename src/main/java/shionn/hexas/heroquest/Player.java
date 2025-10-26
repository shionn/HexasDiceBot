package shionn.hexas.heroquest;

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
	private String pseudo, discord;
	private String name;

	private int attack, defence, body, mind, perc;

	private boolean enable;

}
