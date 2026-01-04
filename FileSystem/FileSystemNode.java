public abstract class FileSystemNode {
    protected String name;
    protected long createdAt;
    protected long modifiedAt;
    protected Directory parent;

    public FileSystemNode(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.createdAt = System.currentTimeMillis();
        this.modifiedAt = this.createdAt;
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    public abstract boolean isDirectory();
}
