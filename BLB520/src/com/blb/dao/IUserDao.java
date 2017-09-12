package com.blb.dao;

import java.util.List;

import com.blb.domain.User;


public interface IUserDao {
	public abstract List<User> selectAll();
}
