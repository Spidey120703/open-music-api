package com.spidey.openmusicapi.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spidey.openmusicapi.enums.OrderType;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sortable-Filterable Model - 可排序可筛选分页的请求参数
 */
@Data
public class SFModel {

    private Long current = 1L;
    private Long size = 10L;
    private String sort = "";
    private OrderType order = OrderType.ASC;
    private String keyword = "";
    private Map<String, List<String>> filters = new HashMap<>();

    @JsonIgnore
    public boolean isSorting() {
        return ! sort.isEmpty();
    }

    @JsonIgnore
    public boolean isAscending() {
        return ! isDescending();
    }

    @JsonIgnore
    public boolean isDescending() {
        return isSorting() && order == OrderType.DESC;
    }

    @JsonIgnore
    public boolean isSearching() {
        return ! keyword.isEmpty();
    }

    @JsonIgnore
    public boolean isFiltering() {
        return ! filters.isEmpty()
               && filters.values().stream().anyMatch(list -> ! list.isEmpty());
    }

}
