package com.spidey.openmusicapi.common;

import com.spidey.openmusicapi.enums.OrderType;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PageWrapper<T> {

    private List<T> records = Collections.emptyList();
    private Long size = 10L;
    private Long total = 0L;
    private Long current = 1L;
    private Long pages = 0L;
    private String keyword = null;
    private Map.Entry<String, OrderType> orderBy;
    private Map<String, List<?>> filters;

    @SafeVarargs
    public static <T> PageWrapper<T> wrapPage(
            com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page,
            String keyword,
            String sort, OrderType order,
            Map.Entry<String, List<?>>... filters) {
        PageWrapper<T> data = new PageWrapper<>();
        data.setRecords(page.getRecords());
        data.setSize(page.getSize());
        data.setTotal(page.getTotal());
        data.setCurrent(page.getCurrent());
        data.setPages(page.getPages());
        data.setKeyword(keyword.isEmpty() ? null : keyword);
        data.setOrderBy(sort.isEmpty() ? null : Map.entry(sort, order));
        Map<String, List<?>> tempFilters = new HashMap<>();
        for (Map.Entry<String, List<?>> filter : filters) {
            if (filter.getValue().isEmpty()) continue;
            tempFilters.put(filter.getKey(), filter.getValue());
        }
        data.setFilters(tempFilters.isEmpty() ? null : tempFilters);
        return data;
    }
}
