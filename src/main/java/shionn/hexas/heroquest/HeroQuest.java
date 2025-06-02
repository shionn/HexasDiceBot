package shionn.hexas.heroquest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.Getter;
import lombok.Setter;

@Component
@ApplicationScope
@Getter
@Setter
public class HeroQuest {

	private boolean enable;

}
