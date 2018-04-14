package asquero.com.myapplication;

/**
 * Created by Anmol on 10-Apr-18.
 */

class UpcomingList {
    private String contestCode;
    private String contestName;
    private String startDate;
    private String endDate;
    //private int image;
    private String imageUrl;
    private String AIC;

    public UpcomingList(String contestCode, String contestName, String startDate, String endDate, String imageUrl, String AIC) {
        this.contestCode = contestCode;
        this.contestName = contestName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.AIC = AIC;
    }

    public String getContestCode() {
        return contestCode;
    }

    public String getContestName() {
        return contestName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getImageUrl() { return imageUrl; }

    public String getAIC() {
        return AIC;
    }
}