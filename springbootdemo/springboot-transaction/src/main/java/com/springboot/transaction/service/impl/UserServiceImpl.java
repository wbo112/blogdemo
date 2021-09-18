package com.springboot.transaction.service.impl;


import com.springboot.transaction.entity.User;
import com.springboot.transaction.mapper.UserMapper;
import com.springboot.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public List<User> findAll(Map<String,Object> params) {
        return userMapper.findAll(params);
    }
}
