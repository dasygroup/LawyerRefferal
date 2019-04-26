package com.videodasy.OLHS_backend.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.videodasy.OLHS_backend.Domain.Complain;
import com.videodasy.OLHS_backend.Domain.ComplainComent;
import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class ComplainCommentDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create ComplainComment
	 * */
	public String createComplainComment(ComplainComent coment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(coment);
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
	 * Method to update ComplainComent
	 * */
	public String updateComplainComment(ComplainComent coment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(coment);
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
	 * Method to delete ComplainComent
	 * */
	public String deleteComplainComment(ComplainComent coment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			coment.setDeletedStatus(true);
			session.update(coment);
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
	 * Method to list all ComplainComents
	 * */
	@SuppressWarnings("unchecked")
	public List<ComplainComent> allComplainComents() {
			Session session = sessionFactory.openSession();
			List<ComplainComent> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from ComplainComent where deletedStatus=0").list();
			
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
	 * The method to retrieve ComplainComent ByUuid
	 * @param uuid
	 * @return
	 */
	
	public ComplainComent findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		ComplainComent coment=null;
		try{

			coment =(ComplainComent) session.createQuery("from ComplainComent c where c.uuid= :u and c.deletedStatus=0")
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
		return coment;
	}

	/**
	 * The method to retrieve Complains by District ID
	 * @param id
	 * @return
	 */
	
	public List<ComplainComent> FindByComplainId(long id){
		Session session = sessionFactory.openSession();
		List<ComplainComent> coments=null;
		try{

			coments = session.createQuery("from ComplainComent c where complain_id= :rid and c.deletedStatus=0")
					.setParameter("rid",id).list();
					

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return coments;
	}

}
