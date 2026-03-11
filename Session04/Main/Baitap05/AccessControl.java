package Baitap05;

public class AccessControl {

    public boolean canPerformAction(User user, Action action) {
        if (user == null || action == null) return false;

        Role role = user.getRole();

        switch (role) {
            case ADMIN:
                return true;

            case MODERATOR:
                return action == Action.LOCK_USER || action == Action.VIEW_PROFILE;

            case USER:
                return action == Action.VIEW_PROFILE;

            default:
                return false;
        }
    }
}