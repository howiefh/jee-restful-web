package io.github.howiefh.jeews.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

@Configuration
@EnableHypermediaSupport(type= {HypermediaType.HAL})
public class TestHypermediaConfiguration {

	@Bean
	public CurieProvider curieProvider() {
    	return new DefaultCurieProvider("tests", new UriTemplate("http://localhost/tests/rels/{rel}"));
	}
}