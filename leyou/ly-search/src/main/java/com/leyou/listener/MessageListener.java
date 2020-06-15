package com.leyou.listener;

import com.leyou.service.GoodsService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    GoodsService goodsService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "item.edit.search.queue",durable = "true"),
            exchange = @Exchange(name = "item.exchanges",ignoreDeclarationExceptions = "true",
                    type= ExchangeTypes.TOPIC
            ),
            key={"item.insert","item.update"}
    ))
    public void editEsData(Long spuId) throws Exception {
    System.out.println("开始监听修改es数据,supId="+spuId);
    if(spuId==null){
        return;
    }
    goodsService.editEsData(spuId);
    System.out.println("结束修改监听修改es数据,supId="+spuId+"修改成功");

    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "item.delete.search.queue",durable = "true"),
            exchange = @Exchange(name = "item.exchanges",ignoreDeclarationExceptions = "true",
                    type= ExchangeTypes.TOPIC
            ),
            key={"item.delete"}
    ))
    public void deleteEsData(Long spuId) throws Exception {
        System.out.println("开始监听删除es数据,supId="+spuId);
        if(spuId==null){
            return;
        }
        goodsService.deleteEsData(spuId);
        System.out.println("结束修改监听删除es数据,supId="+spuId+"修改成功");

    }
}
