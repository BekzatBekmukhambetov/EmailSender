package kz.beka.emailsenderrabbitmq.service;

import kz.beka.emailsenderrabbitmq.model.RequestDto;
import kz.beka.emailsenderrabbitmq.model.UserDetailsDto;

import java.util.List;

public interface UsersService {
    public void registerUser(RequestDto requestDto);
    public UserDetailsDto getUserByEmail(String email);
    public List<UserDetailsDto> getAllUsers();
    public boolean updateUser(RequestDto requestDto);
    public boolean deleteUser(String email);
}
