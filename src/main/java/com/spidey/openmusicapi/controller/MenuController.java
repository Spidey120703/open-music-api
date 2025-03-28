package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.entity.MenuDO;
import com.spidey.openmusicapi.exception.CustomException;
import com.spidey.openmusicapi.service.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/menu")
@RestController
public class MenuController {

    private final IMenuService menuService;

    @GetMapping("/{menuId}")
    public ApiResponse<MenuDO> getMenuById(@PathVariable Long menuId) {
        return getSuccess(checkNull(menuService.getById(menuId), "菜单不存在"));
    }

    @GetMapping("{menuId}/lazy")
    public ApiResponse<MenuDO> getMenuByIdLazy(@PathVariable Long menuId) {
        return getSuccess(checkNull(menuService.getByIdLazy(menuId), "菜单不存在"));
    }

    @GetMapping("{menuId}/children")
    public ApiResponse<List<MenuDO>> getMenuChildrenById(@PathVariable Long menuId) {
        return getSuccess(checkNull(menuService.getChildrenByParentIdDeep(menuId), "菜单不存在"));
    }

    @GetMapping("{menuId}/children/lazy")
    public ApiResponse<List<MenuDO>> getMenuChildrenByIdLazy(@PathVariable Long menuId) {
        return getSuccess(checkNull(menuService.getChildrenByParentId(menuId), "菜单不存在"));
    }

    @PostMapping("{menuId}")
    public ApiResponse<Boolean> addMenu(@PathVariable Long menuId, @RequestBody MenuDO menu) {
        menu.setParentId(menuId);
        return verifyCreateResult(menuService.save(menu));
    }

    @PutMapping("{menuId}")
    public ApiResponse<Boolean> updateMenuById(@PathVariable Long menuId, @RequestBody MenuDO menu) {
        menu.setId(menuId);
        return verifyUpdateResult(menuService.updateById(menu));
    }

    @DeleteMapping("{menuId}")
    public ApiResponse<Boolean> deleteMenu(@PathVariable Long menuId) {
        if (menuService.getByIdLazy(menuId).getHasChildren()) {
            throw new CustomException("菜单下有子菜单，不能删除");
        }
        return verifyDeleteResult(menuService.removeById(menuId));
    }

}
