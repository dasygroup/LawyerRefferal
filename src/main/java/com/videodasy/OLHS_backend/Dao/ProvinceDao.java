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
import com.videodasy.OLHS_backend.Domain.Province;
import com.videodasy.OLHS_backend.Utility.Messages;



@Repository
@Transactional
public class ProvinceDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Method to create province
	 * */
	public String createProvince(Province province) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(province);
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
	 * Method to update province
	 * */
	public String updateProvince(Province province) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(province);
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
	 * Method to delete province
	 * */
	public String deleteProvince(Province province) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			province.setDeletedStatus(true);
			session.update(province);
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
	public List<Province> allProvinces() {
			Session session = sessionFactory.openSession();
			List<Province> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from Province where deletedStatus=0").list();
			
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

	

	public Province findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		Province province=null;
		try{

			province =(Province) session.createQuery("from Province c where c.uuid= :u and c.deletedStatus=0")
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
		return province;
	}

	


	

	
}

