INSERT INTO EXTRACURRICULAR_PROGRAM (EMP_NO, CTGRY_ID, EDU_NM, EDU_TYPE, EDU_TRGT_LMT, EDU_GNDR_LMT, EDU_SLCTN_TYPE, EDU_PTCP_NOPE, EDU_PRPS, EDU_DTL_CN, EDU_APLY_BGNG_DT, EDU_APLY_END_DT, EDU_BGNG_YMD, EDU_END_YMD, EDU_PLC_NM, EDU_APLY_DT, STTS_NM, EDU_MLG, STTS_CHG_DT, CND_CN, FILE_NO) VALUES
-- 스피치 프로그램 분류체계 (CTGRY_ID=1)
('E000000001', 1, '기초 스피치', 'PERSONAL', 'STUDENT', 'ALL', 'FIRSTCOME', 30, '스피치 기본 능력 향상', '기초 발음과 표현 훈련', NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY), '20230801', '20230831', '서울캠퍼스', NOW(), 'APPROVED', 10, NOW(), '출석 80% 이상', 'file1.jpg'),
('E000000002', 1, '심화 스피치', 'TEAM', 'STUDENT', 'ALL', 'SELECTION', 20, '팀 발표 능력 강화', '토론 및 발표 실습', NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), '20230901', '20230930', '서울캠퍼스', NOW(), 'APPROVED', 20, NOW(), '출석 90% 이상', 'file2.jpg'),

-- 토론 프로그램 분류체계 (CTGRY_ID=2)
('E000000003', 2, '기초 토론', 'PERSONAL', 'STUDENT', 'ALL', 'FIRSTCOME', 30, '토론 기본기 습득', '논리 전개 및 반론 연습', NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY), '20230801', '20230831', '서울캠퍼스', NOW(), 'APPROVED', 10, NOW(), '출석 80% 이상', 'file3.jpg'),
('E000000004', 2, '심화 토론', 'TEAM', 'STUDENT', 'ALL', 'SELECTION', 20, '팀 토론 전략 개발', '팀별 주제 토론 실습', NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), '20230901', '20230930', '서울캠퍼스', NOW(), 'APPROVED', 20, NOW(), '출석 90% 이상', 'file4.jpg'),

-- 논술 프로그램 분류체계 (CTGRY_ID=3)
('E000000005', 3, '기초 논술', 'PERSONAL', 'STUDENT', 'ALL', 'FIRSTCOME', 30, '논술 기초 실력 향상', '문장 구성 및 표현 연습', NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY), '20230801', '20230831', '서울캠퍼스', NOW(), 'APPROVED', 10, NOW(), '출석 80% 이상', 'file5.jpg'),
('E000000006', 3, '심화 논술', 'TEAM', 'STUDENT', 'ALL', 'SELECTION', 20, '심층 논술 분석', '논문 및 에세이 작성법', NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), '20230901', '20230930', '서울캠퍼스', NOW(), 'APPROVED', 20, NOW(), '출석 90% 이상', 'file6.jpg'),

-- 에세이 프로그램 분류체계 (CTGRY_ID=4)
('E000000007', 4, '기초 에세이', 'PERSONAL', 'STUDENT', 'ALL', 'FIRSTCOME', 30, '에세이 작성 기초', '주제 설정과 문장 구성', NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY), '20230801', '20230831', '서울캠퍼스', NOW(), 'APPROVED', 10, NOW(), '출석 80% 이상', 'file7.jpg'),
('E000000008', 4, '심화 에세이', 'TEAM', 'STUDENT', 'ALL', 'SELECTION', 20, '팀 에세이 프로젝트', '팀별 에세이 작성 및 발표', NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), '20230901', '20230930', '서울캠퍼스', NOW(), 'APPROVED', 20, NOW(), '출석 90% 이상', 'file8.jpg');
