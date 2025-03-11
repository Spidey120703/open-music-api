package com.spidey.openmusicapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.enums.OrderType;
import com.spidey.openmusicapi.exception.CustomException;
import com.spidey.openmusicapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import static com.spidey.openmusicapi.utils.IdentifierUtils.toSnakeCase;

import java.util.List;

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
    public ApiResponse<IPage<UserDO>> listUser(
            @RequestParam(required = false, defaultValue = "1", name = "page") Integer current,
            @RequestParam(required = false, defaultValue = "10", name = "limit") Integer size,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") List<Long> roleId,
            @RequestParam(required = false, defaultValue = "") List<String> status
    ) {
        Page<UserDO> page = new Page<>(current, size);
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.orderBy(! sort.isEmpty(), ! order.equalsIgnoreCase(OrderType.DESC.getType()), sort);
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
        return ApiResponse.success("查询成功", userService.pageDeep(page, wrapper));
    }

}
