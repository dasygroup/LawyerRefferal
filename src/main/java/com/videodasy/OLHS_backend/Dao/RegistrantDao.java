package com.videodasy.OLHS_backend.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.videodasy.OLHS_backend.Domain.Complain;
import com.videodasy.OLHS_backend.Domain.Registrant;
import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class RegistrantDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create Registrant
	 * */
	public String createRegistrant(Registrant registrant) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(registrant);
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
	 * Method to update Registrant
	 * */
	public String updateRegistrant(Registrant registrant) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(registrant);
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
	 * Method to delete Registrant
	 * */
	public String deleteRegistrant(Registrant registrant) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			registrant.setDeletedStatus(true);
			session.update(registrant);
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
	 * Method to list all Registrants
	 * */
	@SuppressWarnings("unchecked")
	public List<Registrant> allRegistrants() {
			Session session = sessionFactory.openSession();
			List<Registrant> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from Registrant where deletedStatus=0").list();
			
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
	 * The method to retrieve Registrant ByUuid
	 * @param uuid
	 * @return
	 */
	
	public Registrant findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		Registrant registrant=null;
		try{

			registrant =(Registrant) session.createQuery("from Registrant c where c.uuid= :u and c.deletedStatus=0")
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
		return registrant;
	}
	
	/**
	 * The Method to find Registrant by id
	 * @param id
	 * @return
	 */

	public Registrant findOne(long id){
		Session session = sessionFactory.openSession();
		Registrant registrant=null;
		try{

			registrant =(Registrant) session.createQuery("from Registrant c where c.id= :u and c.deletedStatus=0")
					.setParameter("u",id)
					.uniqueResult();

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return registrant;
	}

	/**
	 * The Method to find Registrant By Identity
	 * @param identity
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public Registrant findByIdentity(String identity) {
		
		Session session = sessionFactory.openSession();
		Registrant registrant = null;
	
			registrant = (Registrant) session.createQuery("from Registrant r where r.identity= :id and deletedStatus=0")
					.setParameter("id", identity).uniqueResult();
			
			
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		
		return registrant;
	}

}
