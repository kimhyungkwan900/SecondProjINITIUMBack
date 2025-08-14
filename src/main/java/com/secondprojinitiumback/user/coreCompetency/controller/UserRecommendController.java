package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserRecommendProgramDto;
import com.secondprojinitiumback.user.coreCompetency.service.UserRecommendationService;
import lombok.RequiredArgsConstructor;
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
}
