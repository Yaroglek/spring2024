package org.urfu.spring2024.extern.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urfu.spring2024.app.service.ReviewService;
import org.urfu.spring2024.app.service.UserService;
import org.urfu.spring2024.domain.User;
import org.urfu.spring2024.extern.assembler.UserAssembler;
import org.urfu.spring2024.extern.dto.UserDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private UserAssembler userAssembler;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User newUser = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .additionalInfo(userDTO.getAdditionalInfo())
                .reviews(new ArrayList<>())
                .trackedGames(new ArrayList<>())
                .eventsAttended(new ArrayList<>())
                .build();

        userService.createUser(newUser);

        return new ResponseEntity<>(userAssembler.toModel(newUser), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userAssembler.toModel(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/trackedGames/{gameId}")
    public ResponseEntity<Void> trackGame(@PathVariable long userId, @PathVariable long gameId) {
        userService.trackGame(userId, gameId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/trackedGames/{gameId}")
    public ResponseEntity<Void> unTrackGame(@PathVariable long userId, @PathVariable long gameId) {
        userService.unTrackGame(userId, gameId);
        return ResponseEntity.ok().build();
    }

    //TODO
}
