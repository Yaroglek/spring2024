package org.urfu.spring2024.extern.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urfu.spring2024.app.service.UserService;
import org.urfu.spring2024.domain.User;
import org.urfu.spring2024.extern.assembler.UserAssembler;
import org.urfu.spring2024.extern.dto.UserDTO;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private UserAssembler userAssembler;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(userAssembler.toModel(user));
    }

    //TODO остальные эндпоинты
}
