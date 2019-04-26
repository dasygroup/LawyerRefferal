package com.videodasy.OLHS_backend.Service;

import java.util.List;


import com.videodasy.OLHS_backend.Domain.MembershipComment;

public interface IMemberhipCommentService {
	
	String create(final MembershipComment comment);
	
	String update(final MembershipComment comment);
	
	String delete(final MembershipComment comment);

	MembershipComment findByUuid(String uuid);

	List<MembershipComment> all();
	
	List<MembershipComment> FindByMembershipId(long id);


}
