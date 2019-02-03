package com.ma.cm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ma.cm.entity.Product;

public interface ProductMapper {
	
	@Select("SELECT * FROM product")
	List<Product> getAll();
	
	@Select("SELECT * FROM product WHERE id = #{id}")
	Product getOne(long id);

	@Insert("INSERT INTO product(id, name) VALUES(#{id}, #{name})")
	void insert(Product user);

	@Update("UPDATE product SET name=#{name} WHERE id = #{id}")
	void update(Product user);

	@Delete("DELETE FROM product WHERE id = #{id}")
	void delete(long id);
}
