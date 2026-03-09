// Interface 1
interface UserActions {
    // default method
    default void logActivity(String activity) {
        System.out.println("User activity: " + activity);
    }

}
// Interface 2
interface AdminActions {
    // default method trùng tên
    default void logActivity(String activity) {
        System.out.println("Admin activity: " + activity);
    }

}
// Class implement cả 2 interface
class SuperAdmin implements UserActions, AdminActions {

    // phải override để giải quyết Diamond Problem
    @Override
    public void logActivity(String activity) {
        // có thể chọn gọi method của interface cụ thể
        UserActions.super.logActivity(activity);
        // nếu muốn gọi cả hai cũng được
        AdminActions.super.logActivity(activity);
    }

}

public class Main {
    public static void main(String[] args) {
        SuperAdmin superAdmin = new SuperAdmin();
        // gọi method
        superAdmin.logActivity("Deleted user account");

    }

}