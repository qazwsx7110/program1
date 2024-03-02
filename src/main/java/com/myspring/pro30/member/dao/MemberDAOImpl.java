package com.myspring.pro30.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.member.vo.MemberVO;


//ȸ�� ������ �ҷ�����
@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;

	//��� ȸ�� ��� �ҷ�����
	@Override
	public List selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}

	//ȸ�� ���
	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}

	//ȸ�� ����
	@Override
	public int deleteMember(String id) throws DataAccessException {
		int result = sqlSession.delete("mapper.member.deleteMember", id);
		return result;
	}
	
	//ȸ�� ���� ����
	@Override
	public int modMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.update("mapper.member.updateMember", memberVO);
		return result;
	}
	
	//�α��� ȸ�� �ҷ�����
	@Override
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException{
		  MemberVO vo = sqlSession.selectOne("mapper.member.loginById",memberVO);
		return vo;
	}	
	
	//���̵� �ߺ� Ȯ��
	@Override
	public MemberVO duplicationId(String id) throws DataAccessException{
		  MemberVO vo = sqlSession.selectOne("mapper.member.duplicationId",id);
		return vo;
	}
	
	//���̵� �ߺ� Ȯ��
	@Override
	public int idCheck(String id) throws DataAccessException {
		int result = sqlSession.selectOne("mapper.member.idCheck", id);
		return result;
	}
		
	//�̸� �ߺ� Ȯ��
	@Override
	public int nameCheck(String name) throws DataAccessException {
		int result = sqlSession.selectOne("mapper.member.nameCheck", name);
		return result;
	}
}
