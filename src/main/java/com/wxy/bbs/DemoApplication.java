package com.wxy.bbs;

import com.wxy.bbs.filter.EncodingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

@SpringBootApplication
@ServletComponentScan
public class DemoApplication {
    @Bean
    public FilterRegistrationBean encodingFilterRegistration(){
        FilterRegistrationBean filter=new FilterRegistrationBean();
        filter.setFilter(new EncodingFilter());//设置哪个类的对象是过滤器注册对象
        filter.addUrlPatterns("/*");//设置哪个地址是其过滤请求处理的地址
        filter.addInitParameter("encoding","utf-8");
        filter.setName("Encoder");
        filter.setOrder(1);
        return filter;
    }
    @Bean(value ="Encoder")
    public Filter EncodingFilter(){
        return new EncodingFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
