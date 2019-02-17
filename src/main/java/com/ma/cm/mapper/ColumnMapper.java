package com.ma.cm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ma.cm.entity.Column;

public interface ColumnMapper {

	@Select("SELECT * FROM column_ WHERE productId = #{productId}")
	List<Column> getByProduct(long productId);

	@Select("SELECT * FROM column_ WHERE productId = #{productId} AND columnId = #{columnId}")
	Column getOne(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId);

	@Insert("<script>"
			+ "<if test='columnId == 0'>"
			+ "INSERT INTO column_(productId, name, type, poster, link, options) VALUES(#{productId}, #{name}, #{type}, #{poster}, #{link}, #{options})"
			+ "</if>"
			+ "<if test='columnId != 0'>"
			+ "INSERT INTO column_(productId, columnId, name, type, poster, link, options) VALUES(#{productId}, #{columnId}, #{name}, #{type}, #{poster}, #{link}, #{options})"
			+ "</if>"
			+ "</script>")
	@Options(useGeneratedKeys = true, keyProperty="columnId", keyColumn="columnId")
	void insert(Column column);

	@Update("<script>"
			+ "UPDATE column_ SET name=#{name}, type=#{type}, "
			+ "<if test='poster != null'>"
			+ "poster=#{poster}, "
			+ "</if>"
			+ "link=#{link}, options=#{options} WHERE productId=#{productId} AND columnId = #{columnId}"
			+ "</script>")
	void update(Column column);

	@Delete("DELETE FROM column_ WHERE productId = #{productId} AND columnId = #{columnId}")
	void delete(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId);
	
	@Delete("DELETE FROM column_ WHERE productId = #{productId}")
	void deleteByProduct(long productId);

}
