# MVP Specification

## Purpose

The MVP of Miner’s Echo is focused on proving the core mechanic:

A player can activate a short directional underground scan that detects the first meaningful hidden cavity ahead.

This version is meant to validate gameplay feel, balance, and technical feasibility before any deeper integration, progression, or AI-assisted interpretation is added.

## Activation Rules

Miner’s Echo can only be activated when:

- the player is underground
- the skill or item is not on cooldown

If activation fails because the player is not underground, the system should return a failure response with minimal feedback.

## Underground Requirement

For the MVP, the player is considered underground when:

- the player is not exposed to the sky
- the player has solid cover overhead

This rule may be refined later, but should remain simple and predictable in the first version.

## Scan Rules

The scan is:

- directional
- horizontal only
- based on player yaw
- independent of player pitch

The scan area is:

- length: 5 blocks
- width: 3 blocks
- height: 2 blocks

This results in a total scan volume of 30 checked blocks.

## Direction Handling

The system supports 8 horizontal directions:

- N
- NE
- E
- SE
- S
- SW
- W
- NW

Player yaw is mapped to one of these 8 directions.

Each direction uses a predefined scan pattern.

## Cavity Detection Rules

The scan checks the selected pattern from the nearest row to the farthest row.

The system searches for the first valid hidden cavity ahead of the player.

A valid cavity is defined as:

- a connected air cluster
- using 6-directional adjacency
- with a minimum size of 3 connected air blocks

Single air blocks and insignificant micro-gaps should be ignored.

## Cluster Validation

When an air block is found inside the scan volume:

- a local cluster search is performed
- the search checks neighboring air blocks
- the cluster search is limited in size for performance reasons

Suggested initial search limit:

- 12 blocks maximum

If the cluster size is below the threshold, the result is discarded.

If it meets or exceeds the threshold, it becomes the first valid scan result.

## Result Rules

If a valid cavity is found:

- the scan stops
- the first detected valid cavity is used as the result
- visual and audio feedback are triggered

If no valid cavity is found:

- the player receives a weaker pulse response
- no cavity highlight is shown

## Feedback Rules

### On successful detection

The system should provide:

- a short directional pulse
- a brief highlight or outline of the detected cavity
- a distinct successful resonance sound

### On failed detection

The system should provide:

- a weaker pulse
- no cavity highlight
- a muted failure or empty resonance sound

### On invalid activation

If the player is not underground:

- the scan should not activate
- the system may play a subtle fail sound
- the system may show a minimal message such as:
  - `No underground resonance detected.`

## Cooldown

For the MVP:

- cooldown duration: 10 seconds

Cooldown always applies after a valid activation attempt.

This may later be split into hit and miss cooldowns, but not in the first version.

## MVP Scope Boundaries

The MVP does not include:

- ore detection
- permanent wall vision
- vertical scan mode
- progression tiers
- equipment variants
- AI-driven classification
- advanced cave type analysis
- multiplayer balancing
- final visual polish
- AI-assisted resonance interpretation

## MVP Success Criteria

The MVP is successful if it proves that:

- the directional scan feels intuitive
- the scan detects useful underground spaces
- the mechanic does not feel like full x-ray
- the underground restriction feels natural
- the cooldown creates meaningful use timing
- the feature is enjoyable enough to justify deeper development