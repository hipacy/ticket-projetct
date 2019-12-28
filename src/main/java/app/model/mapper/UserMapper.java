package app.model.mapper;

import app.model.Users;
import lombok.experimental.UtilityClass;

import javax.validation.constraints.NotNull;

@UtilityClass
public class UserMapper {
    public static void mapEditedUser(@NotNull Users userData, @NotNull Users currentUser){
        currentUser.setJobTitle(userData.getJobTitle());
        currentUser.setFirstName(userData.getFirstName());
        currentUser.setLastName(userData.getLastName());
        currentUser.setEmail(userData.getEmail());
    }
}
