package MODULE_ADVANCED_PRORAMMING_WITH_JAVA.Mode.Entity;

public class User {
    private String numberPhone;
    private String Group;
    private String FullName;
    private String Sex;
    private String Address;
    private String Birthday; // Định dạng DD/MM/YYYY
    private String Email;

    public User(String numberPhone, String Group, String FullName, String Sex, String Address, String Birthday, String Email) {
        this.numberPhone = numberPhone;
        this.Group = Group;
        this.FullName = FullName;
        this.Sex = Sex;
        this.Address = Address;
        this.Birthday = Birthday;
        this.Email = Email;
    }

    public User(String fullName, String group, String numberPhone, String sex, String address) {
        this.FullName = fullName;
        this.Group = group;
        this.numberPhone = numberPhone;
        this.Sex = sex;
        this.Address = address;
        this.Birthday = null;
        this.Email = null;
    }

    public String getNumberPhone(){ return numberPhone;}
    public String getGroup(){ return Group;}
    public String getFullName(){ return FullName;}
    public String getSex() {return Sex;}
    public String getAddress() {return Address;}
    public String getBirthday() {return Birthday;}
    public String getEmail() {return Email;}

    public void setNumberPhone(String numberPhone) { this.numberPhone = numberPhone; }
    public void setGroup(String group) { this.Group = group; }
    public void setFullName(String fullName) { this.FullName = fullName; }
    public void setSex(String sex) { this.Sex = sex; }
    public void setAddress(String address) { this.Address = address; }
    public void setBirthday(String birthday) { this.Birthday = birthday; }
    public void setEmail(String email) { this.Email = email; }

    @Override
    public String toString() {
        return "Số điện thoại = " + numberPhone
                + ", Nhóm = " + Group
                + ", Tên đầy đủ = " + FullName
                + ", Giới tính =" + Sex
                + ", Địa chỉ = " + Address
                + ", Ngày sinh = " + Birthday
                + ", Email = " + Email;
    }
}