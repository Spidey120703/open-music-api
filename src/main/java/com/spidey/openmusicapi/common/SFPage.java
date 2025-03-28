package com.spidey.openmusicapi.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spidey.openmusicapi.enums.OrderType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Sortable-Filterable Pagination - 可排序可筛选分页对象
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class SFPage<T> {

    private List<T> records = Collections.emptyList();
    private Long size = 10L;
    private Long total = 0L;
    private Long current = 1L;
    private Long pages = 0L;
    private String keyword = "";
    private Pair<String, OrderType> orderBy;
    private Map<String, List<String>> filters;

    public static <T> SFPage<T> merge(
            IPage<T> page,
            SFModel model) {
        return new SFPage<T>()
                .setRecords(page.getRecords())
                .setSize(page.getSize())
                .setTotal(page.getTotal())
                .setCurrent(page.getCurrent())
                .setPages(page.getPages())
                .setKeyword(model.getKeyword())
                .setFilters(model.getFilters())
                .setOrderBy(Pair.of(model.getSort(), model.getOrder()));
    }

}
