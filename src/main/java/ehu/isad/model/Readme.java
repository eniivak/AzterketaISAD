package ehu.isad.model;

public class Readme {
    private int id;
    private String bertsioa;
    private String md5;
    private String path;
    private String url;

    public Readme() {
    }

    public int getId() {
        return id;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }

    public String getBertsioa() {
        return bertsioa;
    }

    public String getMd5() {
        return md5;
    }

    public String getPath() {
        return path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBertsioa(String bertsioa) {
        this.bertsioa = bertsioa;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
