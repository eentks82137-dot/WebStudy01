-- a001 회원의 기본정보와 roles 정보를 조회
select m.mem_id,
       m.mem_name,
       m.mem_pass,
       mr.role_name
  from member m
  left outer join member_role mr
on ( m.mem_id = mr.mem_id )
 where m.mem_id = 'a001';