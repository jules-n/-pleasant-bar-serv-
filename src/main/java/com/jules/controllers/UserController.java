package com.jules.controllers;

import com.jules.dtos.UserPersonalDataDTO;
import com.jules.dtos.UserRightsDTO;
import com.jules.models.User;
import com.jules.services.users.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Setter(onMethod_ = {@Autowired})
    private UserService service;

    @PostMapping("register")
    public ResponseEntity registration(@RequestBody UserPersonalDataDTO dto) {
        var user = new User(dto.getUsername(), dto.getPassword(), null);
        var result = service.save(user);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PutMapping("add-rights")
    public ResponseEntity addRights(@RequestBody UserRightsDTO dto) {
        var result = service.addRights(dto.getUsername(), dto.getRole());
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PutMapping("update-profile")
    public ResponseEntity addRights(Authentication authentication, @RequestBody UserPersonalDataDTO dto) {
        var user = new User(dto.getUsername(), dto.getPassword(), null);
        var username = ((UserDetails)authentication.getPrincipal()).getUsername();
        var result = service.update(username, user);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
