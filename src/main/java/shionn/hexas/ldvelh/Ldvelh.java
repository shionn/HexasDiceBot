package shionn.hexas.ldvelh;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Scope("singleton")
@Data
public class Ldvelh {

	private boolean enable;
	private int monsterHab;
	private int heroHab;

}
