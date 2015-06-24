package io.github.howiefh.jeews.modules.sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

@Configuration
@EnableHypermediaSupport(type= {HypermediaType.HAL})
public class HypermediaConfiguration {

	@Bean
	public CurieProvider curieProvider() {
    	return new DefaultCurieProvider("api", new UriTemplate("http://localhost/rels/{rel}"));
	}
}