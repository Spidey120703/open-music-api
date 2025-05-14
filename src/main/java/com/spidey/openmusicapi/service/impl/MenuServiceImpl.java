package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.entity.MenuDO;
import com.spidey.openmusicapi.entity.PermissionDO;
import com.spidey.openmusicapi.entity.RouteDO;
import com.spidey.openmusicapi.enums.MenuType;
import com.spidey.openmusicapi.mapper.MenuMapper;
import com.spidey.openmusicapi.service.IMenuService;
import com.spidey.openmusicapi.service.IPermissionService;
import com.spidey.openmusicapi.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

import static com.spidey.openmusicapi.utils.IdentifierUtils.kebab2Camel;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends MPJBaseServiceImpl<MenuMapper, MenuDO> implements IMenuService {

    private final IPermissionService permissionService;

    @Override
    public List<RouteDO> getRoutes() {
        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();

        return permissionService
                .getSubMenusByRoleId(userDetails.getUser().getRoleId())
                .stream()
                .map(menu -> {
                    RouteDO route = new RouteDO();
                    route.setName(menu.getName());

                    String path = menu.getRoute();
                    route.setPath("%s".formatted(path));

                    int lastSlashIndex = path.lastIndexOf('/');
                    route.setComponent(
                            "%s/%sManage.vue".formatted(
                                    path.substring(0, lastSlashIndex),
                                    kebab2Camel(path.substring(lastSlashIndex + 1))
                            ));

                    route.setMeta(new RouteDO.RouteMeta(
                            menu.getTitle(),
                            menu.getIcon(),
                            menu.getHidden()));

                    return route;
                })
                .toList();
    }

    private boolean hasChildren(Long menuId) {
        LambdaQueryWrapper<MenuDO> wrapper = new LambdaQueryWrapper<MenuDO>()
                .eq(MenuDO::getParentId, menuId);
        return count(wrapper) > 0;
    }

    private List<MenuDO> getMenus(Long parentId, Long roleId) {
        MPJLambdaWrapper<MenuDO> wrapper = new MPJLambdaWrapper<MenuDO>()
                .selectAll(MenuDO.class)
                .leftJoin(PermissionDO.class, PermissionDO::getMenuId, MenuDO::getId)
                .eq(PermissionDO::getRoleId, roleId)
                .eq(MenuDO::getParentId, parentId)
                .eq(MenuDO::getType, MenuType.MENU);
        List<MenuDO> menus = list(wrapper);

        for (MenuDO menu : menus) {
            menu.setChildren(getMenus(menu.getId(), roleId));
        }

        return menus;
    }

    @Override
    public List<MenuDO> getMenus() {
        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();
        return getMenus(0L, userDetails.getUser().getRoleId());
    }


    @Override
    public MenuDO getById(Long id) {
        MenuDO menu = getByIdDeep(
                id,
                conf -> conf.property(MenuDO::getChildren).loop(true)
        );
        if (menu == null) return null;

        // 层次遍历，建立一级缓存
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
