package kz.beka.emailsenderrabbitmq.mapper;

import kz.beka.emailsenderrabbitmq.entity.Users;
import kz.beka.emailsenderrabbitmq.model.RequestDto;
import kz.beka.emailsenderrabbitmq.model.UserDetailsDto;

public class UsersMapper {
    public static Users mapToUser(Users user, RequestDto requestDto ){
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        return user;
    }
    public static UserDetailsDto mapToUserDetails(UserDetailsDto userDetailsDto, Users user){
        userDetailsDto.setFirstName(user.getFirstName());
        userDetailsDto.setLastName(user.getLastName());
        userDetailsDto.setEmail(user.getEmail());
        return  userDetailsDto;
    }

}
