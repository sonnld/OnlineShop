package com.project.ShopOnlineBE.validations.login;

import com.project.ShopOnlineBE.dtos.UserDTO;
import com.project.ShopOnlineBE.validations.Validation;

public class PhoneNumberValidation implements Validation {
    private Validation next;
    @Override
    public void setNext(Validation next) {
        this.next = next;
    }

    @Override
    public void validate(UserDTO user) throws Exception {
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new Exception("Phone number is required");
        }
        if (next != null) {
            next.validate(user);
        }
    }
    }

