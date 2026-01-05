package com.university.core.security.permissions;

import com.university.core.security.util.SecurityUtils;
import org.springframework.stereotype.Component;

@Component("selfCheck")
public class SelfCheck {

    public boolean matches(Integer id) {
        return SecurityUtils.currentUserId().equals(id);
    }
}
