//package com.secondprojinitiumback.admin.coreCompetency.controller;
//
//import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
//import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/ideal-talent-profile")
//public class IdealTalentProfileController {
//
//    private final IdealTalentProfileRepository idealTalentProfileRepository;
//
//    @GetMapping("/all")
//    public ResponseEntity<List<IdealTalentProfile>> getAllProfiles() {
//        List<IdealTalentProfile> profiles = idealTalentProfileRepository.findAll();
//        return ResponseEntity.ok(profiles);
//    }
//}
