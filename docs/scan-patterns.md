# Scan Patterns

## Purpose

This document defines the directional scan layouts used by Miner’s Echo in its MVP form.

The scan is:

- directional
- horizontal
- based on player yaw only
- built from predefined offset patterns

Each scan pattern covers:

- 5 blocks forward
- 3 blocks wide
- 2 blocks high

This creates a total scan volume of 30 blocks.

## Coordinate Assumptions

For documentation purposes:

- `x` = left / right
- `y` = vertical height
- `z` = forward / backward

These offsets are relative to the player’s scan origin.

For the MVP:

- `y = 0` represents the lower scan layer
- `y = 1` represents the upper scan layer

The exact in-game origin can later be adjusted to match torso / head level.

## Pattern Rules

- scan direction is determined by player yaw
- pitch is ignored
- the scan checks blocks from nearest row to farthest row
- the first meaningful cavity found ends the scan

## Base Horizontal Patterns

### North (N)

    [
      (-1,0,-1), (0,0,-1), (1,0,-1),
      (-1,0,-2), (0,0,-2), (1,0,-2),
      (-1,0,-3), (0,0,-3), (1,0,-3),
      (-1,0,-4), (0,0,-4), (1,0,-4),
      (-1,0,-5), (0,0,-5), (1,0,-5)
    ]

### South (S)

    [
      (-1,0,1), (0,0,1), (1,0,1),
      (-1,0,2), (0,0,2), (1,0,2),
      (-1,0,3), (0,0,3), (1,0,3),
      (-1,0,4), (0,0,4), (1,0,4),
      (-1,0,5), (0,0,5), (1,0,5)
    ]

### East (E)

    [
      (1,0,-1), (1,0,0), (1,0,1),
      (2,0,-1), (2,0,0), (2,0,1),
      (3,0,-1), (3,0,0), (3,0,1),
      (4,0,-1), (4,0,0), (4,0,1),
      (5,0,-1), (5,0,0), (5,0,1)
    ]

### West (W)

    [
      (-1,0,-1), (-1,0,0), (-1,0,1),
      (-2,0,-1), (-2,0,0), (-2,0,1),
      (-3,0,-1), (-3,0,0), (-3,0,1),
      (-4,0,-1), (-4,0,0), (-4,0,1),
      (-5,0,-1), (-5,0,0), (-5,0,1)
    ]

## Diagonal Patterns

### North-East (NE)

    [
      (0,0,-1), (1,0,-1), (1,0,0),
      (1,0,-2), (2,0,-2), (2,0,-1),
      (2,0,-3), (3,0,-3), (3,0,-2),
      (3,0,-4), (4,0,-4), (4,0,-3),
      (4,0,-5), (5,0,-5), (5,0,-4)
    ]

### North-West (NW)

    [
      (0,0,-1), (-1,0,-1), (-1,0,0),
      (-1,0,-2), (-2,0,-2), (-2,0,-1),
      (-2,0,-3), (-3,0,-3), (-3,0,-2),
      (-3,0,-4), (-4,0,-4), (-4,0,-3),
      (-4,0,-5), (-5,0,-5), (-5,0,-4)
    ]

### South-East (SE)

    [
      (0,0,1), (1,0,1), (1,0,0),
      (1,0,2), (2,0,2), (2,0,1),
      (2,0,3), (3,0,3), (3,0,2),
      (3,0,4), (4,0,4), (4,0,3),
      (4,0,5), (5,0,5), (5,0,4)
    ]

### South-West (SW)

    [
      (0,0,1), (-1,0,1), (-1,0,0),
      (-1,0,2), (-2,0,2), (-2,0,1),
      (-2,0,3), (-3,0,3), (-3,0,2),
      (-3,0,4), (-4,0,4), (-4,0,3),
      (-4,0,5), (-5,0,5), (-5,0,4)
    ]

## Second Height Layer

Each horizontal pattern is duplicated into a second vertical layer.

For every offset:

- `(x,0,z)` also gets `(x,1,z)`

This turns a 15-block pattern into a 30-block scan volume.

## Suggested Data Structure

    SCAN_PATTERNS = {
      "N":  [...],
      "NE": [...],
      "E":  [...],
      "SE": [...],
      "S":  [...],
      "SW": [...],
      "W":  [...],
      "NW": [...]
    }

Then expand each pattern into 2 layers.

## Scan Order

Patterns should be processed from nearest row to farthest row.

This ensures the system finds the closest meaningful cavity first.

## Notes

These patterns are intended for MVP prototyping and may be refined later if testing shows that diagonal scans feel too narrow, too wide, or visually inconsistent.