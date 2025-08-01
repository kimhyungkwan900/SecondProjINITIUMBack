INSERT INTO DGNSTC_SCR_LVL (DGNSTC_TST_ID, MIN_SCR, MAX_SCR, SCR_LVL_NM, SCR_LVL_DC)
VALUES
    (1, 30, 44, '평균 범위의 강박 증상', '강박 증상이 낮은 편으로, 일상생활에서 특별한 어려움을 경험하지 않는 상태입니다.'),
    (1, 45, 49, '중간 정도의 강박 증상', '중간 정도의 강박 증상을 겪고 계시지만, 강박장애 수준까지는 아닙니다. 불편함이 지속되면 상담을 권유드립니다.'),
    (1, 50, 60, '심한 정도의 강박 증상', '강박 장애 가능성이 높으며, 증상이 심해 일상생활에 큰 불편을 주는 상태입니다. 전문가 상담을 적극 권장드립니다.');


INSERT INTO DGNSTC_SCR_LVL (DGNSTC_TST_ID, MIN_SCR, MAX_SCR, SCR_LVL_NM, SCR_LVL_DC)
VALUES
-- 1단계: 증상 없음
(2, 26, 63, '증상 없음', '사회불안이 낮은 편입니다. 발표나 대인관계에서 큰 어려움을 느끼지 않습니다. 누구나 약간은 긴장된다는 것을 아시고 자신감 있게 생활하세요.'),

-- 2단계: 약한 정도의 증상
(2, 64, 81, '약한 정도의 증상', '경미한 수준의 사회불안이 있습니다. 평가나 상황에 대해 중요하게 생각하고 계시며 이러한 불안을 관리하며 사회생활을 유지할 수 있습니다.'),

-- 3단계: 중간 정도의 증상
(2, 82, 98, '중간 정도의 증상', '중간 정도의 사회불안을 겪고 계시지만 사회공포증 수준까지는 아닙니다. 이는 개인의 상태에 따라 큰 불편감을 경험할 수도 있고 일상생활을 하는 데는 큰 지장이 없습니다.'),

-- 4단계: 심한 정도의 증상
(2, 99, 140, '심한 정도의 증상', '즉시 초기면접을 받아보시길 권해드립니다. 이 점수는 사회공포증일 가능성이 매우 큽니다.');


INSERT INTO DGNSTC_SCR_LVL (DGNSTC_TST_ID, MIN_SCR, MAX_SCR, SCR_LVL_NM, SCR_LVL_DC)
VALUES
-- 1단계: 섭식 문제 없음
(3, 0, 14, '섭식 문제 없음', '현재 식사나 음식 섭취, 체중, 다이어트 등에 관련된 심각한 문제는 보이지 않습니다.'),
(3, 0, 17, '섭식 문제 없음', '현재 식사나 음식 섭취, 체중, 다이어트 등에 관련된 심각한 문제는 보이지 않습니다.'),

-- 2단계: 섭식 문제의 경향성 있음
(3, 15, 18, '섭식 문제의 경향성 있음', '음식 섭취, 체중, 다이어트 등과 관련하여 주관적인 문제를 경험할 가능성이 있습니다. 일상생활에서 불편함이 크지 않으나 스트레스 상황에서 증상이 나타날 수 있습니다.'),
(3, 18, 21, '섭식 문제의 경향성 있음', '음식 섭취, 체중, 다이어트 등과 관련하여 주관적인 문제를 경험할 가능성이 있습니다. 일상생활에서 불편함이 크지 않으나 스트레스 상황에서 증상이 나타날 수 있습니다.'),

-- 3단계: 섭식 문제 있음
(3, 19, 22, '섭식 문제 있음', '음식 섭취, 체중, 다이어트 등에 관련하여 문제의식을 가지고 있으며 실제 행동 및 신체적인 문제를 경험하고 있을 가능성이 높습니다. '),
(3, 22, 27, '섭식 문제 있음', '음식 섭취, 체중, 다이어트 등에 관련하여 문제의식을 가지고 있으며 실제 행동 및 신체적인 문제를 경험하고 있을 가능성이 높습니다.'),

-- 4단계: 심각한 섭식 문제 있음
(3, 23, 100, '심각한 섭식 문제 있음', '심각한 식사와 체중 조절 문제로 인해 건강에 큰 영향을 미칠 가능성이 높습니다. 즉시 전문적인 상담과 치료 개입이 필요합니다.'),
(3, 28, 100, '심각한 섭식 문제 있음', '심각한 식사와 체중 조절 문제로 인해 건강에 큰 영향을 미칠 가능성이 높습니다. 즉시 전문적인 상담과 치료 개입이 필요합니다.');


INSERT INTO DGNSTC_SCR_LVL (DGNSTC_TST_ID, MIN_SCR, MAX_SCR, SCR_LVL_NM, SCR_LVL_DC)
VALUES
-- 0~11점
(4, 0, 11, '스트레스 지수 0%', '거의 스트레스를 받지 않는 상태로, 현재 상태를 잘 유지하면 됩니다.'),

-- 12~13점
(4, 12, 13, '스트레스 지수 20%', '약간 스트레스를 받고 있으며, 사람들과의 교제를 늘리고 자신을 위해 긍정적인 활동을 하는 것이 좋습니다.'),

-- 14~16점
(4, 14, 16, '스트레스 지수 40%', '스트레스가 심한 편이므로 원인을 찾아 적극적으로 맞서며 운동, 영양 섭취, 충분한 수면이 필요합니다.'),

-- 17~20점
(4, 17, 20, '스트레스 지수 60%', '심한 스트레스 상태로, 신체 건강 점검과 함께 스트레스 원인을 줄이기 위한 적극적인 대책이 필요합니다.'),

-- 21점 이상
(4, 21, 100, '스트레스 지수 80%', '탈진기에 접어들 위험이 있으며, 면역력이 저하되고 신체 질병이나 정신 질환 위험이 있으므로 즉시 정신과 또는 상담센터를 방문하는 것이 권장됩니다.');


INSERT INTO DGNSTC_SCR_LVL (DGNSTC_TST_ID, MIN_SCR, MAX_SCR, SCR_LVL_NM, SCR_LVL_DC)
VALUES
-- 일반사용자군
(5, 20, 42, '일반사용자군', '인터넷을 자신의 흥미와 욕구, 목적에 맞게 사용하는 경우로, 사용 시간을 적절하게 조절할 수 있습니다. 일상 활동을 유지하며 필요 시 인터넷 사용을 조절할 수 있습니다.'),

-- 잠재적 위험사용자군
(5, 43, 53, '잠재적 위험사용자군', '인터넷 사용시간이 늘어나면서 생활의 일부에 영향을 미칠 수 있는 단계입니다. 스스로 사용 패턴을 점검하고 사용시간을 줄이는 노력이 필요합니다.'),

-- 고위험사용자군
(5, 54, 66, '고위험사용자군', '대인관계, 학업, 직업, 일상생활의 여러 영역에 지장을 초래할 가능성이 높으며, 전문가 상담을 통한 사용습관 개선과 필요 시 전문 치료기관의 개입이 필요합니다.');
