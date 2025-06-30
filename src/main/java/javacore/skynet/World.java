package javacore.skynet;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
public class World extends Faction {
    public World(Factory factory) {
        super(factory);
    }

    @Override
    public void collectParts() {
        try {
            Thread.sleep(new Random().nextInt(50));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<Part> parts = factory.collectParts(5);
        log.info("World collecting {} parts", parts.size());
        processCollectedParts(parts);
    }
}
