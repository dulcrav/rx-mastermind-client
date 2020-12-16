package com.chrosciu.rxmastermindclient.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Scanner;

@Service
public class KeyboardInputService {
    private static final String TERMINATOR = "#";

    public Flux<String> getSamples() {
        Scanner scanner = new Scanner(System.in);
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
