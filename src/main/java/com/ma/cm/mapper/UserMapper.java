package com.ma.cm.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ma.cm.entity.User;

public interface UserMapper {
	
	@Select("SELECT * FROM user")
	List<User> getAll();
	
	@Select("SELECT * FROM user WHERE id = #{id}")
	User getOne(long id);

	@Insert("INSERT INTO user(id, name) VALUES(#{id}, #{name})")
	void insert(User user);

	@Update("UPDATE user SET name=#{name} WHERE id = #{id}")
	void update(User user);

	@Delete("DELETE FROM user WHERE id = #{id}")
	void delete(long id);

}