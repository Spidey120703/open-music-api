package com.spidey.openmusicapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.AuditDO;

import java.util.function.Function;

public interface IAuditService extends IService<AuditDO> {

    IPage<AuditDO> getAuditsByPage(SFModel model, String... columns);

    IPage<AuditDO> getAuditsByPage(
            Function<MPJLambdaWrapper<AuditDO>, MPJLambdaWrapper<AuditDO>> config, SFModel model, String... columns);

    AuditDO getAuditById(Long id);
}