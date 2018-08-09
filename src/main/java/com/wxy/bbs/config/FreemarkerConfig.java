package com.wxy.bbs.config;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerConfig {

    private static Configuration configuration;

    private static Configuration bulitConfiguration() {//1建立Freemarker的配置对象,并告知它的目录在哪里
        if(configuration==null){
            configuration=new Configuration(Configuration.VERSION_2_3_25);
            String path=FreemarkerConfig.class.getResource("/").getPath();
            File file=new File(path+ File.separator+"templates");
            configuration.setDefaultEncoding("utf-8");
            try {
                configuration.setDirectoryForTemplateLoading(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configuration;


    }
    //建立模板对象
    public static Template getTemplate(String ftlName){
        try {
            return bulitConfiguration().getTemplate(ftlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**建立Freemarker传值
     *
     * @param resp:响应对象
     * @param target:转向的页
     * @param vmap：传值的map
     */
    public static void forward(
            HttpServletResponse resp,
            String target,
            HashMap vmap){
        Template template = getTemplate(target);
        resp.setContentType("text/html; charset=utf-8" );

        PrintWriter out=null;
        try {
            out = resp.getWriter();
            template.process(vmap, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }
}
