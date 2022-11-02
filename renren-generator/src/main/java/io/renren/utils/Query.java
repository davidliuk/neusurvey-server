/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://survey.neud.cn
 *
 * 版权所有，侵权必究！
 */

package io.renren.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author David l729641074@163.com
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page;
    //每页条数
    private int answerLimit;

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.answerLimit = Integer.parseInt(params.get("answerLimit").toString());
        this.put("offset", (page - 1) * answerLimit);
        this.put("page", page);
        this.put("answerLimit", answerLimit);
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getAnswerLimit() {
        return answerLimit;
    }

    public void setAnswerLimit(int answerLimit) {
        this.answerLimit = answerLimit;
    }
}
