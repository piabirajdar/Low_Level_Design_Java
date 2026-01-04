public class Directory extends FileSystemNode {

    private final Map<String, FileSystemNode> children = new HashMap<>();

    public Directory(String name, Directory parent) {
        super(name, parent);
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    public void addChild(FileSystemNode node) {
        children.put(node.getName(), node);
        modifiedAt = System.currentTimeMillis();
    }

    public void removeChild(String name) {
        children.remove(name);
        modifiedAt = System.currentTimeMillis();
    }

    public FileSystemNode getChild(String name) {
        return children.get(name);
    }

    public boolean contains(String name) {
        return children.containsKey(name);
    }
}
