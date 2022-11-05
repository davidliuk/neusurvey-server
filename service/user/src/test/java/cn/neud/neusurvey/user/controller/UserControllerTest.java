package cn.neud.neusurvey.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Resource
    UserController userController;

    @Test
    void page() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", "2");
        map.put("limit", "4");
        System.out.println(userController.page(map).getData().getList());
    }
}