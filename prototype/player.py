from dataclasses import dataclass


@dataclass(frozen=True)
class Player:
    x: int
    y: int
    z: int
    yaw: float

    @property
    def position(self) -> tuple[int, int, int]:
        return (self.x, self.y, self.z)

    @property
    def scan_origin(self) -> tuple[int, int, int]:
        """
        Prototype scan origin.

        For now, we use the player's block position directly.
        This can later be adjusted if we want the scan to start
        from torso or head level more explicitly.
        """
        return (self.x, self.y, self.z)