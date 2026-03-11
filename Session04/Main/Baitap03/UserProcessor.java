package Baitap03;

public class UserProcessor {

    public String processEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex == email.length() - 1) {
            throw new IllegalArgumentException("Định dạng email không hợp lệ");
        }
        return email.toLowerCase();
    }
}