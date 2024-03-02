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


//ȸ�� ������ ó��
@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;

	//ȸ�� ��� ��ȸ
	@Override
	public List listMembers() throws DataAccessException {
		List membersList = null;
		membersList = memberDAO.selectAllMemberList();
		return membersList;
	}

	//ȸ�� ���
	@Override
	public int addMember(MemberVO member) throws DataAccessException {
		return memberDAO.insertMember(member);
	}

	//ȸ�� ���� ����
	@Override
	public int modMember(MemberVO member) throws DataAccessException {
		return memberDAO.modMember(member);
	}
	
	//ȸ�� ����
	@Override
	public int removeMember(String id) throws DataAccessException {
		return memberDAO.deleteMember(id);
	}
	
	//�α����� ȸ�� ����
	@Override
	public MemberVO login(MemberVO memberVO) throws Exception{
		return memberDAO.loginById(memberVO);
	}

	//���̵� �ߺ�Ȯ��
	@Override
	public int idCheck(String id) throws Exception{
		int cnt = memberDAO.idCheck(id);
		return cnt;
	}

	//�̸� �ߺ� Ȯ��
	@Override
	public int nameCheck(String name) throws Exception{
		int cnt = memberDAO.nameCheck(name);
		return cnt;
	}
}
