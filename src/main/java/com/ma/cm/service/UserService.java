package com.ma.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.User;
import com.ma.cm.exception.UserNotExistException;
import com.ma.cm.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Transactional
	public List<User> gets() {
		List<User> users = userMapper.getAll();
		return users;
	}

	@Transactional
	public User save(User user) {
		userMapper.insert(user);
		return user;
	}

	@Transactional
	public User get(long id) {
		User user = userMapper.getOne(id);
		if (null == user) {
			throw new UserNotExistException(id);
		}
		return user;
	}

	@Transactional
	public User update(User user) {
		userMapper.update(user);
		User updatedUser = userMapper.getOne(user.getId());
		if (null == updatedUser) {
			throw new UserNotExistException(user.getId());
		}
		return updatedUser;
	}

	@Transactional
	public void delete(long id) {
		User user = userMapper.getOne(id);
		if (null == user) {
			throw new UserNotExistException(id);
		}
		userMapper.delete(id);
	}
}
