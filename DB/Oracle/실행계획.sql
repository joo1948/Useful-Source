# 실제 실행계획 저장
	EXPLAIN PLAN FOR
	SELECT 
	COUNT(*) AS cnt
			FROM EVENT_RESOURCE_BOOKING a
			LEFT JOIN EVENT_DATE b ON a.DateID = b.DateID
	  WHERE a.ResourceID =  (SELECT * FROM (SELECT NVL(LinkFolderID,folderID) FROM SYS_OBJECT_FOLDER WHERE folderID = 1316 ORDER BY folderID DESC) A WHERE ROWNUM = 1)
	    AND a.DeleteDate IS NULL
	    AND a.ApprovalState IN('Approval','ReturnRequest','ReturnComplete')
	    AND (
	        ('2022-09-12 12:00' <= b.StartDateTime AND TO_CHAR(a.RealEndDateTime,'YYYY-MM-DD HH24:MI') <= '2022-09-12 12:30')
	        OR ('2022-09-12 12:00' < b.StartDateTime AND b.StartDateTime <'2022-09-12 12:30' AND '2022-09-12 12:30' <= TO_CHAR(a.RealEndDateTime,'YYYY-MM-DD HH24:MI'))
	        OR (b.StartDateTime <='2022-09-12 12:00' AND '2022-09-12 12:00' < TO_CHAR(a.RealEndDateTime,'YYYY-MM-DD HH24:MI') AND TO_CHAR(a.RealEndDateTime,'YYYY-MM-DD HH24:MI') <= '2022-09-12 12:30')
	        OR (b.StartDateTime <= '2022-09-12 12:00' AND '2022-09-12 12:30' <= TO_CHAR(a.RealEndDateTime,'YYYY-MM-DD HH24:MI') )
	    );
	
 # 마지막 저장된 실행계획 조회
	 SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY());
	

# 실행계획의 의미 
	• Id : 실행계획의 오퍼레이션ID
	• Operation : 해당단계에 수행한 작업 내용
	• Name : 해당단계에 작업을 수행한 대상 오브젝트(테이블 or 인덱스)
	• Rows : 해당 단계 수행 시 조회될 예상 데이터 건수
	• Bytes : 해당 단계까지 사용될 예상 데이터 양(누적)
	• Cost : 해당 단계까지 사용될 예상 비용(누적)
	• Time : 해당 단계까지 사용될 예상 시간(누적)
