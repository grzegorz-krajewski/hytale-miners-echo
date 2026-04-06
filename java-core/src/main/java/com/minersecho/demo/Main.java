package com.minersecho.demo;

import com.minersecho.core.*;

public final class Main {
    public static void main(String[] args) {
        World world = new World(BlockType.STONE);

        // Underground cover above player
        world.setBlock(new Vec3i(0, 1, 0), BlockType.STONE);
        world.setBlock(new Vec3i(0, 2, 0), BlockType.STONE);
        world.setBlock(new Vec3i(0, 3, 0), BlockType.STONE);

        // Valid cavity north of player
        world.setBlock(new Vec3i(0, 0, -2), BlockType.AIR);
        world.setBlock(new Vec3i(0, 1, -2), BlockType.AIR);
        world.setBlock(new Vec3i(1, 0, -2), BlockType.AIR);

        Player player = new Player("player-1", 0, 0, 0, 0);
        CooldownTracker cooldownTracker = new CooldownTracker(10.0);

        double now = 0.0;
        ScanResult first = Scanner.scanForCavity(world, player, cooldownTracker.isOnCooldown(player.playerId(), now));
        print("First activation", first, 0.0);
        if ("hit".equals(first.reason()) || "miss".equals(first.reason())) {
            cooldownTracker.startCooldown(player.playerId(), now);
        }

        now = 2.0;
        ScanResult second = Scanner.scanForCavity(world, player, cooldownTracker.isOnCooldown(player.playerId(), now));
        print("Second activation at t=2", second, cooldownTracker.remainingSeconds(player.playerId(), now));

        now = 10.1;
        ScanResult third = Scanner.scanForCavity(world, player, cooldownTracker.isOnCooldown(player.playerId(), now));
        print("Third activation at t=10.1", third, cooldownTracker.remainingSeconds(player.playerId(), now));
    }

    private static void print(String title, ScanResult result, double cooldownRemaining) {
        System.out.println("\n=== " + title + " ===");
        System.out.println("success: " + result.success());
        System.out.println("reason: " + result.reason());
        System.out.println("direction: " + result.direction());
        System.out.println("cluster size: " + result.clusterPositions().size());
        System.out.println("checked positions: " + result.checkedPositions().size());
        System.out.printf("cooldown remaining: %.1fs%n", cooldownRemaining);
    }
}
