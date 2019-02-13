package com.pp.rc.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.pp.rc.entry.User;
import com.pp.rc.mapper.UserMapper;
import com.pp.rc.service.UserService;

/**
 * 
 * @author pp
 * @Date 2019年2月13日下午3:23:38
 * @Description
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public User getById(int id) {
		// 设置 key
		String key = "user_" + id;
		// 判断 key是否存在
		// 存在 从缓存中获取
		// 不存在 从数据库中获取 并保存到缓存中
		ValueOperations<String, User> operations = redisTemplate.opsForValue();
		Boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			System.out.println(hasKey);
			User user = operations.get(key);
			LOGGER.info("从缓存中获取了用户 >> " + user.getName());
			return user;
		}

		User user = userMapper.selectById(id);
		LOGGER.info("从数据库中获取了用户 >> " + user.toString());

		// 将数据放到缓存中 设置 10 分钟 后 失效
		operations.set(key, user, 10, TimeUnit.MINUTES);
		LOGGER.info("将数据插入缓存 >> " + user.toString());
		return user;
	}

	/**
	 * 	更新用户信息 
	 * 	更新成功 ，更新缓存中的数据
	 * 	更新失败 ，缓存中数据不处理
	 */
	public int update(User user) {
		int i = userMapper.updateById(user);
		if (i > 0) {
			User user2 = userMapper.selectById(user.getId());
			ValueOperations<String, User> operations = redisTemplate.opsForValue();
			String key = "user_" + user.getId();
			Boolean hasKey = redisTemplate.hasKey(key);
			if (hasKey) {
				// 返回修改前的值
				User user4 = operations.getAndSet(key, user2);
				LOGGER.info("更新前缓存 >> " + user4.toString());
				// 获取修改后的值
				User user3 = operations.get(key);
				LOGGER.info("更新后缓存 >> " + user3.toString());
			}
		}
		return i;
	}

	@Override
	public List<User> getAll() {
		List<User> list = userMapper.selectList(null);
		return list;
	}

	/**
	 * 删除用户 删除成功 从缓存中删除对应的数据 删除失败 缓存中数据不做处理
	 */
	public int delete(int id) {
		int i = userMapper.deleteById(id);
		if (i > 0) {
			String key = "user_" + id;
			Boolean hasKey = redisTemplate.hasKey(key);
			if (hasKey) {
				Boolean delete = redisTemplate.delete(key);
				if(delete) {
					LOGGER.info("删除缓存成功 ！！！");
				}else {
					LOGGER.info("删除缓存失败！！！");
				}
				
			}
		}
		return i;
	}

	@Override
	public int save(User user) {
		// TODO Auto-generated method stub
		int i = userMapper.insert(user);
		return i;
	}

}
