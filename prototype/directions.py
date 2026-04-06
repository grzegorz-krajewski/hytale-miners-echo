from enum import Enum


class Direction(str, Enum):
    N = "N"
    NE = "NE"
    E = "E"
    SE = "SE"
    S = "S"
    SW = "SW"
    W = "W"
    NW = "NW"


# 8 sectors, each 45 degrees wide.
# We shift by 22.5 degrees so values round to the nearest direction.
_DIRECTION_ORDER = [
    Direction.N,
    Direction.NE,
    Direction.E,
    Direction.SE,
    Direction.S,
    Direction.SW,
    Direction.W,
    Direction.NW,
]


def normalize_yaw(yaw: float) -> float:
    """
    Normalize any yaw value into the range [0, 360).
    """
    return yaw % 360.0


def yaw_to_direction(yaw: float) -> Direction:
    """
    Convert a yaw angle into one of 8 horizontal directions.

    Assumed sector layout:
        N   = 0
        NE  = 45
        E   = 90
        SE  = 135
        S   = 180
        SW  = 225
        W   = 270
        NW  = 315

    This mapping can be adjusted later if Hytale uses a different yaw convention.
    """
    normalized = normalize_yaw(yaw)
    index = int((normalized + 22.5) // 45) % 8
    return _DIRECTION_ORDER[index]


def direction_to_label(direction: Direction) -> str:
    """
    Return a printable label for the direction.
    """
    return direction.value


if __name__ == "__main__":
    test_yaws = [-45, 0, 10, 44.9, 45, 89.9, 90, 135, 180, 225, 270, 315, 359.9, 360, 405]

    for yaw in test_yaws:
        print(f"yaw={yaw:6.1f} -> {yaw_to_direction(yaw)}")