package com.spidey.openmusicapi.service;

import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.entity.AuthorityDO;

import java.util.Collection;
import java.util.List;

public interface IAuthorityService extends MPJDeepService<AuthorityDO> {

    /**
     * 获取角色的权限信息
     * @param roleId 角色id
     * @return 角色的权限信息
     */
    List<AuthorityDO> getAuthoritiesByRoleId(Long roleId);

    /**
     * 获取角色的菜单id数组
     * @param roleId 角色id
     * @return 获取角色的菜单id数组
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    boolean saveMenuIdsByRoleId(Long roleId, Collection<Long> menuIds);
}
