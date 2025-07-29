-- 강박증 자가진단검사
INSERT INTO DGNSTC_TST (DGNSTC_TST_NM, DGNSTC_TST_DC, USE_YN)
VALUES ('강박증 자가진단검사', '강박증 증상 정도를 평가하는 자가진단 검사', 'Y');

-- 사회공포·회피 검사 (대학생 버전 기준)
INSERT INTO DGNSTC_TST (DGNSTC_TST_NM, DGNSTC_TST_DC, USE_YN)
VALUES ('사회공포·회피 진단검사', '대인관계 상황에서의 불안 및 회피 수준을 측정하는 검사', 'Y');

INSERT INTO DGNSTC_TST (DGNSTC_TST_NM, DGNSTC_TST_DC, USE_YN)
VALUES
    ('섭식장애 자가진단',
     '섭식 태도 검사는 식사, 체중, 다이어트, 음식 섭취 습관 등에 관련된 문제를 파악하여 섭식 장애 여부를 선별하기 위한 검사입니다. 각 문항을 읽고 현재 자신의 상태를 가장 잘 나타내는 항목에 응답해 주시기 바랍니다.',
     'Y');

INSERT INTO DGNSTC_TST (DGNSTC_TST_NM, DGNSTC_TST_DC, USE_YN)
VALUES
    ('스트레스 자가진단 테스트',
     '11개 문항을 통해 현재의 스트레스 정도를 진단합니다. 각 문항은 전혀 그렇지 않다(0점)~매우 그렇다(3점)까지 4단계로 평가되며, 총점을 기반으로 스트레스 상태를 해석합니다.',
     'Y');

INSERT INTO DGNSTC_TST (DGNSTC_TST_NM, DGNSTC_TST_DC, USE_YN)
VALUES
    ('인터넷 중독 진단검사',
     '인터넷 사용 습관과 몰입 정도를 측정하여 중독 위험 수준을 평가하는 검사입니다. \
     컴퓨터 및 스마트폰을 이용한 게임, 채팅, 정보검색 등을 포함하며, 자기보고식 문항에 응답하여 \
     인터넷 사용이 생활에 미치는 영향을 확인합니다.',
     'Y');
