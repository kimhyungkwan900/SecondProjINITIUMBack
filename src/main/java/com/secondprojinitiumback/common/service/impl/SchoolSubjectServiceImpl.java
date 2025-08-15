package com.secondprojinitiumback.common.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.common.domain.QSchoolSubject;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.common.service.SchoolSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolSubjectServiceImpl implements SchoolSubjectService {

    private final JPAQueryFactory queryFactory;
    private final SchoolSubjectRepository repository;

    @Override
    public Page<SchoolSubject> search(String q, String divisionCodeSe, String divisionCode, Pageable pageable) {
        QSchoolSubject s = QSchoolSubject.schoolSubject;

        BooleanBuilder where = new BooleanBuilder();

        if (q != null && !q.isBlank()) {
            where.and(
                    s.subjectName.containsIgnoreCase(q)
                            .or(s.subjectCode.containsIgnoreCase(q))
            );
        }
        if (divisionCodeSe != null && !divisionCodeSe.isBlank()) {
            where.and(s.deptDivision.id.codeGroup.eq(divisionCodeSe));
        }
        if (divisionCode != null && !divisionCode.isBlank()) {
            where.and(s.deptDivision.id.code.eq(divisionCode));
        }

        // base query
        var base = queryFactory
                .selectFrom(s)
                .leftJoin(s.deptDivision).fetchJoin()
                .where(where);

        // count
        long total = queryFactory
                .select(s.count())
                .from(s)
                .leftJoin(s.deptDivision)
                .where(where)
                .fetchOne();

        // content
        List<SchoolSubject> content = base
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(s.subjectName.asc())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public SchoolSubject findByCode(String subjectCode) {
        return repository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHOOL_SUBJECT_NOT_FOUND));
    }
}