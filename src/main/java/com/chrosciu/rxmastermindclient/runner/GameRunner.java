package com.chrosciu.rxmastermindclient.runner;

import com.chrosciu.rxmastermindclient.service.KeyboardInputService;
import com.chrosciu.rxmastermindclient.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameRunner implements CommandLineRunner {
    private final SessionService sessionService;
    private final KeyboardInputService keyboardInputService;

    private final String SUCCESS_RESULT = "40";

    @Override
    public void run(String... args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        sessionService.getSessionId()
                .flatMapMany(
                        id -> keyboardInputService.getSamples()
                                .subscribeOn(Schedulers.boundedElastic())
                                .flatMap(sample -> sessionService.getResult(id, sample))
                                .takeUntil(SUCCESS_RESULT::equals)
                                .doOnComplete(() -> System.out.println("You have guessed! Congratulations!"))
                                .concatWith(sessionService.destroySession(id)
                                        .then(Mono.empty())
                                )
                )
                .doFirst(() -> System.out.println("Please enter samples: "))
                .doFinally(signalType -> countDownLatch.countDown())
                .subscribe(r -> System.out.println("Result: " + r)
                        , e -> log.warn("Error:", e)
                );
        countDownLatch.await();
    }

}
