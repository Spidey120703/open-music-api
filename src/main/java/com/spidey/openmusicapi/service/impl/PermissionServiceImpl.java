package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.entity.MenuDO;
import com.spidey.openmusicapi.entity.PermissionDO;
import com.spidey.openmusicapi.enums.MenuType;
import com.spidey.openmusicapi.mapper.MenuMapper;
import com.spidey.openmusicapi.mapper.PermissionMapper;
import com.spidey.openmusicapi.service.IPermissionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends MPJBaseServiceImpl<PermissionMapper, PermissionDO> implements IPermissionService {

    private final MenuMapper menuMapper;

    @Override
    public List<MenuDO> getMenusByRoleId(Long roleId) {
        MPJLambdaWrapper<PermissionDO> wrapper = new MPJLambdaWrapper<>();
        wrapper.eq(PermissionDO::getRoleId, roleId)
                .leftJoin(MenuDO.class, MenuDO::getId, PermissionDO::getMenuId)
                .eq(MenuDO::getType, MenuType.MENU);
        return this.listDeep(wrapper)
                .stream()
                .map(PermissionDO::getMenu)
                .toList();
    }

    private boolean hasSubMenu(@NonNull MenuDO menu) {
        LambdaQueryWrapper<MenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MenuDO::getParentId, menu.getId())
                .eq(MenuDO::getType, MenuType.MENU);
        return menuMapper.selectCount(wrapper) == 0;
    }

    @Override
    public List<MenuDO> getSubMenusByRoleId(Long roleId) {
        return this.getMenusByRoleId(roleId)
                .stream()
                .filter(this::hasSubMenu)
                .toList();
    }

    @Override
    public List<String> getPermsByRoleId(Long roleId) {
        MPJLambdaWrapper<PermissionDO> wrapper = new MPJLambdaWrapper<>();
        wrapper.eq(PermissionDO::getRoleId, roleId)
                .leftJoin(MenuDO.class, MenuDO::getId, PermissionDO::getMenuId)
                .eq(MenuDO::getType, MenuType.OPERATION);
        return this.listDeep(wrapper)
                .stream()
                .map(perm -> perm.getMenu().getName())
                .toList();
    }

    @Override
    public List<Long> getPermIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getRoleId, roleId);
        return this.list(wrapper)
                .stream()
                .map(PermissionDO::getMenuId)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savePermIdsByRoleId(
            @NonNull Long roleId,
            @NonNull Collection<Long> menuIds) {
        // 删除原有的权限
        this.remove(new LambdaQueryWrapper<PermissionDO>()
                .eq(PermissionDO::getRoleId, roleId));
        // 保存新的权限
        return this.saveBatch(menuIds
                .stream()
                .map(menuId -> new PermissionDO(roleId, menuId))
                .toList());
    }

}
