package kz.beka.emailsenderrabbitmq.controller;

import kz.beka.emailsenderrabbitmq.model.RequestDto;
import kz.beka.emailsenderrabbitmq.model.ResponseDto;
import kz.beka.emailsenderrabbitmq.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody RequestDto requestDto){
        usersService.registerUser(requestDto);
        return  new ResponseEntity<>(ResponseDto.builder()
                .statusCode(HttpStatus.CREATED.toString())
                .statusMsg("GOOD")
                .build(), HttpStatus.CREATED);
    }
}
