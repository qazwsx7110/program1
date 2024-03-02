package com.myspring.pro30.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.member.vo.MemberVO;


//회원 데이터 불러오기
@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;

	//모든 회원 목록 불러오기
	@Override
	public List selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}

	//회원 등록
	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}

	//회원 삭제
	@Override
	public int deleteMember(String id) throws DataAccessException {
		int result = sqlSession.delete("mapper.member.deleteMember", id);
		return result;
	}
	
	//회원 정보 수정
	@Override
	public int modMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.update("mapper.member.updateMember", memberVO);
		return result;
	}
	
	//로그인 회원 불러오기
	@Override
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException{
		  MemberVO vo = sqlSession.selectOne("mapper.member.loginById",memberVO);
		return vo;
	}	
	
	//아이디 중복 확인
	@Override
	public MemberVO duplicationId(String id) throws DataAccessException{
		  MemberVO vo = sqlSession.selectOne("mapper.member.duplicationId",id);
		return vo;
	}
	
	//아이디 중복 확인
	@Override
	public int idCheck(String id) throws DataAccessException {
		int result = sqlSession.selectOne("mapper.member.idCheck", id);
		return result;
	}
		
	//이름 중복 확인
	@Override
	public int nameCheck(String name) throws DataAccessException {
		int result = sqlSession.selectOne("mapper.member.nameCheck", name);
		return result;
	}
}
