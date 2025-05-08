package com.spidey.openmusicapi.entity;

import lombok.Data;

@Data
public class RouteDO {

    @Data
    public static class RouteMeta {
        String title;
        String icon;
    }

    String path;
    String name;
    String component;
    RouteMeta meta;
}
