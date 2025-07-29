package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.dto.DscsnApplyDto;
import com.secondprojinitiumback.user.consult.repository.DscsnApplyRepoistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnApplyService {
    private final DscsnApplyRepoistory dscsnApplyRepoistory;

    //---지도교수 상담신청
    public void applyAcademicConsultation(DscsnApplyDto dscsnApplyDto) {

    }
}
