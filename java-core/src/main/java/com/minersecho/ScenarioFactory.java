package com.minersecho;

public final class ScenarioFactory {
    public record Scenario(String title, World world, Player player) {}

    public static Scenario hitScenario() {
        World world = new World(BlockType.STONE);
        addCeiling(world);
        world.setBlock(new Vec3(0,0,-2), BlockType.AIR);
        world.setBlock(new Vec3(0,1,-2), BlockType.AIR);
        world.setBlock(new Vec3(1,0,-2), BlockType.AIR);
        return new Scenario("Hit scenario", world, new Player("player-1", 0,0,0,0));
    }

    public static Scenario diagonalHitScenario() {
        World world = new World(BlockType.STONE);
        addCeiling(world);
        world.setBlock(new Vec3(2,0,-2), BlockType.AIR);
        world.setBlock(new Vec3(2,1,-2), BlockType.AIR);
        world.setBlock(new Vec3(3,0,-2), BlockType.AIR);
        return new Scenario("Diagonal hit scenario", world, new Player("player-1", 0,0,0,45));
    }

    public static Scenario invalidActivationScenario() {
        World world = new World(BlockType.AIR);
        return new Scenario("Invalid activation scenario", world, new Player("player-1", 0,0,0,0));
    }

    private static void addCeiling(World world) {
        world.setBlock(new Vec3(0,1,0), BlockType.STONE);
        world.setBlock(new Vec3(0,2,0), BlockType.STONE);
        world.setBlock(new Vec3(0,3,0), BlockType.STONE);
    }
}
