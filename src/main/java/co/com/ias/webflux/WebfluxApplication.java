package co.com.ias.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);

     /*   Mono<Integer> mono = Mono
                .just(1)
                .map(x -> x / 0)
                .onErrorReturn(6);

        mono.subscribe(System.out::println);*/

   /*     Flux
                .just(1d,2d,0d,4d,0.0)
                .map(x -> 1d / x)
                .onErrorContinue(ArithmeticException.class::isInstance,
                        (error, object) -> System.out.println("Error arithmetic division" + error.getMessage()))
                .subscribe(result -> System.out.println("result: " + result));*/
    }

}
