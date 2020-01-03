package com.example.myapplication.Model;

public class Cart {

    private String evdate,evid,evlocation,evname,evpricedescription,evtime;

    public Cart() {
    }

    public Cart(String evdate, String evid, String evlocation, String evname, String evpricedescription, String evtime) {
        this.evdate = evdate;
        this.evid = evid;
        this.evlocation = evlocation;
        this.evname = evname;
        this.evpricedescription = evpricedescription;
        this.evtime = evtime;
    }

    public String getEvdate() {
        return evdate;
    }

    public void setEvdate(String evdate) {
        this.evdate = evdate;
    }

    public String getEvid() {
        return evid;
    }

    public void setEvid(String evid) {
        this.evid = evid;
    }

    public String getEvlocation() {
        return evlocation;
    }

    public void setEvlocation(String evlocation) {
        this.evlocation = evlocation;
    }

    public String getEvname() {
        return evname;
    }

    public void setEvname(String evname) {
        this.evname = evname;
    }

    public String getEvpricedescription() {
        return evpricedescription;
    }

    public void setEvpricedescription(String evpricedescription) {
        this.evpricedescription = evpricedescription;
    }

    public String getEvtime() {
        return evtime;
    }

    public void setEvtime(String evtime) {
        this.evtime = evtime;
    }
}
