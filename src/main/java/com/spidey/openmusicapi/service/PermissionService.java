package com.spidey.openmusicapi.service;

import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service("perm")
public class PermissionService {

    public boolean hasPerm(String permission) {
        if (permission == null || permission.isEmpty()) return false;

        LoginUserDetails userDetails = SecurityUtils.getLoginUser();
        if (userDetails == null) return false;

        return userDetails.getPermissions().contains(permission);
    }

}
