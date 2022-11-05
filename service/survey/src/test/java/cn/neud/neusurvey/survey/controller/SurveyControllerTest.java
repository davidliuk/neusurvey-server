package cn.neud.neusurvey.survey.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SurveyControllerTest {

    @Resource
    SurveyController surveyController;

    @Test
    void page() {
        Map<String, Object> map = new HashMap<>();
        System.out.println(surveyController.page(map).getData().getList().get(0));
    }

    @Test
    void get() {
        surveyController.get("1588461503517646850");
    }

}
