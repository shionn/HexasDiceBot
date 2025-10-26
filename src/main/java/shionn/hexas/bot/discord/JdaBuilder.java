package shionn.hexas.bot.discord;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import shionn.hexas.heroquest.discord.HeroQuestEventListener;

@Component
public class JdaBuilder {

	@Bean
	@Scope("singleton")
	public JDA buildJDA(HeroQuestEventListener heroquest) throws IOException, InterruptedException {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("token.properties"));
		return JDABuilder
				.create(properties.getProperty("token"),
						Arrays
								.asList(GatewayIntent.MESSAGE_CONTENT,
										GatewayIntent.GUILD_MESSAGES))
				.addEventListeners(heroquest)
				.build()
				.awaitReady();

	}
}
