package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.MenuDO;
import com.spidey.openmusicapi.entity.RoleDO;
import com.spidey.openmusicapi.exception.BadRequestException;
import com.spidey.openmusicapi.service.IPermissionService;
import com.spidey.openmusicapi.service.IRoleService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/role")
@RestController
public class RoleController {

    private final IRoleService roleService;
    private final IPermissionService authorityService;

    @PreAuthorize("@perm.hasPerm('perm:role:get')")
    @GetMapping("{roleId}")
    public ApiResponse<RoleDO> getRoleById(@PathVariable Long roleId) {
        return getSuccess(checkNull(roleService.getByIdDeep(roleId), "角色不存在"));
    }

    @PreAuthorize("@perm.hasPerm('perm:role:list')")
    @GetMapping
    public ApiResponse<SFPage<RoleDO>> getRolesByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPageUtils.pagingDeep(
                        roleService,
                        model,
                        RoleDO.Fields.name,
                        RoleDO.Fields.label));
    }

    @PreAuthorize("@perm.hasPerm('perm:role:add')")
    @PostMapping
    public ApiResponse<Boolean> addRole(@RequestBody @Validated RoleDO role) {
        checkUniqueIdentifier(roleService, role, "角色名已存在", RoleDO::getName, RoleDO::getId);
        return verifyCreateResult(roleService.save(role));
    }

    @PreAuthorize("@perm.hasPerm('perm:role:edit')")
    @PutMapping("{roleId}")
    public ApiResponse<Boolean> updateRoleById(@PathVariable Long roleId, @RequestBody @Validated RoleDO role) {
        role.setId(roleId);
        checkUniqueIdentifier(roleService, role, "角色名已存在", RoleDO::getName, RoleDO::getId);
        return verifyUpdateResult(roleService.updateById(role));
    }

    @PreAuthorize("@perm.hasPerm('perm:role:delete')")
    @DeleteMapping("{roleId}")
    public ApiResponse<Boolean> deleteRole(@PathVariable Long roleId) {
        return verifyDeleteResult(roleService.removeById(roleId));
    }

    @PreAuthorize("@perm.hasPerm('perm:role:grant')")
    @GetMapping("{roleId}/authorities")
    public ApiResponse<List<Long>> getRoleAuthorities(@PathVariable Long roleId) {
        return getSuccess(authorityService.getPermIdsByRoleId(roleId));
    }

    @PreAuthorize("@perm.hasPerm('perm:role:grant')")
    @PutMapping("{roleId}/authorities")
    public ApiResponse<Boolean> updateRoleAuthorities(@PathVariable Long roleId, @RequestBody List<Long> authorityIds) {
        checkNull(roleService.getById(roleId), "角色不存在");
        if (! authorityService.savePermIdsByRoleId(roleId, authorityIds)) {
            throw new BadRequestException("更新角色权限失败");
        }
        return ApiResponse.success("更新角色权限成功", true);
    }

    @PreAuthorize("@perm.hasPerm('perm:role:grant')")
    @GetMapping("{roleId}/authorities/menus")
    public ApiResponse<List<MenuDO>> getRoleAuthoritiesInfo(@PathVariable Long roleId) {
        return getSuccess(authorityService.getMenusByRoleId(roleId));
    }

}