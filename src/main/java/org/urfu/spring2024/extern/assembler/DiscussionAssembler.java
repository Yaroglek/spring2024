package org.urfu.spring2024.extern.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.urfu.spring2024.domain.Discussion;
import org.urfu.spring2024.domain.Message;
import org.urfu.spring2024.extern.controller.DiscussionController;
import org.urfu.spring2024.extern.dto.DiscussionDTO;

import java.util.stream.Collectors;

@Component
public class DiscussionAssembler extends RepresentationModelAssemblerSupport<Discussion, DiscussionDTO> {
    public DiscussionAssembler() {
        super(DiscussionController.class, DiscussionDTO.class);
    }

    @Override
    public DiscussionDTO toModel(Discussion discussion) {
        DiscussionDTO discussionDTO = instantiateModel(discussion);

        discussionDTO.setId(discussion.getId());
        discussionDTO.setTopicStarterId(discussion.getTopicStarter().getId());
        discussionDTO.setBoardGameId(discussion.getBoardGame().getId());
        discussionDTO.setCreatedDttm(discussion.getCreatedDttm());
        discussionDTO.setTopic(discussion.getTopic());
        discussionDTO.setOpen(discussion.isOpen());
        discussionDTO.setMessagesIds(discussion.getMessages().stream()
                .map(Message::getId)
                .collect(Collectors.toList()));

        return discussionDTO;
    }
}