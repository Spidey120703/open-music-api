package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.AuthorityDO;
import com.spidey.openmusicapi.mapper.AuthorityMapper;
import com.spidey.openmusicapi.service.IAuthorityService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class AuthorityService extends MPJBaseServiceImpl<AuthorityMapper, AuthorityDO> implements IAuthorityService {

    @Override
    public List<AuthorityDO> getAuthoritiesByRoleId(Long roleId) {
        LambdaQueryWrapper<AuthorityDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthorityDO::getRoleId, roleId);
        return this.listDeep(wrapper);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<AuthorityDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthorityDO::getRoleId, roleId);
        return this.list(wrapper)
                .stream()
                .map(AuthorityDO::getMenuId)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMenuIdsByRoleId(
            @NonNull Long roleId,
            @NonNull Collection<Long> menuIds) {
        this.remove(new LambdaQueryWrapper<AuthorityDO>()
                .eq(AuthorityDO::getRoleId, roleId));
        return this.saveBatch(menuIds
                .stream()
                .map(menuId -> new AuthorityDO(roleId, menuId))
                .toList());
    }

}
