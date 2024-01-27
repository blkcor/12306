package com.github.blkcor.filter;

import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/*
 * 通过实现GlobalFilter来实现全局过滤器
 * 通过实现Ordered来实现过滤器的先后顺序
 */
@Component
public class LoginFilter implements GlobalFilter, Ordered {
    private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOG.info("进入Token校验过滤器");
        //放行不需要拦截的请求
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/auth") || path.contains("/admin") || path.contains("/hello")) {
            LOG.info("路径{}不需要进行校验", path);
            return chain.filter(exchange);
        }
        HttpHeaders headers = exchange.getRequest().getHeaders();
        //拿到token
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if(token == null || ObjectUtil.isEmpty(token)){
            LOG.info("Token为空，请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //校验token
        if (!JwtUtil.validToken(token)) {
            LOG.error("无效的Token: {}", token);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //有效的token
        LOG.info("Token校验通过，请求路径{}放行",path);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
