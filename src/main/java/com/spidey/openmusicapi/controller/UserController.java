package com.spidey.openmusicapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.PageWrapper;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.enums.OrderType;
import com.spidey.openmusicapi.exception.CustomException;
import com.spidey.openmusicapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.IdentifierUtils.toSnakeCase;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final IUserService userService;

    @GetMapping("{userId}")
    public ApiResponse<UserDO> getUser(@PathVariable Long userId) {
        UserDO user = userService.getByIdDeep(userId);
        if (user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "用户不存在");
        }
        return ApiResponse.success("查询成功", user);
    }

    @GetMapping("list")
    public ApiResponse<PageWrapper<UserDO>> listUser(
            @RequestParam(required = false, defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") OrderType order,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") List<Long> roleId,
            @RequestParam(required = false, defaultValue = "") List<String> status
    ) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserDO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(current, size);
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.orderBy(! sort.isEmpty(), order == OrderType.ASC, sort);
        wrapper.and(! keyword.isEmpty(), qw -> qw
                .like(UserDO.Fields.username, keyword)
                .or()
                .like(UserDO.Fields.nickname, keyword)
                .or()
                .like(UserDO.Fields.email, keyword)
                .or()
                .like(UserDO.Fields.phone, keyword)
        );
        wrapper.in(! roleId.isEmpty(), toSnakeCase(UserDO.Fields.roleId), roleId);
        wrapper.in(! status.isEmpty(), UserDO.Fields.status, status);
        return ApiResponse.success("查询成功",
                PageWrapper.wrapPage(
                        userService.pageDeep(page, wrapper),
                        keyword,
                        sort, order,
                        Map.entry(UserDO.Fields.roleId, roleId),
                        Map.entry(UserDO.Fields.status, status)
                ));
    }

}
