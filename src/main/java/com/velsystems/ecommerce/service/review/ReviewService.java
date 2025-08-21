package com.velsystems.ecommerce.service;


import com.velsystems.ecommerce.dto.request.ReviewRequestDto;
import com.velsystems.ecommerce.dto.response.ReviewResponseDto;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponseDto addReview(UUID accountId, ReviewRequestDto dto);
    ReviewResponseDto updateReview(UUID accountId, UUID reviewId, ReviewRequestDto dto);
    void deleteReview(UUID accountId, UUID reviewId);
    List<ReviewResponseDto> getProductReviews(UUID productId);
    List<ReviewResponseDto> getAccountReviews(UUID accountId);
}
