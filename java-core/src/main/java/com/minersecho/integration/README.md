# Integration

This directory contains the first integration-layer planning and code structure for bringing Miner’s Echo into an in-game runtime.

The goal of the first vertical slice is intentionally small.

## First in-game vertical slice

The first playable in-game version should include:

- activation trigger
- player position and yaw input
- world block access through an adapter layer
- Miner’s Echo core scan execution
- per-player cooldown handling
- text-based feedback only

## Out of scope for the first vertical slice

The following are intentionally postponed:

- visual cavity outline
- advanced VFX
- advanced UI
- AI-assisted resonance interpretation
- final balancing polish

## Integration philosophy

The core scan logic should remain separate from runtime-specific APIs.

The integration layer should only adapt:

- player data
- world access
- cooldown state
- feedback delivery