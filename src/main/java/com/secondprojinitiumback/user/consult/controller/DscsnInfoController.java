package com.secondprojinitiumback.user.consult.controller;

import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoListDto;
import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoSearchDto;
import com.secondprojinitiumback.user.consult.dto.common.DscsnResultDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnInfoResponseDto;
import com.secondprojinitiumback.user.consult.service.DscsnInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consult")
public class DscsnInfoController {
    private final DscsnInfoService dscsnInfoService;

    @GetMapping({"/dscsnInfo/list", "/dscsnInfo/list/{page}"})
    public ResponseEntity<?> getDscsnInfoList(
            @ModelAttribute DscsnInfoSearchDto dscsnInfoSearchDto,
            @PathVariable(required = false) Integer page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // 사용자 유형
        String userType = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .findFirst()
                .orElse("S");

        System.out.println("유저타입: " + userType);

        String serialNo = userDetails.getUsername(); // 학생이면 학번, 상담사면 사번

        // userType, serialNo을 강제로 덮어쓰기 (외부 파라미터 무시)
        dscsnInfoSearchDto.setUserType(userType);

        // 권한별 추가 필드 강제 세팅
        if ("S".equals(userType)) {
            dscsnInfoSearchDto.setStudentNo(serialNo);
        }else if("E".equals(userType)) {
            dscsnInfoSearchDto.setEmpNo(serialNo);
        }
        //관리자는 전체 조회 가능해서 별도로 처리X

        System.out.println("교원번호: " + dscsnInfoSearchDto.getEmpNo());

        // 페이지 번호 설정
        int pageNo = page != null ? page : 0;

        // 정렬 설정
        Pageable pageable = (sort != null && !sort.isBlank())
                ? PageRequest.of(pageNo, size, Sort.by(sort.split(",")[0])
                .descending())
                : PageRequest.of(pageNo, size);

        // 상담정보 조회
        Page<DscsnInfoResponseDto> dscsnInfos = dscsnInfoService.getDscsnInfo(dscsnInfoSearchDto, pageable);

        // 페이지 정보가 없을 경우 빈 리스트 반환
        DscsnInfoListDto body = DscsnInfoListDto.builder()
                .dscsnInfos(dscsnInfos)
                .build();

        return ResponseEntity.ok(body);
    }

    //--- 상담상태 변경(상담사, 교수)
    @PutMapping("/dscsnInfo/list/{dscsnInfoId}")
    public ResponseEntity<?> updateDscsnStatus(@PathVariable String dscsnInfoId, @RequestBody String status) {

        try {
            dscsnInfoService.updateDscsnStatus(dscsnInfoId, status);
            return ResponseEntity.ok("상태 수정 완료");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("입력값 오류");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상태 수정 중 오류 발생");
        }
    }

    //--- 상담결과 등록(상담사, 교수)
    @PostMapping("/dscsnInfo/list/result")
    public ResponseEntity<?> registerResult(@RequestBody DscsnResultDto dscsnResultDto) {

        try {
            dscsnInfoService.registerDscsnResult(dscsnResultDto);
            return ResponseEntity.ok("결과 등록 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결과 등록 중 에러 발생");
        }
    }

}
