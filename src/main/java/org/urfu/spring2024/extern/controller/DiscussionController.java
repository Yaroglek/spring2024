package org.urfu.spring2024.extern.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urfu.spring2024.app.service.BoardGameService;
import org.urfu.spring2024.app.service.DiscussionService;
import org.urfu.spring2024.app.service.UserService;
import org.urfu.spring2024.domain.Discussion;
import org.urfu.spring2024.extern.assembler.DiscussionAssembler;
import org.urfu.spring2024.extern.dto.DiscussionDTO;

import java.util.ArrayList;

@RestController
@RequestMapping("/discussions")
@AllArgsConstructor
public class DiscussionController {
    private DiscussionService discussionService;
    private DiscussionAssembler discussionAssembler;
    private UserService userService;
    private BoardGameService boardGameService;

    @PostMapping
    public ResponseEntity<DiscussionDTO> createDiscussion(@RequestBody @Valid DiscussionDTO discussionDTO) {
        Discussion newDiscussion = Discussion.builder()
                .topicStarter(userService.getUserById(discussionDTO.getTopicStarterId()))
                .boardGame(boardGameService.getBoardGameById(discussionDTO.getBoardGameId()))
                .createdDttm(discussionDTO.getCreatedDttm())
                .topic(discussionDTO.getTopic())
                .isOpen(discussionDTO.isOpen())
                .messages(new ArrayList<>())
                .build();

        discussionService.createDiscussion(newDiscussion);

        return new ResponseEntity<>(discussionAssembler.toModel(newDiscussion), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscussionDTO> getDiscussionById(@PathVariable long id) {
        Discussion discussion = discussionService.getDiscussionById(id);
        return ResponseEntity.ok(discussionAssembler.toModel(discussion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        discussionService.deleteDiscussionById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{discussionId}/messages/{messageId}")
    public ResponseEntity<Void> postMessage(@PathVariable long discussionId, @PathVariable long messageId) {
        discussionService.postMessage(discussionId, messageId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{discussionId}/messages/{messageId}")
    public ResponseEntity<Void> removeMessage(@PathVariable long discussionId, @PathVariable long messageId) {
        discussionService.removeMessage(discussionId, messageId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{discussionId}/isOpen")
    public ResponseEntity<Void> closeDiscussion(@PathVariable long discussionId) {
        discussionService.closeDiscussion(discussionId);
        return ResponseEntity.ok().build();
    }
}