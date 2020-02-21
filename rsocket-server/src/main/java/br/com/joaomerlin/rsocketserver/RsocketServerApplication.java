package br.com.joaomerlin.rsocketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import reactor.core.publisher.Mono;

@Controller
@SpringBootApplication
public class RsocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketServerApplication.class, args);
    }

    @Bean
    public MeterRegistry meterRegistry() {
        return new LoggingMeterRegistry();
    }

    @MessageMapping("test")
    public Mono<String> test(String message) {
        System.out.println(message);
        return Mono.just(message);
    }

}
