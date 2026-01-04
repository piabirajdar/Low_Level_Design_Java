public class File extends FileSystemNode {

    private String content;

    public File(String name, Directory parent, String content) {
        super(name, parent);
        this.content = content;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        modifiedAt = System.currentTimeMillis();
    }
}
