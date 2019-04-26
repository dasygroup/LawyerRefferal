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

import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class ComplainDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create Complain
	 * */
	public String createComplain(Complain complain) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(complain);
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
	 * Method to update Complain
	 * */
	public String updateComplain(Complain complain) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(complain);
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
	 * Method to delete Complain
	 * */
	public String deleteComplain(Complain complain) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			complain.setDeletedStatus(true);
			session.update(complain);
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
	 * Method to list all Complains
	 * */
	@SuppressWarnings("unchecked")
	public List<Complain> allComplains() {
			Session session = sessionFactory.openSession();
			List<Complain> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from Complain where deletedStatus=0").list();
			
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
	 * The method to retrieve Complain ByUuid
	 * @param uuid
	 * @return
	 */
	
	public Complain findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		Complain complain=null;
		try{

			complain =(Complain) session.createQuery("from Complain c where c.uuid= :u and c.deletedStatus=0")
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
		return complain;
	}

	/**
	 * The method to retrieve Complains by District ID
	 * @param id
	 * @return
	 */
	
	public List<Complain> FindByAppointmentId(long id){
		Session session = sessionFactory.openSession();
		List<Complain> complains=null;
		try{

			complains = session.createQuery("from Complain c where appointment_id= :rid and c.deletedStatus=0")
					.setParameter("rid",id).list();
					

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return complains;
	}


}
