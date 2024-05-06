package org.urfu.spring2024.app.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.urfu.spring2024.app.repository.DiscussionRepository;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Discussion;
import org.urfu.spring2024.domain.Message;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class DiscussionServiceTest {
    @Mock
    private DiscussionRepository discussionRepository;
    @Mock
    private MessageService messageService;

    @InjectMocks
    private DiscussionService discussionService;

    @Test
    public void testCreateDiscussion() {
        Discussion discussion = Discussion.builder().id(1L).boardGame(new BoardGame()).build();

        when(discussionRepository.save(discussion)).thenReturn(discussion);
        Discussion createdDiscussion = discussionService.createDiscussion(discussion);

        assertNotNull(createdDiscussion.getId());
    }

    @Test
    public void testDiscussionNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> discussionService.createDiscussion(null));
    }

    @Test
    public void testFindDiscussionByID() {
        Discussion discussion = Discussion.builder().id(1L).boardGame(new BoardGame()).build();

        when(discussionRepository.findById(1L)).thenReturn(Optional.of(discussion));
        Discussion searchedDiscussion = discussionService.getDiscussionById(1L);

        assertNotNull(searchedDiscussion);
        assertEquals(1L, searchedDiscussion.getId());
    }

    @Test
    public void testDiscussionNotFoundByID() {
        when(discussionRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> discussionService.getDiscussionById(3L));
    }

    @Test
    public void testDeleteDiscussion() {
        Discussion discussion = Discussion.builder().id(3L).boardGame(new BoardGame()).build();

        when(discussionRepository.findById(3L)).thenReturn(Optional.of(discussion));
        discussionService.deleteDiscussionById(3L);

        verify(discussionRepository, times(1)).deleteById(3L);
    }

    @Test
    public void testPostMessage() {
        Discussion discussion = Discussion.builder().id(1L).boardGame(new BoardGame()).isOpen(true).messages(new ArrayList<>()).build();
        Message message = Message.builder().id(1L).build();

        when(discussionRepository.findById(1L)).thenReturn(Optional.of(discussion));
        when(messageService.getMessageById(1L)).thenReturn(message);

        discussionService.postMessage(1L, 1L);

        assertEquals(1, discussion.getMessages().size());
    }

    @Test
    public void testRemoveMessage() {
        Discussion discussion = Discussion.builder().id(1L).boardGame(new BoardGame()).isOpen(true).messages(new ArrayList<>()).build();
        Message message = Message.builder().id(1L).build();

        when(discussionRepository.findById(1L)).thenReturn(Optional.of(discussion));
        when(messageService.getMessageById(1L)).thenReturn(message);

        discussionService.postMessage(1L, 1L);
        assertEquals(1, discussion.getMessages().size());

        discussionService.removeMessage(1L, 1L);
        assertEquals(0, discussion.getMessages().size());
    }

    @Test
    public void testDiscussionClosed() {
        Discussion discussion = Discussion.builder().id(1L).boardGame(new BoardGame()).isOpen(true).messages(new ArrayList<>()).build();
        Message message = Message.builder().id(1L).build();

        when(discussionRepository.findById(1L)).thenReturn(Optional.of(discussion));
        when(messageService.getMessageById(1L)).thenReturn(message);

        discussionService.closeDiscussion(1L);
        discussionService.postMessage(1L, 1L);

        assertEquals(0, discussion.getMessages().size());
    }
}
