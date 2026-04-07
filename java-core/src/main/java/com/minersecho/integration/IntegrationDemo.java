package com.minersecho.integration;

import com.minersecho.core.BlockType;
import com.minersecho.core.CooldownTracker;
import com.minersecho.core.ScanResult;
import com.minersecho.core.Vec3i;
import com.minersecho.core.World;

public final class IntegrationDemo {

    private IntegrationDemo() {
    }

    public static void main(String[] args) {
        World world = createHitWorld();

        MinerEchoService service = new MinerEchoService(
            new CooldownTracker(10.0),
            new ConsoleFeedbackAdapter()
        );

        PlayerContext player = new PlayerContext(
            "player-1",
            0,
            0,
            0,
            0.0f
        );

        ScanResult first = service.activate(player, world, 0.0);
        printResult("First activation", first);

        ScanResult second = service.activate(player, world, 2.0);
        printResult("Second activation at t=2", second);

        ScanResult third = service.activate(player, world, 10.1);
        printResult("Third activation at t=10.1", third);
    }

    private static World createHitWorld() {
        World world = new World(BlockType.STONE);

        // Underground cover above player
        world.setBlock(new Vec3i(0, 1, 0), BlockType.STONE);
        world.setBlock(new Vec3i(0, 2, 0), BlockType.STONE);
        world.setBlock(new Vec3i(0, 3, 0), BlockType.STONE);

        // Meaningful cavity ahead to the north
        world.setBlock(new Vec3i(0, 0, -2), BlockType.AIR);
        world.setBlock(new Vec3i(0, 1, -2), BlockType.AIR);
        world.setBlock(new Vec3i(1, 0, -2), BlockType.AIR);

        return world;
    }

    private static void printResult(String title, ScanResult result) {
        System.out.println("\n=== " + title + " ===");
        System.out.println("success: " + result.success());
        System.out.println("reason: " + result.reason());
        System.out.println("direction: " + result.direction());
        System.out.println("cluster size: " + result.clusterPositions().size());
        System.out.println("checked positions: " + result.checkedPositions().size());
    }
}