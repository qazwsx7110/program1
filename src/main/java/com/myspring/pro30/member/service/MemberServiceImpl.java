package com.myspring.pro30.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro30.member.dao.MemberDAO;
import com.myspring.pro30.member.vo.MemberVO;


//회원 데이터 처리
@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;

	//회원 목록 조회
	@Override
	public List listMembers() throws DataAccessException {
		List membersList = null;
		membersList = memberDAO.selectAllMemberList();
		return membersList;
	}

	//회원 등록
	@Override
	public int addMember(MemberVO member) throws DataAccessException {
		return memberDAO.insertMember(member);
	}

	//회원 정보 수정
	@Override
	public int modMember(MemberVO member) throws DataAccessException {
		return memberDAO.modMember(member);
	}
	
	//회원 삭제
	@Override
	public int removeMember(String id) throws DataAccessException {
		return memberDAO.deleteMember(id);
	}
	
	//로그인한 회원 정보
	@Override
	public MemberVO login(MemberVO memberVO) throws Exception{
		return memberDAO.loginById(memberVO);
	}

	//아이디 중복확인
	@Override
	public int idCheck(String id) throws Exception{
		int cnt = memberDAO.idCheck(id);
		return cnt;
	}

	//이름 중복 확인
	@Override
	public int nameCheck(String name) throws Exception{
		int cnt = memberDAO.nameCheck(name);
		return cnt;
	}
}
