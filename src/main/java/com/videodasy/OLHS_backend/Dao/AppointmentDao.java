package com.videodasy.OLHS_backend.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.videodasy.OLHS_backend.Domain.Appointment;

import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class AppointmentDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create Appointment
	 * */
	public String createProvince(Appointment appointment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(appointment);
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
	 * Method to update Appointment
	 * */
	public String updateAppointment(Appointment appointment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(appointment);
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
	 * Method to delete Appointment
	 * */
	public String deleteAppointment(Appointment appointment) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			appointment.setDeletedStatus(true);
			session.update(appointment);
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
	 * Method to list all appointments
	 * */
	@SuppressWarnings("unchecked")
	public List<Appointment> allAppointments() {
			Session session = sessionFactory.openSession();
			List<Appointment> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from Appointment where deletedStatus=0").list();
			
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
	 * The method to retrieve appointment ByUuid
	 * @param uuid
	 * @return
	 */
	
	public Appointment findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		Appointment appointment=null;
		try{

			appointment =(Appointment) session.createQuery("from Appointment c where c.uuid= :u and c.deletedStatus=0")
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
		return appointment;
	}

	/**
	 * The method to retrieve appointments by Registrant ID
	 * @param id
	 * @return
	 */
	
	public List<Appointment> FindByRegistrantId(long id){
		Session session = sessionFactory.openSession();
		List<Appointment> Appointments=null;
		try{

			Appointments = session.createQuery("from Appointment c where registrant_id= :rid and c.deletedStatus=0")
					.setParameter("rid",id).list();
					

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return Appointments;
	}

	

}
