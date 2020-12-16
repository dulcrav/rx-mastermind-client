package com.chrosciu.rxmastermindclient.runner;

import com.chrosciu.rxmastermindclient.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameRunner implements CommandLineRunner {
    private final GameService gameService;

    @Override
    public void run(String... args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        gameService.getResults()
                .doFinally(signalType -> countDownLatch.countDown())
                .subscribe(
                        r -> System.out.println("Result: " + r),
                        e -> log.warn("Error:", e)
                );
        countDownLatch.await();
    }

}
