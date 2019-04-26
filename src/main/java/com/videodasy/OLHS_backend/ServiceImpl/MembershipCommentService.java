package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Dao.MembershipCommentDao;
import com.videodasy.OLHS_backend.Domain.MembershipComment;
import com.videodasy.OLHS_backend.Service.IMemberhipCommentService;
@Service
public class MembershipCommentService implements IMemberhipCommentService {
	
	@Autowired
	private MembershipCommentDao membershipCommentDao;

	@Override
	public String create(MembershipComment comment) {
		// TODO Auto-generated method stub
		return membershipCommentDao.createMembershipComment(comment);
	}

	@Override
	public String update(MembershipComment comment) {
		// TODO Auto-generated method stub
		return membershipCommentDao.updateMembershipComment(comment);
	}

	@Override
	public String delete(MembershipComment comment) {
		// TODO Auto-generated method stub
		return membershipCommentDao.deletemembershipComment(comment);
	}

	@Override
	public MembershipComment findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return membershipCommentDao.findByUuid(uuid) ;
	}

	@Override
	public List<MembershipComment> all() {
		// TODO Auto-generated method stub
		return membershipCommentDao.allMemberships();
	}

	@Override
	public List<MembershipComment> FindByMembershipId(long id) {
		// TODO Auto-generated method stub
		return membershipCommentDao.FindByMembershipId(id);
	}

}
