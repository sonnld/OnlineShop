package com.project.ShopOnlineBE.validations;

import com.project.ShopOnlineBE.dtos.UserDTO;

public interface Validation {
    void setNext(Validation next);
    void validate(UserDTO user) throws Exception;
}
