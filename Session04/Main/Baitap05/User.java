package Baitap05;
enum Role {
    ADMIN, MODERATOR, USER
}
enum Action {
    DELETE_USER, LOCK_USER, VIEW_PROFILE
}
class User {
    private String username;
    private Role role;

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public Role getRole() { return role; }
}