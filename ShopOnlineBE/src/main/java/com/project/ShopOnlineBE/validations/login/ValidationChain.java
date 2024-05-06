package com.project.ShopOnlineBE.validations.login;

import com.project.ShopOnlineBE.dtos.UserDTO;
import com.project.ShopOnlineBE.validations.Validation;
import com.project.ShopOnlineBE.validations.login.PasswordValidation;
import com.project.ShopOnlineBE.validations.login.PhoneNumberValidation;

public class ValidationChain {
    private Validation head;
    private Validation next;

    public ValidationChain() {
        buildChain();
    }

    private void buildChain() {
        Validation phoneNumberValidation = new PhoneNumberValidation();
        Validation passwordValidation = new PasswordValidation();

        phoneNumberValidation.setNext(passwordValidation);
        head = phoneNumberValidation;
    }

    public void validate(UserDTO user) throws Exception {
        if (head != null) {
            head.validate(user);
        }
    }
}
