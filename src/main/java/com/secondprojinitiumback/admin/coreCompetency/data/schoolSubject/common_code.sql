-- 학과 코드 예시
INSERT INTO COMM_CODE (CD_SE, CD, CD_NM, USE_YN)
VALUES
    ('SCSBJT', 'SSCI', '사회과학계열', 'Y'),
    ('SCSBJT', 'ENG', '공학계열', 'Y'),
    ('SCSBJT', 'ART', '예술디자인계열', 'Y'),
    ('SCSBJT', 'HUM', '인문계열', 'Y'),
    ('SCSBJT', 'NSCI', '자연과학계열', 'Y'),
    ('SCSBJT', 'HLTH', '보건계열', 'Y');

-- 부서 코드 예시
INSERT INTO COMM_CODE (CD_SE, CD, CD_NM, USE_YN)
VALUES
    ('DEPT', 'PLAN', '기획부서', 'Y'),
    ('DEPT', 'SAFF', '학생부서', 'Y');

-- 학기 코드 예시
INSERT INTO COMM_CODE (CD_SE, CD, CD_NM, USE_YN)
VALUES
    ('SEMES', '1', '1학기', 'Y'),
    ('SEMES', '2', '2학기', 'Y');

-- 온라인/오프라인 코드 예시
INSERT INTO COMM_CODE (CD_SE, CD, CD_NM, USE_YN)
VALUES
    ('ONLYN', 'Y', '온라인', 'Y'),
    ('ONLYN', 'N', '오프라인', 'Y');
