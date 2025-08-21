package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.ReviewRequestDto;
import com.velsystems.ecommerce.dto.response.ReviewResponseDto;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "Endpoints for managing product reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Add a new review", description = "A account can add a review for a product. Each account can review a product only once.")
    @PostMapping
    public ResponseEntity<ApiResponse> addReview(
            @RequestParam UUID accountId,
            @RequestBody ReviewRequestDto dto) {
        ReviewResponseDto review = reviewService.addReview(accountId, dto);
        return ResponseEntity.ok(new ApiResponse("Review added successfully", review));
    }

    @Operation(summary = "Update an existing review", description = "Account can update their own review by ID.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateReview(
            @RequestParam UUID accountId,
            @PathVariable UUID id,
            @RequestBody ReviewRequestDto dto) {
        ReviewResponseDto review = reviewService.updateReview(accountId, id, dto);
        return ResponseEntity.ok(new ApiResponse("Review updated successfully", review));
    }

    @Operation(summary = "Delete a review", description = "Account can delete their own review.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReview(
            @RequestParam UUID accountId,
            @PathVariable UUID id) {
        reviewService.deleteReview(accountId, id);
        return ResponseEntity.ok(new ApiResponse("Review deleted successfully", null));
    }

    @Operation(summary = "Get all reviews for a product", description = "Fetch all reviews for a given product by productId.")
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductReviews(@PathVariable UUID productId) {
        List<ReviewResponseDto> reviews = reviewService.getProductReviews(productId);
        return ResponseEntity.ok(new ApiResponse("Product reviews fetched", reviews));
    }

    @Operation(summary = "Get all reviews by a account", description = "Fetch all reviews submitted by a given account.")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse> getAccountReviews(@PathVariable UUID accountId) {
        List<ReviewResponseDto> reviews = reviewService.getAccountReviews(accountId);
        return ResponseEntity.ok(new ApiResponse("Account reviews fetched", reviews));
    }
}