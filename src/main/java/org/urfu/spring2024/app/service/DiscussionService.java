package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.urfu.spring2024.app.repository.DiscussionRepository;
import org.urfu.spring2024.domain.Discussion;
import org.urfu.spring2024.domain.Message;

/**
 * Сервисный класс для работы с обсуждениями.
 */
@Slf4j
@Service
@AllArgsConstructor
public class DiscussionService {
    private DiscussionRepository discussionRepository;
    private MessageService messageService;

    /**
     * Сохранение в БД нового обсуждения.
     *
     * @param discussion - объект обсуждения для сохранения в БД.
     * @return - сохраненное обсуждение.
     */
    public Discussion createDiscussion(Discussion discussion) {
        if (discussion == null) {
            throw new IllegalArgumentException("Обсуждение не может быть null");
        }
        try {
            discussionRepository.save(discussion);
            log.info("Создано обсуждение с ID {}", discussion.getId());
            return discussion;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при сохранении нового обсуждения", e);
        }
    }

    /**
     * Поиск обсуждения в БД по его id.
     *
     * @param discussionId - уникальный идентификатор для поиска обсуждения.
     * @return - обсуждение с указанным id.
     */
    public Discussion getDiscussionById(long discussionId) {
        var searchedDiscussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new IllegalArgumentException("Обсуждение с ID " + discussionId + " не найдено"));
        log.debug("Обсуждение с ID {} найдено", discussionId);
        return searchedDiscussion;
    }

    /**
     * Удаление обсуждения из БД по его id.
     *
     * @param discussionId - уникальный идентификатор для поиска обсуждения.
     */
    public void deleteDiscussionById(long discussionId) {
        discussionRepository.deleteById(discussionId);
        log.info("Обсуждение с ID {} удалено", discussionId);
    }

    /**
     * Оставление сообщения в обсуждении.
     *
     * @param discussionId - уникальный идентификатор для поиска обсуждения.
     * @param messageId - уникальный идентификатор для поиска сообщения.
     */
    public void postMessage(long discussionId, long messageId) {
        Discussion discussion = getDiscussionById(discussionId);
        Message message = messageService.getMessageById(messageId);

        if (!discussion.getMessages().contains(message) && discussion.isOpen()) {
            discussion.getMessages().add(message);
            discussionRepository.save(discussion);
            log.info("Сообщение с ID {} было оставлено в обсуждении {} (ID {})", discussion.getTopic(), discussionId, messageId);
        }
    }

    /**
     * Удаление сообщения из обсуждении.
     *
     * @param discussionId - уникальный идентификатор для поиска обсуждения.
     * @param messageId - уникальный идентификатор для поиска сообщения.
     */
    public void removeMessage(long discussionId, long messageId) {
        Discussion discussion = getDiscussionById(discussionId);
        Message message = messageService.getMessageById(messageId);

        if (discussion.getMessages().contains(message)) {
            discussion.getMessages().remove(message);
            discussionRepository.save(discussion);
            log.info("Сообщение с ID {} было удалено из обсуждения {} (ID {})", discussion.getTopic(), discussionId, messageId);
        }
    }

    /**
     * Закрытие обсуждения.
     *
     * @param discussionId - уникальный идентификатор для поиска обсуждения.
     */
    public void closeDiscussion(long discussionId) {
        Discussion discussion = getDiscussionById(discussionId);
        discussion.setOpen(false);
        discussionRepository.save(discussion);
        log.info("Обсуждение с ID {} закрыто", discussionId);
    }
}
