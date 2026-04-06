# Test Scenarios

This document tracks the current scenario-based test cases used for the standalone Miner’s Echo prototype.

Each scenario should describe:

- name
- purpose
- world setup
- player setup
- expected result

---

## Scenario: Single air block ahead

**Purpose**  
Verify that a single air block does not count as a meaningful cavity.

**World setup**  
- player is underground
- 1 air block exists ahead of the player
- all surrounding blocks are solid stone

**Player setup**  
- position: `(0, 0, 0)`
- yaw: `0` (North)

**Expected result**  
- `success = False`
- `reason = "miss"`

---

## Scenario: Two connected air blocks ahead

**Purpose**  
Verify that two connected air blocks still do not count as a meaningful cavity.

**World setup**  
- player is underground
- 2 connected air blocks exist ahead of the player
- all surrounding blocks are solid stone

**Player setup**  
- position: `(0, 0, 0)`
- yaw: `0` (North)

**Expected result**  
- `success = False`
- `reason = "miss"`

---

## Scenario: Three connected air blocks ahead

**Purpose**  
Verify that three connected air blocks count as a meaningful cavity.

**World setup**  
- player is underground
- 3 connected air blocks exist ahead of the player
- all surrounding blocks are solid stone

**Player setup**  
- position: `(0, 0, 0)`
- yaw: `0` (North)

**Expected result**  
- `success = True`
- `reason = "hit"`

---

## Scenario: Straight scan hit

**Purpose**  
Verify that a valid cavity directly ahead is detected by a straight scan.

**World setup**  
- player is underground
- a valid air cluster exists ahead of the player in the scan path

**Player setup**  
- position: `(0, 0, 0)`
- yaw: `0` (North)

**Expected result**  
- `success = True`
- `reason = "hit"`
- `direction = Direction.N`

---

## Scenario: Diagonal scan hit

**Purpose**  
Verify that diagonal direction mapping and diagonal scan patterns work correctly.

**World setup**  
- player is underground
- a valid air cluster exists ahead-right in the diagonal scan path

**Player setup**  
- position: `(0, 0, 0)`
- yaw: `45` (North-East)

**Expected result**  
- `success = True`
- `reason = "hit"`
- `direction = Direction.NE`

---

## Scenario: No cavity ahead

**Purpose**  
Verify that a full scan through solid stone returns a miss.

**World setup**  
- player is underground
- no air cluster exists in the scan volume

**Player setup**  
- position: `(0, 0, 0)`
- yaw: `0` (North)

**Expected result**  
- `success = False`
- `reason = "miss"`

---

## Scenario: Invalid activation on surface

**Purpose**  
Verify that Miner’s Echo cannot activate when the player is not underground.

**World setup**  
- player is exposed to the sky
- no solid cover overhead

**Player setup**  
- position: `(0, 0, 0)`
- yaw: `0` (North)

**Expected result**  
- `success = False`
- `reason = "invalid_activation"`