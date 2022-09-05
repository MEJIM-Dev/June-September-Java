package First.Application.Model.ResponseEntity;

import First.Application.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ResponseObject {

    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private int age;

    public ResponseObject(User user){
        this.email=user.getEmail();
        this.firstname=user.getFirstName();
        this.lastname=user.getLastname();
        this.gender=user.getGender();
        this.age=user.getAge();
    }

}
