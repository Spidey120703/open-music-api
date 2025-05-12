package com.spidey.openmusicapi.service;

import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.entity.MenuDO;
import com.spidey.openmusicapi.entity.RouteDO;

import java.util.List;

public interface IMenuService extends MPJDeepService<MenuDO> {

    List<RouteDO> getRoutes();

    List<MenuDO> getMenus();

    MenuDO getById(Long menuId);

    MenuDO getByIdLazy(Long menuId);

    List<MenuDO> getChildrenByParentId(Long parentId);

    List<MenuDO> getChildrenByParentIdDeep(Long menuId);

}
