package com.minersecho.integration;

public final class ConsoleFeedbackAdapter implements FeedbackAdapter {

    @Override
    public void sendMessage(String playerId, String message) {
        System.out.println("[player=" + playerId + "] " + message);
    }
}