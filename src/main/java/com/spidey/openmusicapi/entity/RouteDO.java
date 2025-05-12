package com.spidey.openmusicapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RouteMeta {
        String title;
        String icon;
        Boolean hidden;
    }

    String path;
    String name;
    String component;
    RouteMeta meta;
}
