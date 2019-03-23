package com.best.zcdn.controller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.best.zcdn.bean.Department;
import com.best.zcdn.bean.Employee;
import com.best.zcdn.bean.Permission;
import com.best.zcdn.config.ErrorCode;
import com.best.zcdn.mapper.DepartmentMapper;
import com.best.zcdn.mapper.EmployeeMapper;
import com.best.zcdn.service.ConfQuAnswerServiceImpl;
import com.best.zcdn.service.interfaces.TestServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
public class DeptController {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    TestServiceI testService;

    @Autowired
    ConfQuAnswerServiceImpl confQuAnswerService;


    @RequestMapping(value = "/lala", method = RequestMethod.GET)
    public void lala(){
//        confQuAnswerService.test();
    }

    @RequestMapping(value = "/getDepartment", method = RequestMethod.GET)
    public Department getDepartment( Integer id){
        return departmentMapper.getDeptById(id);
    }

    @RequestMapping(value = "/insertDept", method = RequestMethod.GET)
    public Department insertDept(Department department){
       int i = departmentMapper.insertDept(department);
        System.out.println(i);
        return department;
    }




    @RequestMapping(value = "/getEmp", method = RequestMethod.GET)
    public Employee getEmp( String id){
       return testService.getDeptByRedis(id);
    }





    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public List<Permission> getUser( Integer id){
        Long s1= System.currentTimeMillis();
        List<Permission> list = employeeMapper.getUserById(id);
        Long s2= System.currentTimeMillis();
        System.out.println(s2-s1);
        return list;
    }


    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    public Employee insertEmp(){

        Employee employee = new Employee();
        System.out.println(employee.getMmp());
        if(employee.getMmp()==0){
            System.out.println("哦也");
        }
        employee.setId(1);
        employee.setLastName("张三");


        //根据id执行删除操作
        try {
            //这里是你先删后插入的那个插入
        }catch (DuplicateKeyException e){
            //因为你是先删除，后插入的，所以这里一旦主键重复，说明并发了，此时你可以在这里执行update，也可以不做操作
            //加入log日志，说明一下
        }

        employee = new Employee();
        employee.setId(1);
        employee.setLastName("李四");
        try {
            employeeMapper.createTable();
           int i = employeeMapper.insertEmp(employee);
            System.out.println(i);
        }catch (DuplicateKeyException e){
            System.out.println("DuplicateKeyException");
        }


        return employee;
    }

    @RequestMapping(value = "/emps", method = RequestMethod.GET)
    public Employee insertEmps(){
        List<Employee> e = new ArrayList<>();

        Employee employee = new Employee();
        employee.setLastName("杨一");
        e.add(employee);
        employee = new Employee();
        employee.setLastName("杨二");
        e.add(employee);
        employee = new Employee();
        employee.setLastName("杨三");
        e.add(employee);
        employee = new Employee();
        employee.setLastName("杨四");
        e.add(employee);
        employee = new Employee();
        employee.setLastName("杨五");
        e.add(employee);
        employee = new Employee();
        employee.setLastName("杨六");
        e.add(employee);
        employee = new Employee();
        employee.setLastName("杨七");
        e.add(employee);
       int i = employeeMapper.insertEmps(e);
        System.out.println(i);
        return employee;
    }

    @RequestMapping(path = "/confQuestionAnswer",method =RequestMethod.GET)

    public String confQuestionAnswer(){
//        String body ="{'op':'del','qaSet':[{'id':'6'},{'id':'7'}]}";
        String body ="{'op':'del','qaSet':[{'questions':['效益','笑意同学'],'answers':['wozaizheli']},{'id':'6','questions':['收到','更好同学'],answers:['你环境']}]}";
        long start = System.currentTimeMillis();
        JSONObject ret = null;
        List<Integer> list = null;
        try {
            JSONObject object = JSONObject.parseObject(body);
            String op = object.getString("op");
            JSONArray qaSet = object.getJSONArray("qaSet");
            if (qaSet == null){
                ErrorCode errorCode = new ErrorCode(ErrorCode.INVALID_PARAM,"qaSet is null");
                ret =errorCode.toJSONObject();
                return  ret.toJSONString();
            }

            if (StringUtils.isEmpty(op)  ||qaSet.size()==0){
                ErrorCode errorCode = new ErrorCode(ErrorCode.INVALID_PARAM,op);
                ret =errorCode.toJSONObject();
                return  ret.toJSONString();
            }
            list = confQuAnswerService.conf(op,qaSet); ;
            if (list==null){
                ErrorCode errorCode = new ErrorCode(ErrorCode.INVALID_PARAM,body);
                ret =errorCode.toJSONObject();
                return  ret.toJSONString();
            }
            ErrorCode errorCode = new ErrorCode(ErrorCode.SUCCED,JSONArray.toJSONString(list));
            ret =errorCode.toJSONObject();
            return ret.toJSONString();
        }catch (JSONException e){
            ErrorCode errorCode = new ErrorCode(ErrorCode.INVALID_PARAM,body);
            ret =errorCode.toJSONObject();
            return  ret.toJSONString();
        }catch (Exception e){
            ErrorCode errorCode = new ErrorCode(ErrorCode.SYSTEM_ERR);
            ret =errorCode.toJSONObject();
            //加入error日志  自己添加一下
            return  ret.toJSONString();
        }

    }

}
