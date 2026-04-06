from dataclasses import dataclass, field

from cavity import get_meaningful_air_cluster
from directions import Direction, yaw_to_direction
from player import Player
from scan_patterns import get_scan_pattern
from underground import is_underground
from world import World


@dataclass(frozen=True)
class ScanResult:
    success: bool
    reason: str
    direction: Direction | None = None
    cluster_positions: list[tuple[int, int, int]] = field(default_factory=list)
    checked_positions: list[tuple[int, int, int]] = field(default_factory=list)


def apply_offset(
    origin: tuple[int, int, int],
    offset: tuple[int, int, int],
) -> tuple[int, int, int]:
    ox, oy, oz = origin
    dx, dy, dz = offset
    return (ox + dx, oy + dy, oz + dz)


def scan_for_cavity(
    world: World,
    player: Player,
    *,
    on_cooldown: bool = False,
) -> ScanResult:
    """
    Main prototype scan flow for Miner's Echo.

    Rules:
    - activation allowed only underground
    - direction determined from player yaw
    - scan uses predefined directional pattern
    - first meaningful air cluster ends the scan
    - activation can be blocked by cooldown
    """
    if on_cooldown:
        return ScanResult(
            success=False,
            reason="cooldown",
            direction=None,
            cluster_positions=[],
            checked_positions=[],
        )

    if not is_underground(world, player.position):
        return ScanResult(
            success=False,
            reason="invalid_activation",
            direction=None,
            cluster_positions=[],
            checked_positions=[],
        )

    direction = yaw_to_direction(player.yaw)
    pattern = get_scan_pattern(direction)
    origin = player.scan_origin

    checked_positions: list[tuple[int, int, int]] = []

    for offset in pattern:
        position = apply_offset(origin, offset)
        checked_positions.append(position)

        if not world.is_air(position):
            continue

        cluster = get_meaningful_air_cluster(world, position)
        if cluster:
            return ScanResult(
                success=True,
                reason="hit",
                direction=direction,
                cluster_positions=cluster,
                checked_positions=checked_positions,
            )

    return ScanResult(
        success=False,
        reason="miss",
        direction=direction,
        cluster_positions=[],
        checked_positions=checked_positions,
    )


if __name__ == "__main__":
    from world import AIR, STONE

    world = World(default_block=STONE)

    world.set_block((0, 1, 0), STONE)
    world.set_block((0, 2, 0), STONE)
    world.set_block((0, 3, 0), STONE)

    world.set_block((0, 0, -2), AIR)
    world.set_block((0, 1, -2), AIR)
    world.set_block((1, 0, -2), AIR)

    player = Player(x=0, y=0, z=0, yaw=0)

    result = scan_for_cavity(world, player)

    print("Success:", result.success)
    print("Reason:", result.reason)
    print("Direction:", result.direction)
    print("Cluster:", result.cluster_positions)
    print("Checked:", len(result.checked_positions))