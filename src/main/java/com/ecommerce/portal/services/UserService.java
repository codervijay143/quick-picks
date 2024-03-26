package com.ecommerce.portal.services;


import com.ecommerce.portal.dtos.AuthResponse;
import com.ecommerce.portal.dtos.UserDTO;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.UserException;

import java.util.List;

public interface UserService {

    public User findUserById(Long userId)throws UserException;


    User findUserProfileByJwt(String jwt)throws UserException;

    List<UserDTO> findAllUsers();
}
