package com.ma.cm.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ma.cm.entity.FTP;

public interface FTPMapper {
	
	@Select("SELECT * FROM ftp")
	List<FTP> getAll();
	
	@Select("SELECT * FROM ftp WHERE id = #{id}")
	FTP getOne(long id);

	@Update("UPDATE ftp SET host=#{host}, port=#{port}, username=#{username}, password=#{password} WHERE id = #{id}")
	void update(FTP ftp);

}