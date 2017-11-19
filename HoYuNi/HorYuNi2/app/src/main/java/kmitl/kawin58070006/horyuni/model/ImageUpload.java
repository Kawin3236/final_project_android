package kmitl.kawin58070006.horyuni.model;

/**
 * Created by Administrator on 1/11/2560.
 */

public class ImageUpload {

    public String name;
    private ImageUpload img;
    private String zone;
    private String moreDetail;
    public String url;
    public String url2;
    public String url3;
    public String url4;
    public String url5;
    public String url6;
    public String url7;
    public String url8;

    public ImageUpload(String name, String zone, String moreDetail, String url) {
        this.name = name;
        this.zone = zone;
        this.moreDetail = moreDetail;
        this.url = url;
    }
    public ImageUpload(String name, String zone, String moreDetail, String url, String url2) {
        this.name = name;
        this.zone = zone;
        this.moreDetail = moreDetail;
        this.url = url;
        this.url2 = url2;
    }

    public ImageUpload(String name, String zone, String moreDetail, String url, String url2, String url3) {
        this.name = name;
        this.zone = zone;
        this.moreDetail = moreDetail;
        this.url = url;
        this.url2 = url2;
        this.url3 = url3;
    }

    public ImageUpload(String name, String zone, String moreDetail, String url, String url2, String url3, String url4) {
        this.name = name;
        this.zone = zone;
        this.moreDetail = moreDetail;
        this.url = url;
        this.url2 = url2;
        this.url3 = url3;
        this.url4 = url4;
    }

    public ImageUpload(String name, String zone, String moreDetail, String url, String url2, String url3, String url4, String url5) {
        this.name = name;
        this.zone = zone;
        this.moreDetail = moreDetail;
        this.url = url;
        this.url2 = url2;
        this.url3 = url3;
        this.url4 = url4;
        this.url5 = url5;

    }

    public ImageUpload(String name, String zone, String moreDetail, String url, String url2, String url3, String url4, String url5, String url6) {
        this.name = name;
        this.zone = zone;
        this.moreDetail = moreDetail;
        this.url = url;
        this.url2 = url2;
        this.url3 = url3;
        this.url4 = url4;
        this.url5 = url5;
        this.url6 = url6;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }


    public ImageUpload getImg() {
        return img;
    }

    public void setImg(ImageUpload img) {
        this.img = img;
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

    public ImageUpload(String url2) {
        this.url2 = url2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getUrl4() {
        return url4;
    }

    public void setUrl4(String url4) {
        this.url4 = url4;
    }

    public String getUrl5() {
        return url5;
    }

    public void setUrl5(String url5) {
        this.url5 = url5;
    }

    public String getUrl6() {
        return url6;
    }

    public void setUrl6(String url6) {
        this.url6 = url6;
    }

    public String getUrl7() {
        return url7;
    }

    public void setUrl7(String url7) {
        this.url7 = url7;
    }

    public String getUrl8() {
        return url8;
    }

    public void setUrl8(String url8) {
        this.url8 = url8;
    }

    public ImageUpload() {
    }


}
