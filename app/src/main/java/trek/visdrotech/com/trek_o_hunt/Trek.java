package trek.visdrotech.com.trek_o_hunt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Trek {

    enum TrekDifficulty {EASY, MEDIUM, DIFFICULT};

    String name;
    String imgUrl;
    String staticImgUrl;
    ArrayList<String> images;
    ArrayList<String> treasureHuntImages;
    int rating;
    TrekDifficulty difficulty;
    String about;
    String latitude;
    String longitude;
    String elevation;
    String transportMeans;
    String checkList;
    String thingsToNote;
    double price;


    public Trek()
    {
        images = new ArrayList<>();
        treasureHuntImages = new ArrayList<>();
    }

    public Trek(JSONObject jsonObject) throws JSONException
    {
    }

    public Trek(String name, String imgUrl, String statucImgUrl, ArrayList<String> images, ArrayList<String> treasureHuntImages, int rating, TrekDifficulty difficulty, String about, String latitude, String longitude, String elevation, String transportMeans, String checkList) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.staticImgUrl = statucImgUrl;
        this.images = images;
        this.treasureHuntImages = treasureHuntImages;
        this.rating = rating;
        this.difficulty = difficulty;
        this.about = about;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.transportMeans = transportMeans;
        this.checkList = checkList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStaticImgUrl() {
        return staticImgUrl;
    }

    public void setStaticImgUrl(String staticImgUrl) {
        this.staticImgUrl = staticImgUrl;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void addImages(String url) {
        this.images.add(url);
    }

    public ArrayList<String> getTreasureHuntImages() {
        return treasureHuntImages;
    }

    public void setTreasureHuntImages(ArrayList<String> treasureHuntImages) {
        this.treasureHuntImages = treasureHuntImages;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public TrekDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(TrekDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getTransportMeans() {
        return transportMeans;
    }

    public void setTransportMeans(String transportMeans) {
        this.transportMeans = transportMeans;
    }

    public String getCheckList() {
        return checkList;
    }

    public void setCheckList(String checkList) {
        this.checkList = checkList;
    }

    public String getThingsToNote() {
        return thingsToNote;
    }

    public void setThingsToNote(String thingsToNote) {
        this.thingsToNote = thingsToNote;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

