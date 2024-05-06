package com.project.ShopOnlineBE.services;

import com.project.ShopOnlineBE.dtos.UpdateUserDTO;
import com.project.ShopOnlineBE.dtos.UserDTO;
import com.project.ShopOnlineBE.exceptions.DataNotFoundException;
import com.project.ShopOnlineBE.exceptions.InvalidPasswordException;
import com.project.ShopOnlineBE.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password) throws Exception;

    @Transactional
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    User getUserDetailsFromRefreshToken(String refreshToken) throws Exception;

    Page<User> findAll(String keyword, Pageable pageable);

    @Transactional
    void resetPassword(Long userId, String newPassword)
            throws InvalidPasswordException, DataNotFoundException;

    @Transactional
    void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;

    User getUserById(Long userId);
}
