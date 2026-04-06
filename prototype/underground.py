from world import World


MAX_OVERHEAD_CHECK_HEIGHT = 6
MIN_SOLID_BLOCKS_OVERHEAD = 3


def get_blocks_above(
    position: tuple[int, int, int],
    height: int = MAX_OVERHEAD_CHECK_HEIGHT,
) -> list[tuple[int, int, int]]:
    x, y, z = position
    return [(x, y + offset, z) for offset in range(1, height + 1)]


def is_exposed_to_sky(
    world: World,
    position: tuple[int, int, int],
    height: int = MAX_OVERHEAD_CHECK_HEIGHT,
) -> bool:
    """
    Prototype sky exposure rule:
    if all checked blocks above are air, the player is considered exposed.
    """
    blocks_above = get_blocks_above(position, height=height)
    return all(world.is_air(block_pos) for block_pos in blocks_above)


def count_solid_blocks_above(
    world: World,
    position: tuple[int, int, int],
    height: int = MAX_OVERHEAD_CHECK_HEIGHT,
) -> int:
    blocks_above = get_blocks_above(position, height=height)
    return sum(1 for block_pos in blocks_above if world.is_solid(block_pos))


def has_solid_cover_overhead(
    world: World,
    position: tuple[int, int, int],
    height: int = MAX_OVERHEAD_CHECK_HEIGHT,
    min_solid_blocks: int = MIN_SOLID_BLOCKS_OVERHEAD,
) -> bool:
    solid_count = count_solid_blocks_above(world, position, height=height)
    return solid_count >= min_solid_blocks


def is_underground(
    world: World,
    position: tuple[int, int, int],
    height: int = MAX_OVERHEAD_CHECK_HEIGHT,
    min_solid_blocks: int = MIN_SOLID_BLOCKS_OVERHEAD,
) -> bool:
    return (
        not is_exposed_to_sky(world, position, height=height)
        and has_solid_cover_overhead(
            world,
            position,
            height=height,
            min_solid_blocks=min_solid_blocks,
        )
    )


if __name__ == "__main__":
    from world import AIR, STONE

    world = World(default_block=AIR)

    # Make a simple stone ceiling above the player.
    world.set_block((0, 1, 0), STONE)
    world.set_block((0, 2, 0), STONE)
    world.set_block((0, 3, 0), STONE)

    print("Underground:", is_underground(world, (0, 0, 0)))