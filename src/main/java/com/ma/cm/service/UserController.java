package com.ma.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ma.cm.entity.User;
import com.ma.cm.mapper.UserMapper;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserMapper userMapper;

	@RequestMapping
	public List<User> getUsers() {
		List<User> users = userMapper.getAll();
		return users;
	}

	@RequestMapping("/{id}")
	public User getUser(@PathVariable int id) throws Exception {
		User user = userMapper.getOne(id);
		if(user != null) {
			return user;
		} else {
			// TODO throw exception will get 5XX, we should use 4XX instead
			throw new Exception("user %s is not exist");
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public User save(User user) {
		userMapper.insert(user);
		return user;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") int id) {
		userMapper.delete(id);
	}

	@RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public User update(User user) {
		userMapper.update(user);
		// TODO return info from DB instead info from URL
		// TODO if patch, update value which is setted
		return user;
	}

}
