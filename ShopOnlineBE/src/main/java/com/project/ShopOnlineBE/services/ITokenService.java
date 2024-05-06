package com.project.ShopOnlineBE.services;

import com.project.ShopOnlineBE.models.Token;
import com.project.ShopOnlineBE.models.User;
import org.springframework.stereotype.Service;

@Service

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
}