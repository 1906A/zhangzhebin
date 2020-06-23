package com.leyou.controller;

import com.leyou.common.JwtUtils;
import com.leyou.common.UserInfo;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    //操作购物车数据放到redis hash类型
    //1.加入购物车
    //2.修改购物车
    //3.删除购物车
    //4.查询购物车
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    JwtProperties jwtProperties;
    public String prifix="ly_carts_";
    @RequestMapping("add")
    public void add(@CookieValue("token")String token, @RequestBody Sku sku){
      UserInfo userInfo=this.getUserInfoByToken(token);
      if(userInfo!=null) {
          //添加购物车
          BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate
                  .boundHashOps(prifix + userInfo.getId());
          //判断redis中信息
          if(hashOps.hasKey(sku.getId())){
              //存在
              Sku redisSku =(Sku) hashOps.get(sku.getId().toString());
          }else {
              hashOps.put(sku.getId(), sku);
          }
      }
    }
    @RequestMapping("update")
    public void update(@RequestBody Sku sku){

    }
    @RequestMapping("delete")
    public void delete(@RequestParam("id")Long id){

    }
    @RequestMapping("query")
    public void query(){

    }
    public UserInfo getUserInfoByToken(String token){
        UserInfo userInfo=new UserInfo();

        try {
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
