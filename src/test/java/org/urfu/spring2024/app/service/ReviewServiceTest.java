package org.urfu.spring2024.app.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.urfu.spring2024.app.repository.ReviewRepository;
import org.urfu.spring2024.domain.Review;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testCreateReview() {
        Review review = Review.builder().id(1L).build();

        when(reviewRepository.save(review)).thenReturn(review);
        Review createdReview = reviewService.createReview(review);

        assertNotNull(createdReview.getId());
    }

    @Test
    public void testReviewNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(null));
    }

    @Test
    public void testGetReviewById() {
        Review review = Review.builder().id(1L).build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        Review searchedReview = reviewService.getReviewById(1L);

        assertNotNull(searchedReview);
        assertEquals(1L, searchedReview.getId());
    }

    @Test
    public void testReviewNotFoundByID() {
        when(reviewRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reviewService.getReviewById(5L));
    }

    @Test
    public void testDeleteManufacturerByID() {
        Review review = Review.builder().id(2L).build();

        when(reviewRepository.findById(2L)).thenReturn(Optional.of(review));
        reviewService.deleteCategoryById(2L);

        verify(reviewRepository, times(1)).deleteById(2L);
    }
}
