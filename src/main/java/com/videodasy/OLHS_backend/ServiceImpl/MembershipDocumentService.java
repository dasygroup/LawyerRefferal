package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Dao.MembershipDocumentDao;
import com.videodasy.OLHS_backend.Domain.MemebershipDocument;
import com.videodasy.OLHS_backend.Service.IMembershipDocument;
@Service
public class MembershipDocumentService implements IMembershipDocument {
	
	@Autowired
	private MembershipDocumentDao membershipDocumentDao;

	@Override
	public String create(MemebershipDocument document) {
		// TODO Auto-generated method stub
		return membershipDocumentDao.createMembershipDocument(document);
	}

	@Override
	public String update(MemebershipDocument document) {
		// TODO Auto-generated method stub
		return membershipDocumentDao.updateMembershipDocument(document);
	}

	@Override
	public String delete(MemebershipDocument document) {
		// TODO Auto-generated method stub
		return membershipDocumentDao.deletemembershipDocument(document);
	}

	@Override
	public MemebershipDocument findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return membershipDocumentDao.findByUuid(uuid);
	}

	@Override
	public List<MemebershipDocument> all() {
		// TODO Auto-generated method stub
		return membershipDocumentDao.allMemberships();
	}

	@Override
	public List<MemebershipDocument> FindByMembershipId(long id) {
		// TODO Auto-generated method stub
		return membershipDocumentDao.FindByMembershipId(id);
	}

	@Override
	public String deleteAllByMembership(long mid) {
		// TODO Auto-generated method stub
		return membershipDocumentDao.deleteAllByMembership(mid);
	}

}
