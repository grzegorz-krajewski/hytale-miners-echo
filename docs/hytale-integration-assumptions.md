# Hytale Integration Assumptions

## Purpose

This document defines the current assumptions for integrating the Miner’s Echo prototype into a future Hytale-compatible runtime or mod/plugin environment.

At this stage, these assumptions are intentionally high-level.

The goal is to identify which parts of the current prototype are engine-agnostic and which parts will later depend on Hytale-specific APIs, systems, or runtime constraints.

## Current Project State

The current Miner’s Echo prototype is implemented as a standalone Python sandbox.

It validates:

- yaw-based directional scanning
- predefined scan patterns
- meaningful air cluster detection
- underground activation rules
- hit / miss / invalid activation flow

This logic is not tied to Hytale yet.

## Core Assumption

Miner’s Echo should be developed in two layers:

### 1. Core mechanic logic
This includes:

- direction resolution
- scan pattern selection
- scan traversal order
- cavity detection logic
- underground activation rules
- result classification

This layer is conceptually portable.

### 2. Runtime integration layer
This includes:

- player input handling
- player position lookup
- player yaw lookup
- world block access
- visual feedback
- audio feedback
- cooldown persistence
- item / skill / equipment integration

This layer will depend on the actual Hytale integration path and available APIs.

## Expected Runtime Dependencies

A future Hytale integration will need access to the following concepts.

### Player state
Miner’s Echo needs:

- player position
- player facing direction or yaw
- player activation input
- player-specific cooldown state

### World state
Miner’s Echo needs:

- block lookup at world coordinates
- block classification such as air vs solid
- access to neighboring block positions
- overhead block lookup for underground validation

### Feedback systems
Miner’s Echo needs:

- temporary visual effect spawning or highlighting
- temporary audio playback
- optional minimal UI or status feedback

### Trigger system
Miner’s Echo needs one of the following activation paths:

- item use
- skill activation
- custom input binding
- equipment-triggered ability

The final choice should follow whatever feels most natural in Hytale’s runtime model.

## Prototype Elements That Should Survive Integration

The following ideas should remain unchanged even if the implementation changes:

- 8-direction horizontal scan
- yaw-based direction selection
- pitch ignored in MVP
- directional scan volume
- first meaningful cavity detection
- cavity threshold logic
- underground-only activation
- cooldown-based use
- hit / miss / invalid activation result model

## Prototype Elements Likely To Change During Integration

The following details are likely to change when moving into a real game runtime:

- exact yaw convention
- exact scan origin relative to the player model
- exact block categories and block metadata
- underground detection implementation details
- visual feedback implementation
- audio feedback implementation
- cooldown persistence model
- activation source (item, skill, tool, or other)

## Yaw Convention Assumption

The prototype currently assumes:

- `0` = North
- `45` = North-East
- `90` = East
- etc.

This is only a prototype convention.

When integration begins, the real Hytale coordinate and yaw system must be checked and the direction mapping layer must be adapted if necessary.

## Block Classification Assumption

The prototype currently uses a simplified world model:

- `air`
- `stone`

A future integration will need a richer interpretation of game blocks.

At minimum, the runtime integration layer should be able to decide whether a block behaves as:

- empty / passable
- solid / obstructing
- relevant to underground cover detection

## Underground Validation Assumption

The current prototype defines underground as:

- not exposed to the sky
- solid cover overhead

This rule is intentionally simple.

When integrated into the game, it may need refinement depending on:

- cave entrances
- shallow overhangs
- biome or terrain edge cases
- how sky exposure is represented in the actual runtime

## Feedback Assumption

The prototype currently does not implement final feedback systems.

A future Hytale integration is expected to support:

- a short pulse effect
- a successful cavity feedback effect
- a miss feedback effect
- an invalid activation feedback effect

The exact implementation should remain flexible.

## Cooldown Assumption

The prototype treats cooldown as a gameplay rule, not a rendering concern.

The final runtime should provide a player-bound cooldown mechanism that can be queried and updated on activation.

## AI-Assisted Direction

Miner’s Echo may later include an AI-assisted resonance interpretation layer.

This should be treated as an optional extension on top of the base mechanic, not as a dependency for the first in-game integration.

The first Hytale-compatible version should focus on deterministic mechanic parity with the prototype.

## Integration Priorities

When the project moves toward Hytale integration, priorities should be:

1. confirm actual runtime / modding path
2. confirm player and world data access model
3. port core scan logic
4. reproduce hit / miss / invalid activation behavior
5. add visual and audio feedback
6. add item / skill / activation binding
7. validate gameplay feel in runtime
8. iterate only after base parity is achieved

## Recommended Integration Philosophy

Do not redesign the mechanic during integration.

First reproduce the existing prototype behavior as faithfully as possible.

Only after parity is achieved should the project move into:

- visual polish
- better feedback
- AI-assisted interpretation
- progression systems
- extended feature set

## Summary

The current prototype should be treated as a reference model for Miner’s Echo behavior.

Future Hytale integration should preserve the mechanic’s logic and player-facing identity while adapting the runtime-dependent parts to the real game environment.