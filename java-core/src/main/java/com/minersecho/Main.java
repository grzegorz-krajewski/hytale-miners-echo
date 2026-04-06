package com.minersecho;

public final class Main {
    public static void main(String[] args) {
        runScenario(ScenarioFactory.hitScenario());
        runScenario(ScenarioFactory.diagonalHitScenario());
        runScenario(ScenarioFactory.invalidActivationScenario());
        runCooldownDemo();
    }

    private static void runScenario(ScenarioFactory.Scenario scenario) {
        ScanResult result = Scanner.scanForCavity(scenario.world(), scenario.player(), false);
        printResult(scenario.title(), result, null);
    }

    private static void runCooldownDemo() {
        CooldownTracker cooldown = new CooldownTracker(10.0);
        ScenarioFactory.Scenario scenario = ScenarioFactory.hitScenario();
        Player player = scenario.player();

        ScanResult first = Scanner.scanForCavity(scenario.world(), player, cooldown.isOnCooldown(player.playerId, 0.0));
        printResult("Cooldown demo - first activation", first, null);
        if (first.reason().equals("hit") || first.reason().equals("miss")) {
            cooldown.startCooldown(player.playerId, 0.0);
        }

        double now = 2.0;
        ScanResult second = Scanner.scanForCavity(scenario.world(), player, cooldown.isOnCooldown(player.playerId, now));
        printResult("Cooldown demo - second activation at t=2", second, cooldown.remainingSeconds(player.playerId, now));

        now = 10.1;
        ScanResult third = Scanner.scanForCavity(scenario.world(), player, cooldown.isOnCooldown(player.playerId, now));
        printResult("Cooldown demo - third activation at t=10.1", third, cooldown.remainingSeconds(player.playerId, now));
    }

    private static void printResult(String title, ScanResult result, Double cooldownRemaining) {
        System.out.println("\n=== " + title + " ===");
        System.out.println("success: " + result.success());
        System.out.println("reason: " + result.reason());
        System.out.println("direction: " + result.direction());
        System.out.println("cluster size: " + result.clusterPositions().size());
        System.out.println("checked positions: " + result.checkedPositions().size());
        if (cooldownRemaining != null) {
            System.out.printf("cooldown remaining: %.1fs%n", cooldownRemaining);
        }
        if (!result.clusterPositions().isEmpty()) {
            System.out.println("cluster positions:");
            for (Vec3 pos : result.clusterPositions()) {
                System.out.println("  " + pos);
            }
        }
    }
}
