package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.urfu.spring2024.app.repository.ReviewRepository;
import org.urfu.spring2024.domain.Category;
import org.urfu.spring2024.domain.Review;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * Сохранение в БД нового отзыва.
     *
     * @param review - объект отзыва для сохранения в БД.
     * @return - сохраненный отзыв.
     */
    public Review createReview(Review review) {
        if (review == null) {
            throw new IllegalArgumentException("Отзыв не может быть null");
        }
        try {
            reviewRepository.save(review);
            log.info("Создан отзыв с ID {}", review.getId());
            return review;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при сохранении нового отзыва", e);
        }
    }

    /**
     * Поиск отзыва в БД по его id.
     *
     * @param reviewId - уникальный идентификатор для поиска отзыва.
     * @return - отзыв с указанным id.
     */
    public Review getReviewById(long reviewId) {
        Review searchedReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Отзыв с ID " + reviewId + " не найден"));
        log.info("Отзыв с ID {} найден", reviewId);
        return searchedReview;
    }

    /**
     * Удаление отзыва из БД по его id.
     *
     * @param reviewId - уникальный идентификатор для поиска отзыва.
     */
    public void deleteCategoryById(long reviewId) {
        Review searchedReview = getReviewById(reviewId);
        if (searchedReview == null) {
            throw new IllegalArgumentException("Отзыв с ID " + reviewId + " не найден");
        } else {
            reviewRepository.deleteById(reviewId);
            log.info("Отзыв с ID {} удален", reviewId);
        }
    }

    //TODO
}
