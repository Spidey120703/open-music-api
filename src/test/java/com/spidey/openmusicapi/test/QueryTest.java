package com.spidey.openmusicapi.test;

import com.spidey.openmusicapi.entity.RoleDO;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.mapper.RoleMapper;
import com.spidey.openmusicapi.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QueryTest {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IUserService userService;

    @Test
    public void testRoleQuery() {
        RoleDO role = roleMapper.selectById(1L);
        System.out.println(role);
    }

    @Test
    public void testUserQuery() {
        UserDO user = userService.getByIdDeep(1L);
        System.out.println(user);
    }
}
