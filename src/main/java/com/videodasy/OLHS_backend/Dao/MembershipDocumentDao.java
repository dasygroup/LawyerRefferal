package com.videodasy.OLHS_backend.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.videodasy.OLHS_backend.Domain.MembershipComment;
import com.videodasy.OLHS_backend.Domain.MemebershipDocument;
import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class MembershipDocumentDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create MembershipDocument
	 * */
	public String createMembershipDocument(MemebershipDocument document) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(document);
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
	 * Method to update MemebershipDocument
	 * */
	public String updateMembershipDocument(MemebershipDocument document) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(document);
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
	 * Method to delete MemebershipDocument
	 * */
	public String deletemembershipDocument(MemebershipDocument document) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			document.setDeletedStatus(true);
			session.update(document);
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
	 * Method to list all MemebershipDocument
	 * */
	@SuppressWarnings("unchecked")
	public List<MemebershipDocument> allMemberships() {
			Session session = sessionFactory.openSession();
			List<MemebershipDocument> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from MemebershipDocument where deletedStatus=0").list();
			
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
	 * The method to retrieve MemebershipDocument ByUuid
	 * @param uuid
	 * @return
	 */
	
	public MemebershipDocument findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		MemebershipDocument Document=null;
		try{

			Document =(MemebershipDocument) session.createQuery("from MemebershipDocument c where c.uuid= :u and c.deletedStatus=0")
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
		return Document;
	}

	/**
	 * The method to retrieve MemebershipDocument by Registrant ID
	 * @param id
	 * @return
	 */
	
	public List<MemebershipDocument> FindByMembershipId(long id){
		Session session = sessionFactory.openSession();
		List<MemebershipDocument> documents=null;
		try{

			documents = session.createQuery("from MemebershipDocument c where membership_id= :rid and c.deletedStatus=0")
					.setParameter("rid",id).list();
					

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return documents;
	}
	
	  public String deleteAllByMembership(long mid) {
	        Session session = sessionFactory.openSession();
	        Transaction tx = session.beginTransaction();
	        try {
	            Query q = session.createQuery("update MembershipDocument set deleted_status=1 where membership_id=:mId");
	            q.setParameter("mId", mid);
	            q.executeUpdate();
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
}
