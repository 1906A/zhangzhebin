package com.leyou.client;

import com.leyou.SpecParamClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface SpecParamClient extends SpecParamClientServer {
}
