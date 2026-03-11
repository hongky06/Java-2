package Baitap01;

public class UserValidator {
    public static boolean isValidUserName(String userName){
        if(userName.length() < 6 || userName.length() > 20 || userName.contains(" ")){
            return false;
        }
        return true;
    }

}
