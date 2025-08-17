package space.jsserver.entertest.problem;

public class Problem {
    private String id;
    private String description;
    private boolean isImg;
    private String imgUrl;

    public Problem() {}
    public Problem(String id, String description, boolean isImg, String imgUrl) {
        this.id = id;
        this.description = description;
        this.isImg = isImg;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isImg() {
        return isImg;
    }

    public void setImg(boolean img) {
        isImg = img;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
