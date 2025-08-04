-- 학과 코드
INSERT INTO scsbjt (SCSBJT_NO, SCSBJT_NM, CD, CD_SE) VALUES
                                                         ('MGT01', '경영학과', 'SSCI', 'SCSBJT'),
                                                         ('CSE01', '컴퓨터공학과', 'ENG', 'SCSBJT'),
                                                         ('VDS01', '시각디자인학과', 'ART', 'SCSBJT'),
                                                         ('KOR01', '국어국문학과', 'HUM', 'SCSBJT'),
                                                         ('MAT01', '수학과', 'NSCI', 'SCSBJT'),
                                                         ('NUR01', '간호학과', 'HLTH', 'SCSBJT');

-- 부서 코드
INSERT INTO SCSBJT (SCSBJT_NO, SCSBJT_NM, CD, CD_SE)
VALUES
    ('PLA01', '기획부서', 'PLAN', 'DEPT'),
    ('SAF01', '학생부서', 'SAFF', 'DEPT');
