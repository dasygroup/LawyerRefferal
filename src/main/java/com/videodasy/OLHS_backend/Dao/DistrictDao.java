package com.videodasy.OLHS_backend.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.videodasy.OLHS_backend.Domain.District;

import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class DistrictDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create District
	 * */
	public String createProvince(District district) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(district);
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
	 * Method to update district
	 * */
	public String updateDistrict(District district) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(district);
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
	 * Method to delete District
	 * */
	public String deleteDistrict(District district) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			district.setDeletedStatus(true);
			session.update(district);
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
	 * Method to list all Provinces
	 * */
	@SuppressWarnings("unchecked")
	public List<District> allDistricts() {
			Session session = sessionFactory.openSession();
			List<District> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from District where deletedStatus=0").list();
			
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
	 * The method to retrieve District ByUuid
	 * @param uuid
	 * @return
	 */
	
	public District findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		District district=null;
		try{

			district =(District) session.createQuery("from District c where c.uuid= :u and c.deletedStatus=0")
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
		return district;
	}

	
	public List<District> FindDistrictByProvince(long id){
		Session session = sessionFactory.openSession();
		List<District> districts=null;
		try{

			districts = session.createQuery("from District c where province_id= :id and c.deletedStatus=0")
					.setParameter("id",id).list();
					

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return districts;
	}

	

}
