package com.velsystems.ecommerce.service.review;

import com.velsystems.ecommerce.dto.request.ReviewRequestDto;
import com.velsystems.ecommerce.dto.response.ReviewResponseDto;
import com.velsystems.ecommerce.model.Review;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.model.product.Product;
import com.velsystems.ecommerce.repository.ReviewRepository;
import com.velsystems.ecommerce.repository.UserRepository;
import com.velsystems.ecommerce.repository.product.ProductRepository;
import com.velsystems.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReviewResponseDto addReview(UUID userId, ReviewRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // check if already reviewed
        reviewRepository.findByProductIdAndUserId(dto.getProductId(), userId)
                .ifPresent(r -> { throw new RuntimeException("User already reviewed this product"); });

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        Review saved = reviewRepository.save(review);
        return modelMapper.map(saved, ReviewResponseDto.class);
    }

    @Override
    public ReviewResponseDto updateReview(UUID userId, UUID reviewId, ReviewRequestDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to update this review");
        }

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return modelMapper.map(reviewRepository.save(review), ReviewResponseDto.class);
    }

    @Override
    public void deleteReview(UUID userId, UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this review");
        }
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponseDto> getProductReviews(UUID productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(r -> modelMapper.map(r, ReviewResponseDto.class))
                .toList();
    }

    @Override
    public List<ReviewResponseDto> getUserReviews(UUID userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(r -> modelMapper.map(r, ReviewResponseDto.class))
                .toList();
    }
}
