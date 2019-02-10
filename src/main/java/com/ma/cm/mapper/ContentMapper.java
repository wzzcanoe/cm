package com.ma.cm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ma.cm.entity.Content;

public interface ContentMapper {

	@Select("SELECT * FROM content WHERE productId = #{productId}")
	List<Content> getByProduct(long productId);

	@Select("SELECT * FROM content WHERE productId = #{productId} AND contentId = #{contentId}")
	Content getOne(@Param(value = "productId") long productId, @Param(value = "contentId") long contentId);

	@Insert("<script>"
			+ "<if test='contentId == 0'>"
			+ "INSERT INTO content(productId, poster, icon, screenShot, type, link, tip) VALUES(#{productId}, #{poster}, #{icon}, #{screenShot}, #{type}, #{link}, #{tip})"
			+ "</if>"
			+ "<if test='contentId != 0'>"
			+ "INSERT INTO content(productId, contentId, poster, icon, screenShot, type, link, tip) VALUES(#{productId}, #{contentId}, #{poster}, #{icon}, #{screenShot}, #{type}, #{link}, #{tip})"
			+ "</if>"
			+ "</script>")
	@Options(useGeneratedKeys = true, keyProperty="contentId", keyColumn="contentId")
	void insert(Content content);

	@Update("UPDATE content SET poster=#{poster}, icon=#{icon}, screenShot=#{screenShot}, type=#{type}, link=#{link}, tip=#{tip} "
			+ "WHERE productId = #{productId} AND contentId = #{contentId}")
	void update(Content content);

	@Delete("DELETE FROM content WHERE productId = #{productId} AND contentId = #{contentId}")
	void delete(@Param(value = "productId") long productId, @Param(value = "contentId") long contentId);

	@Delete("DELETE FROM content WHERE productId = #{productId}")
	void deleteByProduct(long productId);

}