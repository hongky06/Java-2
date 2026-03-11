package Baitap06;

import java.time.LocalDate;
import java.util.List;

public class ProfileService {
    public User updateProfile(User existingUser, UserProfile newProfile, List<User> allUsers) {
        if (newProfile.dob.isAfter(LocalDate.now())) {
            return null;
        }
        if (allUsers != null) {
            for (User otherUser : allUsers) {
                if (!otherUser.id.equals(existingUser.id) &&
                        otherUser.profile.email.equalsIgnoreCase(newProfile.email)) {
                    return null;
                }
            }
        }
        existingUser.profile = newProfile;
        return existingUser;
    }
}