package kmitl.kawin58070006.horyuni.model;

/**
 * Created by Administrator on 6/11/2560.
 */

public class Detail {
    private String name;
    private ImageUpload img;
    private String zone;
    private String moreDetail;

    public Detail(String name) {
        this.name = name;
    }

    public Detail(String name, ImageUpload imageUpload) {
        this.name = name;
        this.img = imageUpload;
    }

    public Detail(String name, ImageUpload img, String zone, String moreDetail) {
        this.name = name;
        this.img = img;
        this.zone = zone;
        this.moreDetail = moreDetail;
    }



    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getMoreDetail() {
        return moreDetail;
    }

    public void setMoreDetail(String moreDetail) {
        this.moreDetail = moreDetail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageUpload getImg() {
        return img;
    }

    public void setImg(ImageUpload img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }
}
