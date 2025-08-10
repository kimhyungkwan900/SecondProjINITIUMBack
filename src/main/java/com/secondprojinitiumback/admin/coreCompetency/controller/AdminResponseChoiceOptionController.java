package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.ResponseChoiceOptionDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminResponseChoiceOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/questions")
@RequiredArgsConstructor
public class AdminResponseChoiceOptionController {

    private final AdminResponseChoiceOptionService responseService;

    @PostMapping("/{questionId}/options")
    public void addOptions(@PathVariable Long questionId, @RequestBody List<ResponseChoiceOptionDto> dtos) {
        responseService.createOptions(questionId, dtos);
    }

    @PutMapping("/options/{optionId}")
    public void updateOption(@PathVariable Long optionId, @RequestBody ResponseChoiceOptionDto dto) {
        responseService.updateOption(optionId, dto);
    }

    @DeleteMapping("/options/{optionId}")
    public void deleteOption(@PathVariable Long optionId) {
        responseService.deleteOption(optionId);
    }

    @GetMapping("/{questionId}/options")
    public List<ResponseChoiceOptionDto> getAllOptions(@PathVariable Long questionId) {
        return responseService.getOptionsByQuestionId(questionId);
    }
}
