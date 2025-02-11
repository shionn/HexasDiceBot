package shionn.hexas.jdr.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shionn.hexas.jdr.Player;

public interface JdrPlayersDao {

	@Select("SELECT * FROM whisperia_player ORDER BY name")
	@Results({
		@Result(column = "classe", property = "clazz")
	})
	public List<Player> list();

	@Select("SELECT * FROM whisperia_player WHERE pseudo = #{name}")
	@Results({
		@Result(column = "classe", property = "clazz")
	})
	public Player readByPseudo(String name);

	@Select("SELECT * FROM whisperia_player WHERE id = #{id}")
	@Results({
		@Result(column = "classe", property = "clazz")
	})
	public Player read(int id);

	
	@Insert("INSERT INTO whisperia_player ( "
			+ "pseudo, name, classe, strenght, dexterity, constitution, intelligence, wisdom, charisma ) VALUES ( "
			+ "#{pseudo}, #{name}, #{clazz}, #{strenght}, #{dexterity}, #{constitution}, #{intelligence}, #{wisdom}, #{charisma} ) ")
	public void create(Player player);

	@Delete("DELETE FROM whisperia_player WHERE id = #{id}")
	public void rm(int id);

	@Select("UPDATE whisperia_player SET priority = #{priority} WHERE id = #{id}")
	public void updatePriority(Player player);

	@Select("SELECT * FROM whisperia_player ORDER BY priority DESC")
	@Results({
		@Result(column = "classe", property = "clazz")
	})
	public List<Player> priorities();

	@Update("UPDATE whisperia_player SET pseudo = #{pseudo}, name = #{name}, classe = #{clazz}, "
			+ "strenght = #{strenght}, dexterity = #{dexterity}, constitution = #{constitution}, intelligence = #{intelligence}, "
			+ "wisdom = #{wisdom}, charisma = #{charisma} WHERE id = #{id} ")
	public void udpate(Player player);

}
