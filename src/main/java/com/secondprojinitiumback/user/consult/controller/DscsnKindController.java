package com.secondprojinitiumback.user.consult.controller;

import com.secondprojinitiumback.user.consult.dto.common.DscsnKindDto;
import com.secondprojinitiumback.user.consult.dto.common.DscsnKindListDto;
import com.secondprojinitiumback.user.consult.service.DscsnKindService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DscsnKindController {
    private final DscsnKindService dscsnKindService;

    //--- 상담항목 추가
    @PostMapping("/admin/consult/dscsnkind/new")
    public ResponseEntity<?> newDscsnKind(@RequestBody DscsnKindDto dscsnKindDto) {
        try{
            dscsnKindService.saveDscsnKind(dscsnKindDto);
            return ResponseEntity.ok("항목 추가 완료");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("항목 등록 중 에러 발생");
        }
    }

    //--- 상담신청 페이지 상담항목 가져오기
    @GetMapping("/consult/dscsnkind/get/{prefix}")
    public ResponseEntity<?> getDscsnKindForConsult(@PathVariable String prefix) {
        List<DscsnKindDto> dscsnKinds = dscsnKindService.getDscsnKindByPrefix(prefix);

        return ResponseEntity.ok(dscsnKinds);
    }

    //--- 상담항목 조회
    @GetMapping({"/admin/consult/dscsnkind/", "/admin/consult/dscsnkind/{page}"})
    public ResponseEntity<?> getDscsnKind(@ModelAttribute DscsnKindDto dscsnKindDto, @PathVariable Integer page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<DscsnKindDto> dscsnKinds = dscsnKindService.getDscsnKind(dscsnKindDto, pageable);

        DscsnKindListDto dscsnKindListDto = DscsnKindListDto.builder()
                .dscsnKinds(dscsnKinds)
                .maxPage(dscsnKinds.getTotalPages())
                .totalPage(dscsnKinds.getTotalPages())
                .build();

        return ResponseEntity.ok(dscsnKindListDto);
    }

    //--- 상담항목 수정
    @PutMapping("/admin/consult/dscsnkind/update")
    public ResponseEntity<?> updateDscsnKind(@RequestBody DscsnKindDto dscsnKindDto) {
        try {
            dscsnKindService.updateDscsnKind(dscsnKindDto);
            return ResponseEntity.ok("항목 수정 완료");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("입력값 오류");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 중 오류가 발생하였습니다.");
        }
    }

    //--- 상담항목 삭제
    @DeleteMapping("/admin/consult/dscsnkind/delete")
    public ResponseEntity<?> deleteDscsnKind(@RequestBody List<String> dscsnKindIds) {
        try {
            dscsnKindService.deleteDscsnKind(dscsnKindIds);
            return ResponseEntity.ok("항목 삭제 완료");
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("선택한 항목을 찾을 수 없습니다.");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
        }
    }
}
