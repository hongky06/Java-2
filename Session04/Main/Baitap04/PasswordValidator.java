package Baitap04;

public class PasswordValidator {

    public String evaluatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            return "Yếu";
        }

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        // Đếm xem có bao nhiêu loại ký tự thỏa mãn
        int count = 0;
        if (hasUpper) count++;
        if (hasLower) count++;
        if (hasDigit) count++;
        if (hasSpecial) count++;

        if (count == 4) {
            return "Mạnh";
        } else if (count >= 3) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }
}