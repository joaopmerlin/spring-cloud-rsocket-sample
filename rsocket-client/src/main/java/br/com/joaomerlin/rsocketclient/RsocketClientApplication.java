package br.com.joaomerlin.rsocketclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.rsocket.client.BrokerClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;

@SpringBootApplication
public class RsocketClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketClientApplication.class, args);
    }

    @Bean
    public MeterRegistry meterRegistry() {
        return new LoggingMeterRegistry();
    }

    @Component
    public class Runner implements ApplicationListener<PayloadApplicationEvent<RSocketRequester>> {

        @Autowired
        private BrokerClient client;

        @Override
        public void onApplicationEvent(PayloadApplicationEvent<RSocketRequester> event) {
            event.getPayload().route("test")
                    .metadata(client.forwarding("server"))
                    .data("hello word")
                    .retrieveMono(String.class)
                    .doOnNext(System.out::println)
                    .subscribe();
        }
    }

}
