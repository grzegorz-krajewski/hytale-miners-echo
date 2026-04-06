from cooldown import CooldownTracker
from scanner import scan_for_cavity
from scenarios import SCENARIOS


def print_result(title: str, result, cooldown_remaining: float | None = None) -> None:
    print(f"\n=== {title} ===")
    print(f"success: {result.success}")
    print(f"reason: {result.reason}")
    print(f"direction: {result.direction}")
    print(f"cluster size: {len(result.cluster_positions)}")
    print(f"checked positions: {len(result.checked_positions)}")

    if cooldown_remaining is not None:
        print(f"cooldown remaining: {cooldown_remaining:.1f}s")

    if result.cluster_positions:
        print("cluster positions:")
        for pos in result.cluster_positions:
            print(f"  {pos}")


def run_basic_scenarios() -> None:
    for title, (world, player) in SCENARIOS:
        result = scan_for_cavity(world, player)
        print_result(title, result)


def run_cooldown_demo() -> None:
    print("\n=== Cooldown demo ===")

    title, (world, player) = next(
        (item for item in SCENARIOS if item[0] == "Hit scenario")
    )

    cooldown = CooldownTracker(duration_seconds=10.0)

    now = 0.0
    on_cooldown = cooldown.is_on_cooldown(player.player_id, now)
    result = scan_for_cavity(world, player, on_cooldown=on_cooldown)
    print_result("Cooldown demo - first activation", result)

    if result.reason in {"hit", "miss"}:
        cooldown.start_cooldown(player.player_id, now)

    now = 2.0
    on_cooldown = cooldown.is_on_cooldown(player.player_id, now)
    result = scan_for_cavity(world, player, on_cooldown=on_cooldown)
    print_result(
        "Cooldown demo - second activation at t=2",
        result,
        cooldown_remaining=cooldown.remaining_seconds(player.player_id, now),
    )

    now = 10.1
    on_cooldown = cooldown.is_on_cooldown(player.player_id, now)
    result = scan_for_cavity(world, player, on_cooldown=on_cooldown)
    print_result(
        "Cooldown demo - third activation at t=10.1",
        result,
        cooldown_remaining=cooldown.remaining_seconds(player.player_id, now),
    )


def main() -> None:
    run_basic_scenarios()
    run_cooldown_demo()


if __name__ == "__main__":
    main()