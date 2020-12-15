package com.chrosciu.rxmastermindclient.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Scanner;

@Service
public class KeyboardInputService {
    public Flux<String> getSamples() {
        return Flux.create(sink -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.equals("#")) {
                    sink.complete();
                    return;
                }
                sink.next(line);
            }
        });
    }
}
