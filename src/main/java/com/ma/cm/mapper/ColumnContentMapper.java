package com.ma.cm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ma.cm.entity.ColumnContent;

public interface ColumnContentMapper {

	List<ColumnContent> getByProductByColumn(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId);
	
	List<ColumnContent> getByProductByContentByType0(@Param(value = "productId") long productId, @Param(value = "contentId") long contentId);
	
	List<ColumnContent> getByProductByContentByType1(@Param(value = "productId") long productId, @Param(value = "contentId") long contentId);
	
	List<ColumnContent> getDetailContentsByProductByColumn(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId);

	List<ColumnContent> getDetailColumnsByProductByColumn(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId);

	@Select("SELECT * FROM column_content WHERE productId = #{productId} AND columnId = #{columnId} AND type = #{type} AND contentId = #{contentId}")
	ColumnContent getOne(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId, @Param(value = "type") long type, @Param(value = "contentId") long contentId);

	@Insert("INSERT INTO column_content(productId, columnId, type, contentId, position) VALUES(#{productId}, #{columnId}, #{type}, #{contentId}, #{position})")
	void insert(ColumnContent columnContent);

	@Update("UPDATE column_content SET position=#{position} WHERE productId = #{productId} AND columnId = #{columnId} AND type = #{type} AND contentId = #{contentId}")
	void update(ColumnContent column);

	@Delete("DELETE FROM column_content WHERE productId = #{productId} AND columnId = #{columnId} AND type = #{type} AND contentId = #{contentId}")
	void delete(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId, @Param(value = "type") long type, @Param(value = "contentId") long contentId);

	@Delete("DELETE FROM column_content WHERE productId = #{productId} AND columnId = #{columnId}")
	void deleteByProductByColumn(@Param(value = "productId") long productId, @Param(value = "columnId") long columnId);

	@Delete("DELETE FROM column_content WHERE productId = #{productId}")
	void deleteByProduct(long productId);
	
}
