package com.pp.rc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pp.rc.entry.User;
import com.pp.rc.service.UserService;


/**
 * 
 * @author pp
 * @Date 2019年2月13日下午3:19:40
 * @Description
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	@ResponseBody
	public User getById(@PathVariable int id) {
		User user = userService.getById(id);
		return user;
	}

	@PutMapping("/update")
	@ResponseBody
	public int update(@RequestBody User user) {
		return userService.update(user);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public int delete(@PathVariable int id) {
		return userService.delete(id);
	}

	@PostMapping("/save")
	@ResponseBody
	public int save(@RequestBody User user) {
		return userService.save(user);
	}

	@RequestMapping("/list")
	@ResponseBody
	public List<User> userAll() {
		return userService.getAll();
	}
}
