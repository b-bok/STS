
-- * 회원 관련 서비스

-- 로그인 회원 조회
select 
       user_id
     , user_pwd
     , user_name
     , email
     , gender
     , age
     , phone
     , address
     , enroll_date
     , modify_date
     , status
from member
where user_id = #{ userId }
  and user_pwd = #{ userPwd }
  ;
