package Baitap02;

public class UserService {
    public static boolean checkRegistrationAge(int age){
        if(age < 0){
            throw new IllegalArgumentException("Tuổi làm gì có âm trêu t à cu");
        } else if (age < 18) {
            return false;
        }
        return true;
    }
}
