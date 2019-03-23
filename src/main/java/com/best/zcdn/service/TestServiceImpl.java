package com.best.zcdn.service;

import com.best.zcdn.bean.Employee;
import com.best.zcdn.mapper.DepartmentMapper;
import com.best.zcdn.mapper.EmployeeMapper;
import com.best.zcdn.service.interfaces.TestServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by EDZ on 2018/11/19.
 */
@Service
public class TestServiceImpl implements TestServiceI {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    ConfQuAnswerServiceImpl confQuAnswerService;


    @Override
    public Employee getDeptByRedis(String id) {
        return getDeptByRedisTemp(id);
    }

//    (value = "testRedis",key = "#id")

    @Cacheable
    public  Employee getDeptByRedisTemp(String id){
        System.out.println("-------------------------没有从redis中取值-------------------------------------------");
        return employeeMapper.getEmpById(id);
    }
}
