package com.secondprojinitiumback.admin.Mileage.controller;

import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import com.secondprojinitiumback.admin.Mileage.dto.MileageItemRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.MileageItemResponseDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.MileageItemRepository;
import com.secondprojinitiumback.admin.Mileage.service.MileageItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/mileage-items")
public class MileageItemController {

    private final MileageItemService mileageItemService;

    @GetMapping("/list")
    public PageResponseDto<MileageItemResponseDto> list(
            PageRequestDto pageRequestDto,
            @RequestParam(required = false) String itemCode,
            @RequestParam(required = false) String eduNm){
        return mileageItemService.getList(pageRequestDto, itemCode, eduNm);
    }

    @PostMapping("/create")
    public ResponseEntity<MileageItemResponseDto> create(@RequestBody MileageItemRequestDto dto) {
        return ResponseEntity.ok(mileageItemService.register(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MileageItemResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mileageItemService.findById(id));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteAll(@RequestBody List<Long> ids) {
        mileageItemService.deleteAll(ids);
        return ResponseEntity.noContent().build();
    }
}
