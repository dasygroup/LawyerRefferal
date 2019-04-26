package com.videodasy.OLHS_backend.Service;

import java.util.List;


import com.videodasy.OLHS_backend.Domain.ComplainComent;

public interface IComplainCommentSerice {

	 String create(final ComplainComent coment);
		
		String update(final ComplainComent coment);
		
		String delete(final ComplainComent coment);

		ComplainComent findByUuid(String uuid);

		List<ComplainComent> all();
		
		List<ComplainComent> FindByComplainId(long id);
}
