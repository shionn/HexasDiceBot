package shionn.hexas.ldvelh;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
public class Ldvelh {

	private int monsterHab;
	private int heroHab;

}
