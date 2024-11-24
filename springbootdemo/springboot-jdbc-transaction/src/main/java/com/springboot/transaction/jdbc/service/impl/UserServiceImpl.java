package com.springboot.transaction.jdbc.service.impl;


import com.springboot.transaction.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public boolean addUser() {
        List<Map<String,Object>> result=jdbcTemplate.queryForList("select id from user order by id desc limit 1");
        int nextIndex;
        if(Objects.isNull(result)||result.isEmpty()){
            nextIndex=0;
        }else{
            nextIndex= (int) result.get(0).get("id");
        }
        return 1==jdbcTemplate.update("insert into user values("+nextIndex+",'a','a')");
    }
}
