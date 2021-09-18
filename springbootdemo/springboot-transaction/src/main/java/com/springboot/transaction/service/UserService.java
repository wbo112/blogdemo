package com.springboot.transaction.service;



import com.springboot.transaction.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {



     List<User> findAll(Map<String,Object> params) ;
}
