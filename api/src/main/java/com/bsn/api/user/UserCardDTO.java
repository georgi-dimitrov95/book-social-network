package com.bsn.api.user;

import com.bsn.api.file.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCardDTO {

    private String firstname;

    private String lastname;

    private String location;

    private byte[] avatar;

    public UserCardDTO(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.location = user.getLocation();
        this.avatar = FileUtils.readFileFromLocation(user.getAvatarPath());
    }
}
