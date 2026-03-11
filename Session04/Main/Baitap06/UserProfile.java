package Baitap06;

import java.time.LocalDate;
import java.util.List;

class UserProfile {
    String email;
    LocalDate dob;

    public UserProfile(String email, LocalDate dob) {
        this.email = email;
        this.dob = dob;
    }
}

class User {
    String id;
    UserProfile profile;

    public User(String id, UserProfile profile) {
        this.id = id;
        this.profile = profile;
    }
}