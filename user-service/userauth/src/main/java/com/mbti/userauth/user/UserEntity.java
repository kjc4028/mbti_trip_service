package com.mbti.userauth.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document("user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    private String _id;

    private String userId;
    
    private String userPw;

    public UserEntity(UserRequestDto dto){
        this._id = dto.get_id();
        this.userId = dto.getUserId();
        this.userPw = dto.getUserPw();
    }

}
