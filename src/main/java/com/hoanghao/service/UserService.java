package com.hoanghao.service;

import com.hoanghao.exception.InvalidJwtAuthenticationException;
import com.hoanghao.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwtToken) throws InvalidJwtAuthenticationException;

    public User findUserByEmail(String email) throws Exception;
}
