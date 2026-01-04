public class Player {

    private final String name;
    private final Mark mark;   // X or O

    public Player(String name, Mark mark) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
        if (mark == null) {
            throw new IllegalArgumentException("Mark cannot be null");
        }
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public Mark getMark() {
        return mark;
    }
}

public enum Mark {
    X,
    O
}