package com.ma.cm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ma.cm.entity.Column;

public interface ColumnMapper {

	@Select("SELECT * FROM column_ WHERE productId = #{productId}")
	List<Column> getByProduct(long productId);

	@Select("SELECT * FROM column_ WHERE productId = #{productId} AND columnId = #{columnId}")
	Column getOne(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId);

	@Insert("INSERT INTO column_(productId, columnId, name, type, poster, link) VALUES(#{productId}, #{columnId}, #{name}, #{type}, #{poster}, #{link})")
	void insert(Column column);

	@Update("UPDATE column_ SET name=#{name}, type=#{type}, poster=#{poster}, link=#{link} WHERE productId=#{productId} AND columnId = #{columnId}")
	void update(Column column);

	@Delete("DELETE FROM column_ WHERE productId = #{productId} AND columnId = #{columnId}")
	void delete(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId);
	
	@Delete("DELETE FROM column_ WHERE productId = #{productId}")
	void deleteByProduct(long productId);

}