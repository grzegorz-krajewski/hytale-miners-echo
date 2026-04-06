# Hytale: Miner’s Echo

Miner’s Echo is a directional underground sensing mechanic for Hytale, designed to help players detect nearby hidden caves without turning mining into full x-ray gameplay.

## Status

Concept / pre-production

## Overview

Miner’s Echo is a short-range mining utility that sends a directional pulse in front of the player and briefly reveals meaningful underground cavities such as tunnels, openings, and small cave chambers.

The goal is to make mining more rewarding and readable while preserving exploration and discovery.

## Core Idea

Instead of permanent wall vision, Miner’s Echo gives the player a short, controlled scan:

- directional scan only
- limited range
- short visual feedback
- cooldown-based usage
- underground only

This makes it feel more like a mining tool than a cheat.

## MVP Goals

The first playable version should:

- scan in front of the player only
- use 8 horizontal directions
- ignore vertical look pitch
- detect meaningful underground air clusters
- reveal the first valid cavity ahead of the player
- apply a cooldown after use

## Current MVP Spec

- scan size: 5x3x2
- scan type: directional
- directions: 8-way horizontal
- input basis: yaw only
- pitch: ignored
- activation: underground only
- result: first meaningful air cluster
- minimum cavity threshold: 3 connected air blocks
- cooldown: 10 seconds
- feedback: pulse + brief highlight on hit, weaker pulse on miss

## Design Philosophy

Miner’s Echo should feel like:

- a smart mining utility
- a server feature players remember
- an exploration aid, not an x-ray replacement

The ideal player reaction is:

> "This server has Miner’s Echo. Mining actually feels better here."

## Planned Extensions

Future versions may include:

- improved visual and audio feedback
- item-based progression
- AI-assisted resonance analysis
- smarter cavity classification
- optional ore-related upgrades
- server-specific tuning and balance

## AI-Assisted Resonance Direction

Miner’s Echo may later include an AI-assisted resonance analysis layer.

This layer is not intended to replace the core scan mechanic. Instead, it should help interpret scan results and communicate them more clearly to the player.

Possible future uses include:

- cavity type hints
- smarter resonance descriptions
- contextual scan result interpretation
- improved player-facing feedback

Examples of future hint styles:

- `Small cavity detected ahead.`
- `Possible tunnel continuation.`
- `Possible chamber detected ahead-right.`
- `No meaningful resonance found.`

The AI-assisted layer is planned as an extension on top of the deterministic core mechanic, not as part of the first playable prototype.

## Repository Structure

- `docs/` — design and feature documentation
- `prototype/` — gameplay logic prototypes
- `tests/` — test scenarios and validation cases

## Next Steps

- finalize design overview
- lock MVP rules
- document scan direction patterns
- prototype scan logic outside the game
- build test scenarios before game integration