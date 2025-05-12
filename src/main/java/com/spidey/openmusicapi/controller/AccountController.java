package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.entity.ChangePasswordDTO;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.service.IAccountService;
import com.spidey.openmusicapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.ControllerUtils.getSuccess;
import static com.spidey.openmusicapi.utils.ControllerUtils.verifyUpdateResult;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {


    private final IUserService userService;
    private final IAccountService accountService;

    @GetMapping("current")
    public ApiResponse<UserDO> getCurrentUser() {
        return getSuccess(accountService.getCurrentUser());
    }

    @PatchMapping("current")
    public ApiResponse<Boolean> updateCurrentUser(@RequestBody @Validated UserDO user) {
        user.setRoleId(user.getRole().getId());
        return verifyUpdateResult(userService.updateById(user));
    }

    @PostMapping("password")
    public ApiResponse<Boolean> changePassword(@RequestBody @Validated ChangePasswordDTO dto) {
        return verifyUpdateResult(accountService.changePassword(dto.getOldPassword(), dto.getNewPassword()));
    }

}
