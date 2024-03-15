package com.bbdgrad.beanIndex;

public class GDPData {

    private String country;
    private Double gdpAmount;

    public GDPData(String country, Double gdpAmount) {
        this.country = country;
        this.gdpAmount = gdpAmount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getGdpAmount() {
        return gdpAmount;
    }

    public void setGdpAmount(Double gdpAmount) {
        this.gdpAmount = gdpAmount;
    }

    @Override
    public String toString() {
        return "GDPData{" +
                "country='" + country + '\'' +
                ", gdpAmount=" + gdpAmount +
                '}';
    }
    
}
