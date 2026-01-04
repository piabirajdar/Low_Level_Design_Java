class FileSystem {
    private Directory root;
    private Directory currentDirectory;

    public FileSystem() {
        this.root = new Directory("root", null);
        this.currentDirectory = root;
    }

    /* ---------------- CREATE ---------------- */
    public void createFile(String path, String content) {
        Directory parent = resolveParent(path);
        String fileName = getName(path);

        if(parent.getChild(fileName) != null) {
            throw new IllegalArgumentException("File or directory already exists: " + fileName);
        }
        parent.addChild(new File(fileName, parent, content));
    }

    public void createDirectory(String path) {
        Directory parent = resolveParent(path);
        String dirName = getName(path);

        if(parent.getChild(dirName) != null) {
            throw new IllegalArgumentException("File or directory already exists: " + dirName);
        }
        parent.addChild(new Directory(dirName, parent));
    }

    /* ---------------- READ ---------------- */
    public String readFile(String path) {
        FileSystemNode node = resolvePath(path);

        if (node == null || !(node instanceof File)) {
            throw new IllegalArgumentException("File not found: " + path);
        }

        return ((File) node).getContent();
    }

    public List<String> listDirectory(String path) {
        FileSystemNode node = resolve(path);

        if (!(node instanceof Directory)) {
            throw new IllegalArgumentException("Path is not a directory");
        }

        return ((Directory) node).listChildren();
    }


    /* ---------------- WRITE ---------------- */
    public void writeFile(String path, String content) {
        FileSystemNode node = resolvePath(path);

        if (node == null || !(node instanceof File)) {
            throw new IllegalArgumentException("File not found: " + path);
        }

        ((File) node).setContent(content);
    }

    public void appendToFile(String path, String content) {
        FileSystemNode node = resolve(path);

        if (!(node instanceof File)) {
            throw new IllegalArgumentException("Path is not a file");
        }

        File file = (File) node;
        file.setContent(file.getContent() + content);
    }

    /* ---------------- DELETE ---------------- */
    public void delete(String path) {
        FileSystemNode node = resolve(path);
        Directory parent = node.getParent();

        parent.removeChild(node.getName());
    }

    /* ---------------- NAVIGATION ---------------- */





    /* ---------------- PATH RESOLUTION ---------------- */
    private FileSystemNode resolvePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Invalid path");
        }

        // Start point: root for absolute, current for relative
        FileSystemNode currentNode =
                path.startsWith("/") ? root : current;

        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }

            if (part.equals("..")) {
                if (currentNode.getParent() != null) {
                    currentNode = currentNode.getParent();
                }
                continue;
            }

            if (!(currentNode instanceof Directory)) {
                throw new IllegalArgumentException("Invalid path: not a directory");
            }

            FileSystemNode child =
                    ((Directory) currentNode).getChild(part);

            if (child == null) {
                throw new IllegalArgumentException("Path not found: " + path);
            }

            currentNode = child;
        }

        return currentNode;
    }
}
