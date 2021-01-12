package ehu.isad.model;

public class Model {
    private int id;
    private String filename;
    private String value;
    private int date;

    public void setId(int id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getValue() {
        return value;
    }

    public int getDate() {
        return date;
    }
}
