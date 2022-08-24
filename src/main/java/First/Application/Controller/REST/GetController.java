package First.Application.Controller.REST;

import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.User;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GetController {

    @Autowired
    UserServiceImplementation userImpl;

    String[] groceries= {"apples","oranges","bananas"};
    String[] electronics= {"laptop","smart phone","Power bank"};
    String[] healthBeauty= {"soap","creams","wigs"};
    String[] empty = {"404, No such category found"};

    @RequestMapping(value = "/",method = RequestMethod.GET) //@GetMapping("/")
    public String Homepage(){
        System.out.println("firstname");
        return "welcome";
    }

    @GetMapping("/find/user/firstname")
    public User FindByFirstname(@RequestParam("firstname") String firstname) throws UserNotFoundException {
        return userImpl.findByFirstname(firstname);
    }

    @GetMapping("/allusers")
    public List<User> GetUser(){

        return userImpl.findAllUser();
    }

    @GetMapping("/electronics")
    public String[] GetElectronicsCategory(){
        return electronics;
    }

    @GetMapping("/health-beauty")
    public String[] GtElectronicsCategory(){
        return healthBeauty;
    }

    @GetMapping("/path1/path2")
    public String DuoPaths(){
        return "duo path responded";
    }

    @GetMapping("/path1/path2/paths")
    public String TriPath(){
        return "duo path responded";
    }

    @GetMapping("/groceries")
    public String[] GeElectronicsCategory(){
        return groceries;
    }


    @GetMapping("/login/redirect/")
    public String QueryParamLogin(@RequestParam() String returnUrl){
        return "welcome! "+ "The return url is: "+returnUrl;
    }

    @GetMapping("/category/viaqueryparams/")
    public String  MultiQueryParamLogin(@RequestParam("category") String category, @RequestParam("item") String item){

        int i = 0;

        try{
            i= Integer.parseInt(item);
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            return "Enter a valid Number";
        }

        String errMsg ="No such item with an index of: "+i+" in this category";

        if(category.equalsIgnoreCase("health-beauty")){
            return i>=0 && i < healthBeauty.length? healthBeauty[i]: errMsg;
        }
        if(category.equalsIgnoreCase("groceries") || category.equalsIgnoreCase("grocery")){
            return i>=0 && i < groceries.length? groceries[i] : errMsg;
        }
        if(category.equalsIgnoreCase("electronics") || category.equalsIgnoreCase("electronic-appliances")){
            return i>=0 && i < electronics.length? electronics[i] : errMsg;
        }

        return "404 no product found";
    }


    @GetMapping("/category/{categories}/{item}")
    public String LoginWithPathParam(@PathVariable("categories") String data, @PathVariable("item") String itemIndex){

        int i = 0;

        try{
            i= Integer.parseInt(itemIndex);
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            return "Enter a valid Number";
        }

        String errMsg ="No such item with an index of: "+i+" in this category";

        if(data.equalsIgnoreCase("health-beauty")){
//            if(i >= 0 &&i < healthBeauty.length){
//                return healthBeauty[i];
//            } else {
//                return errMsg;
//            }
            return i>=0 && i < healthBeauty.length? healthBeauty[i]: errMsg;
        }
        if(data.equalsIgnoreCase("groceries")){
            return i>=0 && i < groceries.length? groceries[i] : errMsg;
        }
        if(data.equalsIgnoreCase("electronics")){
            return i>=0 && i < electronics.length? electronics[i] : errMsg;
        }

        return "404 No product found";
    }


//    @GetMapping("/**")
//    public String ResourceNotFound(){
//        return "404, No such path has been mapped";
//    }
}
