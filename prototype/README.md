# Prototype

This folder contains the standalone logic prototype for Miner’s Echo.

Its purpose is to validate the core mechanic outside the Hytale runtime before any in-game integration begins.

## Scope

The prototype is focused on logic only.

It is intended to test:

- yaw to 8-direction mapping
- directional scan pattern usage
- meaningful air cluster detection
- underground activation rules
- scan result flow (hit, miss, invalid activation)
- cooldown-aware activation flow

## Non-Goals

This prototype does not include:

- Hytale integration
- rendering or visual effects
- audio feedback
- AI-assisted resonance analysis
- multiplayer logic
- final gameplay balancing

## Why a standalone prototype?

The goal is to verify whether the mechanic works and feels coherent before tying it to the game’s runtime and API.

This helps isolate core logic from integration complexity.

## Planned Modules

- `world.py` — simple block world representation
- `player.py` — player position and yaw data
- `directions.py` — yaw to 8-direction mapping
- `scan_patterns.py` — directional scan pattern definitions
- `scanner.py` — main scan flow
- `cavity.py` — meaningful air cluster detection
- `underground.py` — underground activation rules
- `main.py` — manual scenario runner

## Current Status

Early prototype phase with working directional scan, cavity detection, underground validation, and cooldown handling