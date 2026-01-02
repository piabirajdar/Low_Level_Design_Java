
 enum DiscColor {
    RED,
    YELLOW,
    EMPTY
 }

 class Player {
    - String name
    - DiscColor color

    + Player(String name, DiscColor color)
    + getName() -> String
    + getColor() -> DiscColor
 }