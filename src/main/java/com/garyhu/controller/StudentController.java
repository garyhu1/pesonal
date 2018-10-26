package com.garyhu.controller;

import com.garyhu.entity.Student;
import com.garyhu.pojo.Result;
import com.garyhu.repository.StudentRepository;
import com.garyhu.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: garyhu
 * @since: 2018/10/26 0026
 * @decription:
 */
@RestController
public class StudentController {

    @Resource
    StudentRepository studentRepository;

    @GetMapping("/getStudent")
    public Result<Student> getStudent(@RequestParam(value = "id")int id){
        Student st = studentRepository.findOne(id);

        Result success = ResponseUtils.success(st);

        return success;
    }
}
