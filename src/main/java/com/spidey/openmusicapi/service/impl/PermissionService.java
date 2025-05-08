package com.spidey.openmusicapi.service.impl;

import org.springframework.stereotype.Service;

@Service("perm")
public class PermissionService {

    public boolean hasPerm(String permission) {
        if (permission == null || permission.isEmpty()) return false;

        // TODO: 权限校验

        return true;
    }

}
