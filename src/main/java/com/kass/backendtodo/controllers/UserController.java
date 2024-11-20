package com.kass.backendtodo.controllers;

import com.kass.backendtodo.models.UserModel;
import com.kass.backendtodo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAll();
    }


    @PostMapping("/pro")
    public ResponseEntity<?> createAd(@Valid @RequestBody UserModel user, BindingResult bindingResult ) {
        if (bindingResult.hasFieldErrors()) {
            return validation(bindingResult);
        }
        user.setAdmin(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping()
    public ResponseEntity<?> createUs(@Valid @RequestBody UserModel user, BindingResult bindingResult ) {
        if (bindingResult.hasFieldErrors()) {
            return validation(bindingResult);
        }
        user.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    private ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), "The field "+ error.getField()+
                        " "+error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
