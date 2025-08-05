package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.IdealTalentProfileDto;
import com.secondprojinitiumback.user.coreCompetency.service.IdealTalentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ideal-talent-profile")
public class IdealTalentProfileController {

    private final IdealTalentProfileService idealTalentProfileService;

    @GetMapping("/tree")
    public ResponseEntity<List<IdealTalentProfileDto>> getIdealTalentTree() {
        List<IdealTalentProfileDto> tree = idealTalentProfileService.getIdealTalentStructure();
        return ResponseEntity.ok(tree);
    }
}
