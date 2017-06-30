package com.example.administrator.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("areacode")
    @Expose
    private String areacode;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("card")
    @Expose
    private String card;

    /**
     *
     * @return
     * The province
     */
    public String getProvince() {
        return province;
    }

    /**
     *
     * @param province
     * The province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The areacode
     */
    public String getAreacode() {
        return areacode;
    }

    /**
     *
     * @param areacode
     * The areacode
     */
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    /**
     *
     * @return
     * The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     * The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     *
     * @return
     * The company
     */
    public String getCompany() {
        return company;
    }

    /**
     *
     * @param company
     * The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return
     * The card
     */
    public String getCard() {
        return card;
    }

    /**
     *
     * @param card
     * The card
     */
    public void setCard(String card) {
        this.card = card;
    }

}
