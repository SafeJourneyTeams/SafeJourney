package rsta.safejourney;

/**
 * Created by TekNath on 14-Jan-18.
 */

public class User {

    private String name,phone,gender,driver;
    public User(String name, String phone, String grnder, String driver){
        this.name=name;
        this.phone=phone;
        this.gender=gender;
        this.driver=driver;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getDriver() {
        return driver;
    }
}
