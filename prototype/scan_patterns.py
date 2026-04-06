from directions import Direction


BASE_SCAN_PATTERNS: dict[Direction, list[tuple[int, int, int]]] = {
    Direction.N: [
        (-1, 0, -1), (0, 0, -1), (1, 0, -1),
        (-1, 0, -2), (0, 0, -2), (1, 0, -2),
        (-1, 0, -3), (0, 0, -3), (1, 0, -3),
        (-1, 0, -4), (0, 0, -4), (1, 0, -4),
        (-1, 0, -5), (0, 0, -5), (1, 0, -5),
    ],
    Direction.S: [
        (-1, 0, 1), (0, 0, 1), (1, 0, 1),
        (-1, 0, 2), (0, 0, 2), (1, 0, 2),
        (-1, 0, 3), (0, 0, 3), (1, 0, 3),
        (-1, 0, 4), (0, 0, 4), (1, 0, 4),
        (-1, 0, 5), (0, 0, 5), (1, 0, 5),
    ],
    Direction.E: [
        (1, 0, -1), (1, 0, 0), (1, 0, 1),
        (2, 0, -1), (2, 0, 0), (2, 0, 1),
        (3, 0, -1), (3, 0, 0), (3, 0, 1),
        (4, 0, -1), (4, 0, 0), (4, 0, 1),
        (5, 0, -1), (5, 0, 0), (5, 0, 1),
    ],
    Direction.W: [
        (-1, 0, -1), (-1, 0, 0), (-1, 0, 1),
        (-2, 0, -1), (-2, 0, 0), (-2, 0, 1),
        (-3, 0, -1), (-3, 0, 0), (-3, 0, 1),
        (-4, 0, -1), (-4, 0, 0), (-4, 0, 1),
        (-5, 0, -1), (-5, 0, 0), (-5, 0, 1),
    ],
    Direction.NE: [
        (0, 0, -1), (1, 0, -1), (1, 0, 0),
        (1, 0, -2), (2, 0, -2), (2, 0, -1),
        (2, 0, -3), (3, 0, -3), (3, 0, -2),
        (3, 0, -4), (4, 0, -4), (4, 0, -3),
        (4, 0, -5), (5, 0, -5), (5, 0, -4),
    ],
    Direction.NW: [
        (0, 0, -1), (-1, 0, -1), (-1, 0, 0),
        (-1, 0, -2), (-2, 0, -2), (-2, 0, -1),
        (-2, 0, -3), (-3, 0, -3), (-3, 0, -2),
        (-3, 0, -4), (-4, 0, -4), (-4, 0, -3),
        (-4, 0, -5), (-5, 0, -5), (-5, 0, -4),
    ],
    Direction.SE: [
        (0, 0, 1), (1, 0, 1), (1, 0, 0),
        (1, 0, 2), (2, 0, 2), (2, 0, 1),
        (2, 0, 3), (3, 0, 3), (3, 0, 2),
        (3, 0, 4), (4, 0, 4), (4, 0, 3),
        (4, 0, 5), (5, 0, 5), (5, 0, 4),
    ],
    Direction.SW: [
        (0, 0, 1), (-1, 0, 1), (-1, 0, 0),
        (-1, 0, 2), (-2, 0, 2), (-2, 0, 1),
        (-2, 0, 3), (-3, 0, 3), (-3, 0, 2),
        (-3, 0, 4), (-4, 0, 4), (-4, 0, 3),
        (-4, 0, 5), (-5, 0, 5), (-5, 0, 4),
    ],
}


def expand_pattern_to_two_layers(
    pattern: list[tuple[int, int, int]],
) -> list[tuple[int, int, int]]:
    expanded: list[tuple[int, int, int]] = []

    for x, _, z in pattern:
        expanded.append((x, 0, z))
        expanded.append((x, 1, z))

    return expanded


SCAN_PATTERNS: dict[Direction, list[tuple[int, int, int]]] = {
    direction: expand_pattern_to_two_layers(pattern)
    for direction, pattern in BASE_SCAN_PATTERNS.items()
}


def get_scan_pattern(direction: Direction) -> list[tuple[int, int, int]]:
    return SCAN_PATTERNS[direction]


if __name__ == "__main__":
    for direction, pattern in SCAN_PATTERNS.items():
        print(f"{direction.value}: {len(pattern)} blocks")