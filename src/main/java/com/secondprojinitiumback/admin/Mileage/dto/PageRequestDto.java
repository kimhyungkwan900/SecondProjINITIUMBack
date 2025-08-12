package com.secondprojinitiumback.admin.Mileage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {

    @Builder.Default
    private int page = 1; // 기본 1페이지

    @Builder.Default
    private int size = 10; // 기본 페이지 크기

    private String sortField = "id"; // 정렬 필드 기본값
    private Sort.Direction sortDirection = Sort.Direction.DESC; // 정렬 방향 기본값

    // JPA Pageable 변환
    public Pageable toPageable() {
        return PageRequest.of(
                Math.max(0, this.page - 1), // JPA는 0부터 시작
                this.size,
                Sort.by(this.sortDirection, this.sortField)
        );
    }
}

