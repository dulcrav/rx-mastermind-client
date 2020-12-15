package com.chrosciu.rxmastermindclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class KeyboardInputService {
    private final Scanner scanner;

    private static final String TERMINATOR = "#";

    public Flux<String> getSamples() {
        return Flux.create(sink -> {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (TERMINATOR.equals(line)) {
                    sink.complete();
                    return;
                }
                sink.next(line);
            }
        });
    }
}
