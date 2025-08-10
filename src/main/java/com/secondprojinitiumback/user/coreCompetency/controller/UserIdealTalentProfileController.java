package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserIdealTalentProfileDto;
import com.secondprojinitiumback.user.coreCompetency.service.IdealTalentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/ideal-talent-profile")
public class UserIdealTalentProfileController {

    private final IdealTalentProfileService idealTalentProfileService;

    @GetMapping("/tree")
    public ResponseEntity<List<UserIdealTalentProfileDto>> getIdealTalentTree() {
        List<UserIdealTalentProfileDto> tree = idealTalentProfileService.getIdealTalentStructure();
        return ResponseEntity.ok(tree);
    }
}
