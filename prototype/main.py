from player import Player
from scanner import scan_for_cavity
from world import AIR, STONE, World


def print_result(title: str, result) -> None:
    print(f"\n=== {title} ===")
    print(f"success: {result.success}")
    print(f"reason: {result.reason}")
    print(f"direction: {result.direction}")
    print(f"cluster size: {len(result.cluster_positions)}")
    print(f"checked positions: {len(result.checked_positions)}")
    if result.cluster_positions:
        print("cluster positions:")
        for pos in result.cluster_positions:
            print(f"  {pos}")


def create_hit_world() -> tuple[World, Player]:
    world = World(default_block=STONE)

    # Underground cover above player
    world.set_block((0, 1, 0), STONE)
    world.set_block((0, 2, 0), STONE)
    world.set_block((0, 3, 0), STONE)

    # Meaningful cavity ahead to the north
    world.set_block((0, 0, -2), AIR)
    world.set_block((0, 1, -2), AIR)
    world.set_block((1, 0, -2), AIR)

    player = Player(x=0, y=0, z=0, yaw=0)
    return world, player

def create_diagonal_hit_world() -> tuple[World, Player]:
    world = World(default_block=STONE)

    # Underground cover above player
    world.set_block((0, 1, 0), STONE)
    world.set_block((0, 2, 0), STONE)
    world.set_block((0, 3, 0), STONE)

    # Meaningful cavity ahead to the north-east
    world.set_block((2, 0, -2), AIR)
    world.set_block((2, 1, -2), AIR)
    world.set_block((3, 0, -2), AIR)

    # yaw=45 -> NE in our prototype convention
    player = Player(x=0, y=0, z=0, yaw=45)
    return world, player#

def create_miss_world() -> tuple[World, Player]:
    world = World(default_block=STONE)

    # Underground cover above player
    world.set_block((0, 1, 0), STONE)
    world.set_block((0, 2, 0), STONE)
    world.set_block((0, 3, 0), STONE)

    # No cavity ahead
    player = Player(x=0, y=0, z=0, yaw=0)
    return world, player


def create_invalid_activation_world() -> tuple[World, Player]:
    world = World(default_block=AIR)

    # No solid cover above player, exposed to sky
    player = Player(x=0, y=0, z=0, yaw=0)
    return world, player

def create_single_air_world() -> tuple[World, Player]:
    world = World(default_block=STONE)

    # Underground cover above player
    world.set_block((0, 1, 0), STONE)
    world.set_block((0, 2, 0), STONE)
    world.set_block((0, 3, 0), STONE)

    # Only 1 air block ahead -> should NOT count as meaningful cavity
    world.set_block((0, 0, -2), AIR)

    player = Player(x=0, y=0, z=0, yaw=0)
    return world, player


def create_two_air_world() -> tuple[World, Player]:
    world = World(default_block=STONE)

    # Underground cover above player
    world.set_block((0, 1, 0), STONE)
    world.set_block((0, 2, 0), STONE)
    world.set_block((0, 3, 0), STONE)

    # 2 connected air blocks -> still should NOT count
    world.set_block((0, 0, -2), AIR)
    world.set_block((0, 1, -2), AIR)

    player = Player(x=0, y=0, z=0, yaw=0)
    return world, player


def create_three_air_world() -> tuple[World, Player]:
    world = World(default_block=STONE)

    # Underground cover above player
    world.set_block((0, 1, 0), STONE)
    world.set_block((0, 2, 0), STONE)
    world.set_block((0, 3, 0), STONE)

    # 3 connected air blocks -> should count as meaningful cavity
    world.set_block((0, 0, -2), AIR)
    world.set_block((0, 1, -2), AIR)
    world.set_block((1, 0, -2), AIR)

    player = Player(x=0, y=0, z=0, yaw=0)
    return world, player

def main() -> None:
    scenarios = [
        ("Single air block scenario", create_single_air_world()),
        ("Two air blocks scenario", create_two_air_world()),
        ("Three air blocks scenario", create_three_air_world()),
        ("Hit scenario", create_hit_world()),
        ("Diagonal hit scenario", create_diagonal_hit_world()),
        ("Miss scenario", create_miss_world()),
        ("Invalid activation scenario", create_invalid_activation_world()),
    ]

    for title, (world, player) in scenarios:
        result = scan_for_cavity(world, player)
        print_result(title, result)


if __name__ == "__main__":
    main()