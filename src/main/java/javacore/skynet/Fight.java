package javacore.skynet;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Fight {
    private static final int SIMULATION_DAYS = 10;
    private static final int DAY_NIGHT_CYCLE_MS = 1000;
    private static final int MAX_DAILY_PRODUCTION = 10;
    private static final int COUNT_THREADS = 2;

    private final World world;
    private final Wednesday wednesday;
    private final Factory factory;
    private final ScheduledExecutorService executor;
    private final AtomicInteger dayCounter = new AtomicInteger(0);

    public Fight() {
        this.factory = new Factory(MAX_DAILY_PRODUCTION);
        this.world = new World(factory);
        this.wednesday = new Wednesday(factory);
        this.executor = Executors.newScheduledThreadPool(COUNT_THREADS);
    }

    public void runSimulation() {
        executor.scheduleAtFixedRate(() -> {
            log.info("=== DAY {} START ===", dayCounter.incrementAndGet());
            factory.produceDailyParts();
        }, 0, DAY_NIGHT_CYCLE_MS, TimeUnit.MILLISECONDS);

        executor.scheduleAtFixedRate(() -> {
            log.info("=== NIGHT START ===");
            CompletableFuture.runAsync(world::collectParts, executor);
            CompletableFuture.runAsync(wednesday::collectParts, executor);
        }, DAY_NIGHT_CYCLE_MS / 2, DAY_NIGHT_CYCLE_MS, TimeUnit.MILLISECONDS);

        executor.schedule(this::endSimulation, SIMULATION_DAYS, TimeUnit.SECONDS);
    }

    private void endSimulation() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        printResults();
    }

    private void printResults() {
        log.info("\n=== FINAL RESULTS ===");
        log.info("World: {} robots | Wednesday: {} robots",
                world.getRobotCount(), wednesday.getRobotCount());
        if (world.getRobotCount() == wednesday.getRobotCount()) {
            log.info("It's a draw, dude!");
        } else {
            log.info(world.getRobotCount() > wednesday.getRobotCount() ? "World win" : "Wednesday win");
        }
    }

    public static void main(String[] args) {
        new Fight().runSimulation();
    }
}
