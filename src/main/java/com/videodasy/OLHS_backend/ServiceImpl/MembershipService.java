package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.videodasy.OLHS_backend.Dao.MembershipDao;
import com.videodasy.OLHS_backend.Domain.Membership;
import com.videodasy.OLHS_backend.Service.IMembershipService;
@Service
public class MembershipService implements IMembershipService {

	@Autowired
	private MembershipDao membershipDao;
	@Override
	public String create(Membership membership) {
		// TODO Auto-generated method stub
		return membershipDao.createMembership(membership);
	}

	@Override
	public String update(Membership membership) {
		// TODO Auto-generated method stub
		return membershipDao.updateMembership(membership);
	}

	@Override
	public String delete(Membership membership) {
		// TODO Auto-generated method stub
		return membershipDao.deletemembership(membership);
	}

	@Override
	public Membership findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return membershipDao.findByUuid(uuid);
	}

	@Override
	public List<Membership> all() {
		// TODO Auto-generated method stub
		return membershipDao.allMemberships();
	}

	@Override
	public List<Membership> FindByRegistrantId(long id) {
		// TODO Auto-generated method stub
		return membershipDao.FindByRegistrantId(id);
	}



	@Override
	public Object upLoad(List<MultipartFile> files, String uuid) {
		// TODO Auto-generated method stub
		return membershipDao.upLoad(files, uuid);
	}
}
