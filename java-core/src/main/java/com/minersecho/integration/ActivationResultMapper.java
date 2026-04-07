package com.minersecho.integration;

public final class ActivationResultMapper {

    private ActivationResultMapper() {
    }

    public static String toMessage(String reason, String direction) {
        if (reason == null) {
            return "Unknown resonance result.";
        }

        return switch (reason) {
            case "hit" -> hitMessage(direction);
            case "miss" -> "No meaningful resonance found.";
            case "cooldown" -> "Miner's Echo is recharging.";
            case "invalid_activation" -> "No underground resonance detected.";
            default -> "Unknown resonance result.";
        };
    }

    private static String hitMessage(String direction) {
        if (direction == null || direction.isBlank()) {
            return "Cavity detected nearby.";
        }

        return switch (direction) {
            case "N" -> "Cavity detected ahead.";
            case "NE" -> "Possible chamber ahead-right.";
            case "E" -> "Cavity detected to the right.";
            case "SE" -> "Possible opening behind-right.";
            case "S" -> "Resonance detected behind.";
            case "SW" -> "Possible opening behind-left.";
            case "W" -> "Cavity detected to the left.";
            case "NW" -> "Possible chamber ahead-left.";
            default -> "Cavity detected nearby.";
        };
    }
}