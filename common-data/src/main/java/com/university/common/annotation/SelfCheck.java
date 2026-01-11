package com.university.common.annotation;

import org.springframework.stereotype.Component;

@Component("selfCheck")
public class SelfCheck {

    public boolean matches(Integer id) {
        return SecurityUtils.currentUserId().equals(id);
    }
}
