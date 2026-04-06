# Tests

This folder is intended to contain test-related materials for Miner’s Echo.

At the current stage of the project, testing is still scenario-based and focused on validating the core mechanic before any in-game integration.

## Current Testing Approach

The project currently uses manual scenario testing through the standalone prototype.

These scenarios are used to validate:

- yaw to 8-direction mapping
- directional scan behavior
- meaningful air cluster detection
- underground activation rules
- hit / miss / invalid activation flow

## Current Scenario Types

The prototype currently covers scenarios such as:

- single air block ahead -> expected miss
- two connected air blocks ahead -> expected miss
- three connected air blocks ahead -> expected hit
- straight scan hit
- diagonal scan hit
- no cavity ahead -> expected miss
- invalid activation on surface / sky exposure

## Purpose of This Folder

This folder will later hold:

- scenario documentation
- structured test case definitions
- automated tests
- prototype validation helpers
- CI-related test execution setup

## Short-Term Direction

Before introducing formal automated tests, the next goal is to define a consistent scenario format so that expected outcomes are clearly documented and easy to extend.

## Long-Term Direction

Later, this folder may include:

- unit tests for prototype logic
- scenario-based automated validation
- regression tests for scan behavior
- CI test execution workflows