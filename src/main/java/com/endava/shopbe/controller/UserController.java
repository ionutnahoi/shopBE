package com.endava.shopbe.controller;

import com.endava.shopbe.email.SendEmail;
import com.endava.shopbe.entity.User;
import com.endava.shopbe.repository.RoleRepo;
import com.endava.shopbe.service.RoleService;
import com.endava.shopbe.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    private final RoleRepo roleRepo;

    public final RoleService roleService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> findAll() {
        try {
            List<User> userList = userService.findAllUsers();
            if (userList != null) {
                return new ResponseEntity<>(userList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public <S extends User> S save(@Valid @RequestBody S user) {
        try {
//            SendEmail sendEmail = new SendEmail();
//            sendEmail.RegisterSendEmail(user.getEmail());
            user.setRole(roleService.getRoleByName("USER"));
            return userService.save(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/id")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        try {
            User account = userService.findById(id);
            if (account != null) {
                return new ResponseEntity<>(account, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> deleteById(Long aLong) {
        try {
            User account = userService.findById(aLong);
            if (account != null) {
                userService.deleteById(aLong);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void delete(User entity) {
        userService.delete(entity);
    }
}
