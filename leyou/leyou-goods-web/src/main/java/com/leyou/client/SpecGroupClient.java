package com.leyou.client;

import com.leyou.SpecGroupClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface SpecGroupClient extends SpecGroupClientServer {
}
