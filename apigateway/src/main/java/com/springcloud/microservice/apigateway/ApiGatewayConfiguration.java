package com.springcloud.microservice.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator getwayRouter(RouteLocatorBuilder builder) {

		Function<PredicateSpec, Buildable<Route>> path =
				url -> url.path("/get").
				filters(f->f.addRequestHeader("myuri", "uri").addRequestParameter("param", "paramval"))
				.uri("http://httpbin.org:80");
				
				
		return builder.routes()
				.route(path /** this is example */)
				.route(
					url->url.path("/currency-exchange/**").uri("lb://currency-exchange"))
				.route(
					url->url.path("/currency-conversion/**").uri("lb://currency-conversion"))
				.route(
					url->url.path("/currency-conversion-feign/**").uri("lb://currency-conversion"))
				.route(
					url->url.path("/currency-conversion-any/**")
							.filters(f->f.rewritePath("currency-conversion-any/(?<segment>.*)", "currency-conversion/${segment}"))
							.uri("lb://currency-conversion"))
				.build();

	}

}
