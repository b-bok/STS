package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository
public class MemberDao {

	
	public Member loginMember(SqlSessionTemplate sqlSession, Member m) {
		
		
		// 과제
		// dao단에 기술해야되는 코드, member-mapper.xml 기술할 내용을 기술해 보시오 
		
		return sqlSession.selectOne("memberMapper.loginMember", m);
		
	}

	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		
		return sqlSession.insert("memberMapper.insertMember", m);
	}
	
	
	
}
