package com.secondprojinitiumback.admin.extracurricular.controller;


import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularAdminSurveyResponseDTO;
import com.secondprojinitiumback.user.extracurricular.service.ExtracurricularSurveyResponseService;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extracurricular/survey")
@RequiredArgsConstructor
public class ExtracurricularSurveyController {

    private final ExtracurricularSurveyResponseService extracurricularSurveyResponseService;

    @GetMapping("/list")
    public Page<ExtracurricularAdminSurveyResponseDTO> getSurveyList(
            @RequestParam Long eduMngId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return extracurricularSurveyResponseService.getSurveyResponse(eduMngId, pageable);
    }

}
