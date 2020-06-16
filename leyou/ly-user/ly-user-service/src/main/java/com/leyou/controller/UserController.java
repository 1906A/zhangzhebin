package com.leyou.controller;

import com.leyou.pojo.User;
import com.leyou.service.UserService;
import com.leyou.utils.CodeUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //实现用户数据的校验，主要包括对：手机号，用户名的唯一性校验
    @GetMapping("/check/{data}/{type}")
    public Boolean check(@PathVariable("data")String data,@PathVariable("type")Integer type){
       System.out.println("校验"+data+"==="+type);
        return  userService.check(data,type);

    }
    //根据用户手机号生成随意验证码
    @PostMapping("/code")
    public  void  code(@RequestParam("phone")String phone){
        System.out.println("code:"+phone);
        //1.生成一个六位数字的随机码code
        String code= CodeUtils.messageCode(6);

        //2.使用rabbitmq调用短信服务发送验证码phone,code
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);
        amqpTemplate.convertAndSend("sms.changes","sms.send",map);
        //3:发送短信后存放redis 放验证码
        stringRedisTemplate.opsForValue().set("lysms_"+phone,code,5, TimeUnit.MINUTES);
    }
    //用户注册
    @PostMapping("/register")
    public  void  register(User user,String code){
    System.out.println("用户注册："+user.getUsername()+"code="+code);
    }
    @GetMapping("/query")
    public  User query(@RequestParam("username")String username,@RequestParam("password")String password){
        System.out.println("查询用户: username"+username+"password"+password);
        return new User();
    }
}
