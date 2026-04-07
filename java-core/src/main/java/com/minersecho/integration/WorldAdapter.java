package com.minersecho.integration;

public interface WorldAdapter {

    boolean isAir(int x, int y, int z);

    boolean isSolid(int x, int y, int z);
}