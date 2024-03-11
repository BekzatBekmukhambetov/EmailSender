package kz.beka.emailsenderrabbitmq.service;

import kz.beka.emailsenderrabbitmq.constants.UsersConstant;
import kz.beka.emailsenderrabbitmq.entity.Users;
import kz.beka.emailsenderrabbitmq.exception.ResourceNotFoundException;
import kz.beka.emailsenderrabbitmq.exception.UserAlreadyExistsException;
import kz.beka.emailsenderrabbitmq.mapper.UsersMapper;
import kz.beka.emailsenderrabbitmq.model.EmailDetails;
import kz.beka.emailsenderrabbitmq.model.RequestDto;
import kz.beka.emailsenderrabbitmq.model.UserDetailsDto;
import kz.beka.emailsenderrabbitmq.repository.UsersRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements  UsersService{
    private final UsersRepository usersRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("$rabbitmq.exchange.email.name")
    private String emailExchange;
    @Value("$rabbitmq.binding.email.name")
    private String emailRoutingKey;

    public UsersServiceImpl(UsersRepository usersRepository, RabbitTemplate rabbitTemplate) {
        this.usersRepository = usersRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void registerUser(RequestDto requestDto) {
        if(usersRepository.existsByEmail(requestDto.getEmail())){
            throw new UserAlreadyExistsException(UsersConstant.USER_ALREADY_EXIST);
        }
        Users user = UsersMapper.mapToUser(new Users(),requestDto);
        usersRepository.save(user);
        rabbitTemplate.convertAndSend(
                emailExchange,
                emailRoutingKey,
                EmailDetails.builder()
                        .messageBody("Registration successful with mail id: "+ requestDto.getEmail())
                        .recipient(requestDto.getEmail())
                        .subject("SUCCESSFUL REGISTRATION")
                        .build()
        );

    }

    @Override
    public UserDetailsDto getUserByEmail(String email) {
        if (!usersRepository.existsByEmail(email)) {
            throw  new ResourceNotFoundException(UsersConstant.USER_NOT_FOUNDED);
        }
        Users user =usersRepository.findByEmail(email);
        return UsersMapper.mapToUserDetails(new UserDetailsDto(),user);
    }

    @Override
    public List<UserDetailsDto> getAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        if(usersList.isEmpty()){
            throw new ResourceNotFoundException(UsersConstant.USER_NOT_FOUNDED);
        }
        List<UserDetailsDto> userDetailsDtoList = new ArrayList<>();

        usersList.forEach(users -> {
            userDetailsDtoList.add(UsersMapper.mapToUserDetails(new UserDetailsDto(),users));
        });
        return userDetailsDtoList;
    }

    @Override
    public boolean updateUser(RequestDto requestDto) {
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }
}
