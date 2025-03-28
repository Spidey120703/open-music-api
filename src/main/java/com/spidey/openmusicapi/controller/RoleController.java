package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.RoleDO;
import com.spidey.openmusicapi.service.IRoleService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/role")
@RestController
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("{roleId}")
    public ApiResponse<RoleDO> getRoleById(@PathVariable Long roleId) {
        return getSuccess(checkNull(roleService.getByIdDeep(roleId), "角色不存在"));
    }


    @GetMapping
    public ApiResponse<SFPage<RoleDO>> getRolesByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPageUtils.pagingDeep(
                        roleService,
                        model,
                        List.of(
                                RoleDO.Fields.name,
                                RoleDO.Fields.description
                        )));
    }

    @PostMapping
    public ApiResponse<Boolean> addRole(@RequestBody @Validated RoleDO role) {
        return verifyCreateResult(roleService.save(role));
    }

    @PutMapping("{roleId}")
    public ApiResponse<Boolean> updateRoleById(@PathVariable Long roleId, @RequestBody @Validated RoleDO role) {
        role.setId(roleId);
        return verifyUpdateResult(roleService.updateById(role));
    }

    @DeleteMapping("{roleId}")
    public ApiResponse<Boolean> deleteRole(@PathVariable Long roleId) {
        return verifyDeleteResult(roleService.removeById(roleId));
    }

}