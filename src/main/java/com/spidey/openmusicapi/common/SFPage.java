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

    /**
     * 用于从IPage中继承数据
     *
     * @param page IPage分页结果
     * @param model 参数模型
     * @return SFPage分页结果
     * @param <T> 实体类型
     */
    public static <T> SFPage<T> extend(
            IPage<T> page,
            SFModel model) {
        SFPage<T> sfPage = new SFPage<>();
        sfPage.setRecords(page.getRecords())
                .setSize(page.getSize())
                .setTotal(page.getTotal())
                .setCurrent(page.getCurrent())
                .setPages(page.getPages())
                .setKeyword(model.getKeyword());
        if (! model.getFilters().isEmpty()) {
            sfPage.setFilters(model.getFilters());
        }
        if (! model.getSort().isEmpty()) {
            sfPage.setOrderBy(Pair.of(model.getSort(), model.getOrder()));
        }
        return sfPage;
    }

}
