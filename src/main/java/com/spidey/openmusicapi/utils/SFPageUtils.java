package com.spidey.openmusicapi.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static com.spidey.openmusicapi.utils.IdentifierUtils.toSnakeCase;

@UtilityClass
public class SFPageUtils {

    public <T> Pair<IPage<T>, QueryWrapper<T>> pagingPrepare(IService<T> service, SFModel model, List<String> columns) {
        Page<T> page = new Page<>(model.getCurrent(), model.getSize());
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.and(
                model.isSearching() && ! columns.isEmpty(),
                qw -> {
                    for (int i = 0; i < columns.size(); i ++) {
                        qw.like(toSnakeCase(columns.get(i)), model.getKeyword());
                        if (i + 1 < columns.size()) {
                            qw.or();
                        }
                    }
                }
        );
        wrapper.and(
                model.isFiltering(),
                qw -> model.getFilters().forEach((s, list) -> {
                    qw.in(toSnakeCase(s), list);
                }));
        wrapper.orderBy(model.isSorting(), model.isAscending(), toSnakeCase(model.getSort()));
        return Pair.of(page, wrapper);
    }

    public <T> SFPage<T> paging(IService<T> service, SFModel model, List<String> columns) {
        Pair<IPage<T>, QueryWrapper<T>> pair = pagingPrepare(service, model, columns);
        return SFPage.merge(service.page(pair.getLeft(), pair.getRight()), model);
    }
    public <T> SFPage<T> paging(MPJDeepService<T> service, SFModel model, String column) {
        return paging(service, model, List.of(column));
    }
    public <T> SFPage<T> paging(MPJDeepService<T> service, SFModel model) {
        return paging(service, model, List.of());
    }

    public <T> SFPage<T> pagingDeep(MPJDeepService<T> service, SFModel model, List<String> columns) {
        Pair<IPage<T>, QueryWrapper<T>> pair = pagingPrepare(service, model, columns);
        return SFPage.merge(service.pageDeep(pair.getLeft(), pair.getRight()), model);
    }
    public <T> SFPage<T> pagingDeep(MPJDeepService<T> service, SFModel model, String column) {
        return pagingDeep(service, model, List.of(column));
    }
    public <T> SFPage<T> pagingDeep(MPJDeepService<T> service, SFModel model) {
        return pagingDeep(service, model, List.of());
    }

}
