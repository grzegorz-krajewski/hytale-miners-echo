# Design Overview

## Project Name

Hytale: Miner’s Echo

## Short Description

Miner’s Echo is a directional underground sensing mechanic for Hytale. It helps players detect nearby hidden cavities while mining, without turning the experience into full x-ray gameplay.

## Vision

The project is meant to make mining more interesting, readable, and satisfying.

Instead of giving players constant wall vision, Miner’s Echo offers a short pulse-based scan that helps them decide where to dig next.

The mechanic should feel like a natural mining tool or server feature, not a cheat.

## Player Fantasy

The player is digging through stone, hits a dead end, activates Miner’s Echo, and gets a brief signal that suggests where a hidden tunnel, opening, or cave chamber might be located.

The intended feeling is:

- "there is something behind this wall"
- "I have a clue, not a spoiler"
- "this helps me explore, but I still have to dig"

## Design Goals

- improve underground exploration
- support smarter mining decisions
- preserve discovery and uncertainty
- avoid full x-ray behavior
- create a memorable server-side feature

## Core Mechanic

Miner’s Echo sends a short directional scan in front of the player.

If the scan detects a meaningful underground cavity, the system briefly reveals it through visual and audio feedback.

If nothing is found, the player still receives a weaker pulse response.

## Design Principles

### Limited information
The mechanic should never reveal too much. It should guide the player, not solve exploration completely.

### Directional use
The player must aim the scan by facing a direction. This makes the mechanic feel intentional and interactive.

### Short feedback window
The result should appear only briefly, giving the player just enough time to react.

### Cooldown-based use
Miner’s Echo should be a tool the player uses at the right moment, not something they spam continuously.

### Underground identity
The mechanic should only function underground, reinforcing its fantasy and balance.

## Intended Identity

Miner’s Echo should be remembered as:

- a mining-focused utility
- a signature server mechanic
- a smarter alternative to x-ray-style mods

The desired reaction is:

> "This server has Miner’s Echo. That’s actually really cool."

## Long-Term Direction

In later iterations, Miner’s Echo may evolve into a more advanced underground sensing system with:

- stronger presentation
- progression tiers
- smarter classification of cavity types
- AI-assisted resonance interpretation
- item-based or equipment-based activation