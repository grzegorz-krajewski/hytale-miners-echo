from collections import deque

from world import World


MIN_MEANINGFUL_CLUSTER_SIZE = 3
MAX_CLUSTER_SEARCH_SIZE = 12


def get_6_neighbors(position: tuple[int, int, int]) -> list[tuple[int, int, int]]:
    x, y, z = position
    return [
        (x - 1, y, z),
        (x + 1, y, z),
        (x, y - 1, z),
        (x, y + 1, z),
        (x, y, z - 1),
        (x, y, z + 1),
    ]


def get_air_cluster(
    world: World,
    start_position: tuple[int, int, int],
    max_search_size: int = MAX_CLUSTER_SEARCH_SIZE,
) -> list[tuple[int, int, int]]:
    """
    Find a local connected air cluster using 6-directional adjacency.

    The search is intentionally capped for prototype performance and control.
    """
    if not world.is_air(start_position):
        return []

    visited: set[tuple[int, int, int]] = set()
    queue: deque[tuple[int, int, int]] = deque([start_position])
    cluster: list[tuple[int, int, int]] = []

    while queue and len(cluster) < max_search_size:
        position = queue.popleft()

        if position in visited:
            continue
        visited.add(position)

        if not world.is_air(position):
            continue

        cluster.append(position)

        for neighbor in get_6_neighbors(position):
            if neighbor not in visited:
                queue.append(neighbor)

    return cluster


def is_meaningful_cluster(
    cluster: list[tuple[int, int, int]],
    min_size: int = MIN_MEANINGFUL_CLUSTER_SIZE,
) -> bool:
    return len(cluster) >= min_size


def get_meaningful_air_cluster(
    world: World,
    start_position: tuple[int, int, int],
    min_size: int = MIN_MEANINGFUL_CLUSTER_SIZE,
    max_search_size: int = MAX_CLUSTER_SEARCH_SIZE,
) -> list[tuple[int, int, int]]:
    cluster = get_air_cluster(
        world=world,
        start_position=start_position,
        max_search_size=max_search_size,
    )

    if is_meaningful_cluster(cluster, min_size=min_size):
        return cluster

    return []


if __name__ == "__main__":
    from world import AIR

    world = World()
    world.set_block((1, 0, 0), AIR)
    world.set_block((2, 0, 0), AIR)
    world.set_block((3, 0, 0), AIR)

    cluster = get_meaningful_air_cluster(world, (1, 0, 0))
    print("Cluster:", cluster)
    print("Size:", len(cluster))