package com.spidey.openmusicapi.service;

import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.entity.MenuDO;

import java.util.List;

public interface IMenuService extends MPJDeepService<MenuDO> {

    MenuDO getById(Long menuId);

    MenuDO getByIdLazy(Long menuId);

    List<MenuDO> getChildrenByParentId(Long parentId);

    List<MenuDO> getChildrenByParentIdDeep(Long menuId);

}
