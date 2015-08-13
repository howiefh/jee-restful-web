package io.github.howiefh.jeews.modules.sys.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class HypermediaConfiguration {
    private static final String HAL_OBJECT_MAPPER_BEAN_NAME = "_halObjectMapper";

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public CurieProvider curieProvider() {
        return new DefaultCurieProvider("api", new UriTemplate("http://localhost/rels/{rel}"));
    }

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = (ObjectMapper) beanFactory.getBean(HAL_OBJECT_MAPPER_BEAN_NAME);
        // 设置生成的json数据的日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 设置生成的json数据不包含空值
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        return objectMapper;
    }
}