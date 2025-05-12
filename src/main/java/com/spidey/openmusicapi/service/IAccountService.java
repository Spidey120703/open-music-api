package com.spidey.openmusicapi.service;

import com.spidey.openmusicapi.entity.UserDO;

public interface IAccountService {

    UserDO getCurrentUser();

    boolean changePassword(String oldPassword, String newPassword);

}
