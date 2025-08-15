package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserRecommendProgramDto;
import com.secondprojinitiumback.user.coreCompetency.service.UserRecommendationService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/recommend")
public class UserRecommendController {

    private final UserRecommendationService service;

    @GetMapping("/{assessmentNo}/{studentNo}")
    public List<UserRecommendProgramDto> generate(@PathVariable String assessmentNo, @PathVariable String studentNo,
                                                  @RequestParam(defaultValue = "15") int limit) {
        return service.findRecommendedPrograms(assessmentNo, studentNo, limit);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_STUDENT') and #studentNo == authentication.name or hasAuthority('ROLE_ADMIN')")
    public Page<UserRecommendProgramDto> getRecommended(
            @RequestParam @NotBlank String assessmentNo,
            @RequestParam @Pattern(regexp = "^[0-9A-Za-z]{10}$") String studentNo,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "12") @Min(1) @Max(50) int size,
            @RequestParam(defaultValue = "LATEST") String sort // or ENUM
    ) {
        return service.findRecommendedPrograms(assessmentNo, studentNo, page, size, sort);
    }
}
