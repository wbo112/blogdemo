package com.example.springbootmybatis.service;

import com.example.springbootmybatis.entity.User;
import com.example.springbootmybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {



     List<User> findAll() ;
}
