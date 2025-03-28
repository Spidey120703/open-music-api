package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.service.IUserService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final IUserService userService;

    @GetMapping("{userId}")
    public ApiResponse<UserDO> getUserById(@PathVariable Long userId) {
        return getSuccess(checkNull(userService.getByIdDeep(userId), "用户不存在"));
    }

    @GetMapping
    public ApiResponse<SFPage<UserDO>> getUsersByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPageUtils.pagingDeep(
                        userService,
                        model,
                        List.of(
                                UserDO.Fields.username,
                                UserDO.Fields.nickname,
                                UserDO.Fields.email,
                                UserDO.Fields.phone
                        )));
    }


    @PostMapping
    public ApiResponse<Boolean> addUser(@RequestBody @Validated UserDO user) {
        return verifyCreateResult(userService.save(user));
    }

    @PutMapping("{userId}")
    public ApiResponse<Boolean> updateUserById(@PathVariable Long userId, @RequestBody @Validated UserDO user) {
        user.setId(userId);
        return verifyUpdateResult(userService.updateById(user));
    }

    @DeleteMapping("{userId}")
    public ApiResponse<Boolean> deleteUser(@PathVariable Long userId) {
        return verifyDeleteResult(userService.removeById(userId));
    }

}