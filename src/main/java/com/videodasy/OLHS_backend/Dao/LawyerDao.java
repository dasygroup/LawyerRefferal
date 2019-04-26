package com.videodasy.OLHS_backend.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.videodasy.OLHS_backend.Domain.Lawyer;

import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class LawyerDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create Lawyer
	 * */
	public String createLawyer(Lawyer lawyer) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(lawyer);
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
	 * Method to update Lawyer
	 * */
	public String updateMembershipDocument(Lawyer lawyer) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(lawyer);
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
	 * Method to delete Lawyer
	 * */
	public String deleteLawyer(Lawyer lawyer) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			lawyer.setDeletedStatus(true);
			session.update(lawyer);
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
	 * Method to list all Lawyer
	 * */
	@SuppressWarnings("unchecked")
	public List<Lawyer> allLawyers() {
			Session session = sessionFactory.openSession();
			List<Lawyer> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from Lawyer where deletedStatus=0").list();
			
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
	 * The method to retrieve Lawyer ByUuid
	 * @param uuid
	 * @return
	 */
	
	public Lawyer findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		Lawyer lawyer=null;
		try{

			lawyer =(Lawyer) session.createQuery("from Lawyer c where c.uuid= :u and c.deletedStatus=0")
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
		return lawyer;
	}

	/**
	 * The method to retrieve Lawyer by District ID
	 * @param id
	 * @return
	 */
	
	public List<Lawyer> FindByDistrictId(long id){
		Session session = sessionFactory.openSession();
		List<Lawyer> lawyers=null;
		try{

			lawyers = session.createQuery("from Lawyer c where district_id= :rid and c.deletedStatus=0")
					.setParameter("rid",id).list();
					

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return lawyers;
	}

}
