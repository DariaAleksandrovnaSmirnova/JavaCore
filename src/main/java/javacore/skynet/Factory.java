package javacore.skynet;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class Factory {
    private final List<Part> partsStorage = new CopyOnWriteArrayList<>();
    private final Random random = new Random();
    private final int maxDailyProduction;

    public Factory(int maxDailyProduction) {
        this.maxDailyProduction = maxDailyProduction;
    }

    public void produceDailyParts() {
        partsStorage.clear();
        int partsToProduce = random.nextInt(maxDailyProduction + 1);
        int countParts = Part.values().length;
        for (int i = 0; i < partsToProduce; i++) {
            Part part = Part.values()[random.nextInt(countParts)];
            partsStorage.add(part);
        }
        log.info("Produced {} parts.", partsToProduce);
    }

    public synchronized List<Part> collectParts(int maxParts) {
        List<Part> collected = new ArrayList<>();
        synchronized (partsStorage) {
            int partsToTake = Math.min(maxParts, partsStorage.size());
            for (int i = 0; i < partsToTake; i++) {
                collected.add(partsStorage.removeFirst());
            }
        }
        log.info("Collected {} parts from factory", collected.size());
        return collected;
    }
}
