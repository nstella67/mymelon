-- mediagroup.sql
-- 미디어 그룹 테이블 생성
CREATE TABLE mediagroup(
	mediagroupno NUMBER NOT NULLPRIMARY KEY,	--그룹번호
	title VARCHAR2(255) NAT NULL		--그룹제목
)
;

-- 행추가 테스트
그룹번호 : 그룹번호 최대값+1
그룹제목 : '2018년 댄스 음악'
INSERT INTO mediagroup(mediagroupno, title)
VALUES((SELECT NVL(MAX(mediagroupno), 0)+1 FROM mediagroup), '2018년 댄스 음악')
;

--목록
SELECT mediagroupno, title
FROM mediagroup
ORDER BY mediagroupno DESC
;


