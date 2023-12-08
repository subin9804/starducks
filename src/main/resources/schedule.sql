SELECT * FROM starducks.personal_schedule;

-- 더미 데이터 설정하기
use starducks;

INSERT INTO personal_schedule(sche_no, sche_title, sche_start_date, sche_end_date, notes, calendar_type, emp_id)
VALUES (1, '가가가가', '2023-12-06', '2023-12-07', '내용1', 'PERSONAL_SCHEDULE', 1);

INSERT INTO personal_schedule(sche_no, sche_title, sche_start_date, sche_end_date, notes, calendar_type, emp_id)
VALUES (2, '나나나나', '2023-12-10', '2023-12-11', '내용2', 'OFFICIAL_SCHEDULE', 1);

INSERT INTO personal_schedule(sche_no, sche_title, sche_start_date, sche_end_date, notes, calendar_type, emp_id)
VALUES (3, '다다다다', '2023-12-10', '2023-12-11', '내용2', 'PERSONAL_SCHEDULE', 2);