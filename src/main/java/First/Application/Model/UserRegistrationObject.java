package First.Application.Model;

import lombok.Data;

@Data
public class UserRegistrationObject {
    private Long id;

    private String firstname;

    private String lastname;

    private int age;

    private String password;

    private String email;

    private boolean activated = true;

    private String gender;

    public UserRegistrationObject(){

    }

    public UserRegistrationObject(Long id, String firstname, String lastname, int age, String password, String email, boolean activated, String gender) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.password = password;
        this.email = email;
        this.activated = activated;
        this.gender = gender;
    }


    public UserRegistrationObject(String firstname, String password, int age, Long id){
        this.age = age;
        this.firstname = firstname;
        this.password = password;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String name) {
        this.firstname = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegistrationObject{" +
                "firstname='" + firstname + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                '}';
    }
}