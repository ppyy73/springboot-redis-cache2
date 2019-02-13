package com.pp.rc.service;

import java.util.List;

import com.pp.rc.entry.User;

public interface UserService {
	User getById(int id);

	int update(User user);

	List<User> getAll();

	int delete(int id);

	int save(User user);
}
