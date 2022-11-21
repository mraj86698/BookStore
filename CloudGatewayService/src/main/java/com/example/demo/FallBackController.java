package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallBackController {
	@RequestMapping("/userFallBack")
	public Mono<String> userServiceFallBack(){
		return Mono.just("User Service is taking too long to respond or is down. Please try again Later");
	}
	@RequestMapping("/bookFallBack")
	public Mono<String> bookServiceFallBack(){
		return Mono.just("Book Service is taking to long to respond or is down. Pleaase try again later");
	}
	@RequestMapping("/cartFallBack")
	public Mono<String> cartServiceFallBack(){
		return Mono.just("Cart Service is taking to long to respond or is down. Pleaase try again later");
	}
	@RequestMapping("/orderFallBack")
    public Mono<String> orderServiceFallBack() {
        return Mono.just("Order Service is taking too long to respond or is down. Please try again later");
    }
    @RequestMapping("/paymentFallback")
    public Mono<String> paymentServiceFallBack() {
        return Mono.just("Payment Service is taking too long to respond or is down. Please try again later");
    }

}
