package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.entity.MenuDO;
import com.spidey.openmusicapi.entity.RouteDO;
import com.spidey.openmusicapi.mapper.MenuMapper;
import com.spidey.openmusicapi.service.IMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class MenuService extends MPJBaseServiceImpl<MenuMapper, MenuDO> implements IMenuService {

    @Override
    public List<RouteDO> getRoutes() {
        return List.of();
    }

    private boolean hasChildren(Long menuId) {
        LambdaQueryWrapper<MenuDO> wrapper = new LambdaQueryWrapper<MenuDO>()
                .eq(MenuDO::getParentId, menuId);
        return count(wrapper) > 0;
    }

    @Override
    public MenuDO getById(Long id) {
        MenuDO menu = getByIdDeep(
                id,
                conf -> conf.property(MenuDO::getChildren).loop(true)
        );
        if (menu == null) return null;

        Stack<MenuDO> stack = new Stack<>();
        stack.add(menu);
        while (! stack.empty()) {
            MenuDO top = stack.pop();
            MenuDO tmp = new MenuDO();
            if (top.getChildren() != null) {
                for (MenuDO child : top.getChildren()) {
                    BeanUtils.copyProperties(child, tmp, "parent", "children");
                    child.setParent(tmp);
                    stack.add(child);
                }
            }
        }

        return menu;
    }

    @Override
    public MenuDO getByIdLazy(Long id) {
        MenuDO menu = super.getById(id);
        if (menu == null) return null;

        menu.setHasChildren(hasChildren(id));
        return menu;
    }

    @Override
    public List<MenuDO> getChildrenByParentId(Long parentId) {
        MPJLambdaWrapper<MenuDO> wrapper = new MPJLambdaWrapper<MenuDO>()
                .selectAll(MenuDO.class)
                .selectSub(
                        MenuDO.class,
                        w -> w
                                .selectCount(MenuDO::getId)
                                .eq(MenuDO::getParentId, MenuDO::getId),
                        MenuDO::getHasChildren)
                .eq(MenuDO::getParentId, parentId);
        return listDeep(
                wrapper,
                conf -> conf.property(MenuDO::getHasChildren).deep(1)
        );
    }

    @Override
    public List<MenuDO> getChildrenByParentIdDeep(Long parentId) {
        MPJLambdaWrapper<MenuDO> wrapper = new MPJLambdaWrapper<MenuDO>()
                .selectAll(MenuDO.class)
                .eq(MenuDO::getParentId, parentId);
        return listDeep(
                wrapper,
                conf -> conf.property(MenuDO::getChildren).loop(true)
        );
    }

}
