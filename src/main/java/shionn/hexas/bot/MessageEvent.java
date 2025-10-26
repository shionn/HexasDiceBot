package shionn.hexas.bot;

public interface MessageEvent {

	Source getApplication();

	String getPseudo();

	String getMessage();

	void answer(String message);

}
