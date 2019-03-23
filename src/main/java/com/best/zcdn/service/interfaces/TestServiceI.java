package com.best.zcdn.service.interfaces;

import com.best.zcdn.bean.Employee;

/**
 * Created by EDZ on 2018/11/19.
 */
public interface TestServiceI {

    Employee getDeptByRedis(String id);

}
