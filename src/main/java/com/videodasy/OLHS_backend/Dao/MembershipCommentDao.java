package com.videodasy.OLHS_backend.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.videodasy.OLHS_backend.Domain.Membership;
import com.videodasy.OLHS_backend.Domain.MembershipComment;
import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class MembershipCommentDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create MembershipComment
	 * */
	public String createMembershipComment(MembershipComment membershipComment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(membershipComment);
			tx.commit();
			session.flush();
			return Messages.save;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return Messages.error;
		} finally {
			if (session != null && session.isOpen()) {
				session.clear();
				session.close();
			}
		}
	}

	/**
	 * Method to update MembershipComment
	 * */
	public String updateMembershipComment(MembershipComment membershipComment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(membershipComment);
			tx.commit();
			session.flush();
			return Messages.update;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return Messages.error;
		} finally {
			if (session != null && session.isOpen()) {
				session.clear();
				session.close();
			}
		}
	}

	/**
	 * Method to delete MembershipComment
	 * */
	public String deletemembershipComment(MembershipComment membershipComment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			membershipComment.setDeletedStatus(true);
			session.update(membershipComment);
			tx.commit();
			session.flush();
			return Messages.delete;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return Messages.error;
		} finally {
			if (session != null && session.isOpen()) {
				session.clear();
				session.close();
			}
		}
	}

	/**
	 * Method to list all membershipComments
	 * */
	@SuppressWarnings("unchecked")
	public List<MembershipComment> allMemberships() {
			Session session = sessionFactory.openSession();
			List<MembershipComment> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from MembershipComment where deletedStatus=0").list();
			
			} catch (Exception e) {

			
			} finally {
				if (session != null && session.isOpen()) {
					session.flush();
					session.clear();
					session.close();
				}
			}
			return list;	
	}
	
	/**
	 * The method to retrieve MembershipComment ByUuid
	 * @param uuid
	 * @return
	 */
	
	public MembershipComment findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		MembershipComment membershipComment=null;
		try{

			membershipComment =(MembershipComment) session.createQuery("from MembershipComment c where c.uuid= :u and c.deletedStatus=0")
					.setParameter("u",uuid)
					.uniqueResult();

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return membershipComment;
	}

	/**
	 * The method to retrieve membership by Registrant ID
	 * @param id
	 * @return
	 */
	
	public List<MembershipComment> FindByMembershipId(long id){
		Session session = sessionFactory.openSession();
		List<MembershipComment> membershipCommets=null;
		try{

			membershipCommets = session.createQuery("from MembershipComment c where membership_id= :rid and c.deletedStatus=0")
					.setParameter("rid",id).list();
					

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return membershipCommets;
	}

	
}
