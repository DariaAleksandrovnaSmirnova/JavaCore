package javacore.skynet;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class Faction {
    protected final Factory factory;
    protected final Map<Part, Integer> inventory = new EnumMap<>(Part.class);

    @Getter
    protected int robotCount = 0;

    public Faction(Factory factory) {
        this.factory = factory;
        initializeInventory();
    }

    private void initializeInventory() {
        for (Part part : Part.values()) {
            inventory.put(part, 0);
        }
    }

    public abstract void collectParts();

    public void assembleRobot() {
        while (canAssembleRobot()) {
            inventory.computeIfPresent(Part.HEAD, (k, v) -> v - 1);
            inventory.computeIfPresent(Part.TORSO, (k, v) -> v - 1);
            inventory.computeIfPresent(Part.HAND, (k, v) -> v - 2);
            inventory.computeIfPresent(Part.FEET, (k, v) -> v - 2);
            robotCount++;
        }
    }

    public boolean canAssembleRobot() {
        return inventory.getOrDefault(Part.HEAD, 0) >= 1
                && inventory.getOrDefault(Part.TORSO, 0) >= 1
                && inventory.getOrDefault(Part.HAND, 0) >= 2
                && inventory.getOrDefault(Part.FEET, 0) >= 2;
    }

    protected synchronized void processCollectedParts(List<Part> parts) {
        parts.forEach(part -> inventory.merge(part, 1, Integer::sum));
        while (canAssembleRobot()) {
            assembleRobot();
        }
    }
}
