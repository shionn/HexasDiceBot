package shionn.hexas.heroquest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shionn.hexas.heroquest.Player;

public interface HeroQuestDao {

	@Insert("INSERT INTO heroquest_player ( pseudo, name, attack, defence, body, mind ) VALUES ( "
			+ "#{pseudo}, #{name}, #{attack}, #{defence}, #{body}, #{mind} )")
	int createPlayer(Player player);

	@Select("SELECT * FROM heroquest_player ORDER BY name")
	List<Player> listPlayers();

	@Select("SELECT * FROM heroquest_player WHERE id = #{id}")
	Player readPlayer(int id);

	@Update("UPDATE heroquest_player SET " //
			+ "pseudo = #{pseudo}, name = #{name}, " //
			+ "attack = #{attack}, defence = #{defence}, body = #{body}, mind = #{mind} " //
			+ "WHERE id = #{id} ")
	void editPlayer(Player player);

	@Update("UPDATE heroquest_player SET enable = #{enable} WHERE id = #{id}")
	void updateEnable(Player player);

}
