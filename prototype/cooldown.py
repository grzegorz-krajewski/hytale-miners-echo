from dataclasses import dataclass, field


DEFAULT_COOLDOWN_SECONDS = 10.0


@dataclass
class CooldownTracker:
    duration_seconds: float = DEFAULT_COOLDOWN_SECONDS
    ready_at_by_player: dict[str, float] = field(default_factory=dict)

    def is_on_cooldown(self, player_id: str, now: float) -> bool:
        ready_at = self.ready_at_by_player.get(player_id, 0.0)
        return now < ready_at

    def start_cooldown(self, player_id: str, now: float) -> None:
        self.ready_at_by_player[player_id] = now + self.duration_seconds

    def remaining_seconds(self, player_id: str, now: float) -> float:
        ready_at = self.ready_at_by_player.get(player_id, 0.0)
        return max(0.0, ready_at - now)