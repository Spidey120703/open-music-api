package com.spidey.openmusicapi.service;

import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.entity.MenuDO;
import com.spidey.openmusicapi.entity.PermissionDO;

import java.util.Collection;
import java.util.List;

public interface IPermissionService extends MPJDeepService<PermissionDO> {

    /**
     * 根据角色id获取授权的菜单信息
     * @param roleId 角色id
     * @return 授权的菜单信息
     */
    List<MenuDO> getMenusByRoleId(Long roleId);

    /**
     * 根据角色id获取授权的子菜单信息
     * @param roleId 角色id
     * @return 授权的子菜单信息
     */
    List<MenuDO> getSubMenusByRoleId(Long roleId);

    List<String> getPermsByRoleId(Long roleId);

    /**
     * 根据角色id获取授权的菜单id
     * @param roleId 角色id
     * @return 授权的菜单id
     */
    List<Long> getPermIdsByRoleId(Long roleId);

    /**
     * 保存指定角色下授权的菜单id
     * @param roleId 角色id
     * @param menuIds 菜单id
     * @return 保存成功与否
     */
    boolean savePermIdsByRoleId(Long roleId, Collection<Long> menuIds);
}
