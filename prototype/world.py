from dataclasses import dataclass, field


AIR = "air"
STONE = "stone"


@dataclass
class World:
    blocks: dict[tuple[int, int, int], str] = field(default_factory=dict)
    default_block: str = STONE

    def get_block(self, position: tuple[int, int, int]) -> str:
        return self.blocks.get(position, self.default_block)

    def set_block(self, position: tuple[int, int, int], block_type: str) -> None:
        self.blocks[position] = block_type

    def is_air(self, position: tuple[int, int, int]) -> bool:
        return self.get_block(position) == AIR

    def is_solid(self, position: tuple[int, int, int]) -> bool:
        return not self.is_air(position)

    def fill_air(self, positions: list[tuple[int, int, int]]) -> None:
        for position in positions:
            self.set_block(position, AIR)

    def fill_stone(self, positions: list[tuple[int, int, int]]) -> None:
        for position in positions:
            self.set_block(position, STONE)