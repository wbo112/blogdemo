package com.springboot.transaction.mapper;


import com.springboot.transaction.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> findAll(Map<String,Object> params);
}