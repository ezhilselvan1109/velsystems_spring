package com.velsystems.ecommerce.service.review;

import com.velsystems.ecommerce.dto.request.ReviewRequestDto;
import com.velsystems.ecommerce.dto.response.ReviewResponseDto;
import com.velsystems.ecommerce.model.Review;
import com.velsystems.ecommerce.model.Account;
import com.velsystems.ecommerce.model.product.Product;
import com.velsystems.ecommerce.repository.ReviewRepository;
import com.velsystems.ecommerce.repository.AccountRepository;
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
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReviewResponseDto addReview(UUID accountId, ReviewRequestDto dto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // check if already reviewed
        reviewRepository.findByProductIdAndAccountId(dto.getProductId(), accountId)
                .ifPresent(r -> { throw new RuntimeException("Account already reviewed this product"); });

        Review review = Review.builder()
                .account(account)
                .product(product)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        Review saved = reviewRepository.save(review);
        return modelMapper.map(saved, ReviewResponseDto.class);
    }

    @Override
    public ReviewResponseDto updateReview(UUID accountId, UUID reviewId, ReviewRequestDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getAccount().getId().equals(accountId)) {
            throw new RuntimeException("Not authorized to update this review");
        }

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return modelMapper.map(reviewRepository.save(review), ReviewResponseDto.class);
    }

    @Override
    public void deleteReview(UUID accountId, UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getAccount().getId().equals(accountId)) {
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
    public List<ReviewResponseDto> getAccountReviews(UUID accountId) {
        return reviewRepository.findByAccountId(accountId).stream()
                .map(r -> modelMapper.map(r, ReviewResponseDto.class))
                .toList();
    }
}
