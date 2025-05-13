package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.entity.UserDTO;
import com.spidey.openmusicapi.service.IUserService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final IUserService userService;

    @PreAuthorize("@perm.hasPerm('perm:user:get')")
    @GetMapping("{userId}")
    public ApiResponse<UserDO> getUserById(@PathVariable Long userId) {
        return getSuccess(checkNull(userService.getByIdDeep(userId), "用户不存在"));
    }

    @PreAuthorize("@perm.hasPerm('perm:user:list')")
    @GetMapping
    public ApiResponse<SFPage<UserDO>> getUsersByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPageUtils.doPageDeep(
                        userService,
                        model,
                        UserDO.Fields.username,
                        UserDO.Fields.nickname,
                        UserDO.Fields.email,
                        UserDO.Fields.phone));
    }


    @PreAuthorize("@perm.hasPerm('perm:user:add')")
    @PostMapping
    public ApiResponse<Boolean> addUser(@RequestBody @Validated UserDTO user) {
        user.setId(null);
        checkUniqueIdentifier(userService, user, "用户名已存在", UserDO::getUsername, UserDO::getId);
        user.setRoleId(user.getRole().getId());
        return verifyCreateResult(userService.save(user));
    }

    @PreAuthorize("@perm.hasPerm('perm:user:edit')")
    @PutMapping("{userId}")
    public ApiResponse<Boolean> updateUserById(@PathVariable Long userId, @RequestBody @Validated UserDO user) {
        user.setId(userId);
        user.setRoleId(user.getRole().getId());
        checkUniqueIdentifier(userService, user, "用户名已存在", UserDO::getUsername, UserDO::getId);
        return verifyUpdateResult(userService.updateById(user));
    }

    @PreAuthorize("@perm.hasPerm('perm:user:delete')")
    @DeleteMapping("{userId}")
    public ApiResponse<Boolean> deleteUser(@PathVariable Long userId) {
        return verifyDeleteResult(userService.removeById(userId));
    }

}