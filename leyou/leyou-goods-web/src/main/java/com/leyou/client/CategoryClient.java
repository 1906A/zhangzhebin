package com.leyou.client;

import com.leyou.CategoryClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface CategoryClient  extends CategoryClientServer {
}
