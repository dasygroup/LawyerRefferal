package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Dao.ComplainCommentDao;
import com.videodasy.OLHS_backend.Domain.ComplainComent;
import com.videodasy.OLHS_backend.Service.IComplainCommentSerice;
@Service
public class ComplainCommentService implements IComplainCommentSerice {
	
	
	@Autowired
	private ComplainCommentDao complainDao;

	@Override
	public String create(ComplainComent coment) {
		// TODO Auto-generated method stub
		return complainDao.createComplainComment(coment);
	}

	@Override
	public String update(ComplainComent coment) {
		// TODO Auto-generated method stub
		return complainDao.updateComplainComment(coment);
	}

	@Override
	public String delete(ComplainComent coment) {
		// TODO Auto-generated method stub
		return complainDao.deleteComplainComment(coment);
	}

	@Override
	public ComplainComent findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return complainDao.findByUuid(uuid);
	}

	@Override
	public List<ComplainComent> all() {
		// TODO Auto-generated method stub
		return complainDao.allComplainComents();
	}

	@Override
	public List<ComplainComent> FindByComplainId(long id) {
		// TODO Auto-generated method stub
		return complainDao.FindByComplainId(id);
	}

	
}
