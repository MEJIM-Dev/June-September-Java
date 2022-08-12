package First.Application.Model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity()
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column()
    @NotBlank()
    @Size(min = 2, max=25)
    private String firstname;

//    @Size(min = 2, max = 10, message = "not between the valid range")
    @NotBlank()
    @Size(min = 2, max=25)
    private  String lastname;

    private int age;

    private String password;

    @Email(regexp = "[a-zA-Z0-9]{2,50}@[a-zA-Z]{4,12}.[a-z]{2,5}")
    private String email;

    private boolean activated = false;

    private String gender;

    public User(){

    }

    public User(String firstname, String lastname, int age, String password, String email,String gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.password = password;
        this.email = email;
        this.gender = gender;
    }

    public User(String name, String password, int age){
        this.age = age;
        this.firstname = name;
        this.password = password;
    }

    public String getFirstName() {
        return firstname;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegistrationObject{" +
                "name='" + firstname + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                '}';
    }
}
