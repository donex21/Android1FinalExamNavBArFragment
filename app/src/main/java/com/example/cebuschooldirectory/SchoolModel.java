package com.example.cebuschooldirectory;

public class SchoolModel {

    private String schoolname, schooladdress, schoolemail, schoolcontact, schoolwebsite, schoollatitude, schoollongitude, postedby;

    public SchoolModel(){

    }

    public SchoolModel(String schoolname, String schooladdress, String schoolemail, String schoolcontact, String schoolwebsite, String schoollatitude, String schoollongitude, String postedby) {
        this.schoolname = schoolname;
        this.schooladdress = schooladdress;
        this.schoolemail = schoolemail;
        this.schoolcontact = schoolcontact;
        this.schoolwebsite = schoolwebsite;
        this.schoollatitude = schoollatitude;
        this.schoollongitude = schoollongitude;
        this.postedby = postedby;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getSchooladdress() {
        return schooladdress;
    }

    public void setSchooladdress(String schooladdress) {
        this.schooladdress = schooladdress;
    }

    public String getSchoolemail() {
        return schoolemail;
    }

    public void setSchoolemail(String schoolemail) {
        this.schoolemail = schoolemail;
    }

    public String getSchoolcontact() {
        return schoolcontact;
    }

    public void setSchoolcontact(String schoolcontact) {
        this.schoolcontact = schoolcontact;
    }

    public String getSchoolwebsite() {
        return schoolwebsite;
    }

    public void setSchoolwebsite(String schoolwebsite) {
        this.schoolwebsite = schoolwebsite;
    }

    public String getSchoollatitude() {
        return schoollatitude;
    }

    public void setSchoollatitude(String schoollatitude) {
        this.schoollatitude = schoollatitude;
    }

    public String getSchoollongitude() {
        return schoollongitude;
    }

    public void setSchoollongitude(String schoollongitude) {
        this.schoollongitude = schoollongitude;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }
}
