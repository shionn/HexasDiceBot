package shionn.hexas.jdr.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import shionn.hexas.jdr.Player;

public interface JdrPlayersDao {

	@Select("SELECT * FROM whisperia_player ORDER BY player")
	@Results({
		@Result(column = "player", property = "name"),
		@Result(column = "classe", property = "clazz")
	})
	public List<Player> list();

	@Select("SELECT * FROM whisperia_player WHERE player = #{name}")
	@Results({
		@Result(column = "player", property = "name"),
		@Result(column = "classe", property = "clazz")
	})
	public Player read(String name);
	
	@Insert("INSERT INTO whisperia_player ( "
			+ "player, classe, strenght, dexterity, constitution, intelligence, wisdom, charisma ) VALUES ( "
			+ "#{name}, #{clazz}, #{strenght}, #{dexterity}, #{constitution}, #{intelligence}, #{wisdom}, #{charisma} ) ")
	public void create(Player player);

	@Delete("DELETE FROM whisperia_player WHERE player = #{name}")
	public void rm(String name);

	@Select("UPDATE whisperia_player SET priority = #{priority} WHERE id = #{id}")
	public void updatePriority(Player player);

	@Select("SELECT * FROM whisperia_player ORDER BY priority DESC")
	@Results({
		@Result(column = "player", property = "name"),
		@Result(column = "classe", property = "clazz")
	})
public List<Player> priorities();

}
