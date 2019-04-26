package com.videodasy.OLHS_backend.Dao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.videodasy.OLHS_backend.Domain.Membership;
import com.videodasy.OLHS_backend.Utility.Messages;

@Repository
@Transactional
public class MembershipDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Method to create Membership
	 * */
	public String createMembership(Membership membership) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(membership);
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
	 * Method to update Membership
	 * */
	public String updateMembership(Membership membership) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			session.update(membership);
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
	 * Method to delete Membership
	 * */
	public String deletemembership(Membership membership) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			membership.setDeletedStatus(true);
			session.update(membership);
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
	 * Method to list all membership
	 * */
	@SuppressWarnings("unchecked")
	public List<Membership> allMemberships() {
			Session session = sessionFactory.openSession();
			List<Membership> list=new ArrayList<>();
			try{
		
			list = session.createQuery("from Membership where deletedStatus=0").list();
			
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
	 * The method to retrieve Membership ByUuid
	 * @param uuid
	 * @return
	 */
	
	public Membership findByUuid(String uuid){
		Session session = sessionFactory.openSession();
		Membership membership=null;
		try{

			membership =(Membership) session.createQuery("from Membership c where c.uuid= :u and c.deletedStatus=0")
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
		return membership;
	}

	/**
	 * The method to retrieve membership by Registrant ID
	 * @param id
	 * @return
	 */
	
	public List<Membership> FindByRegistrantId(long id){
		Session session = sessionFactory.openSession();
		List<Membership> memberships=null;
		try{

			memberships = session.createQuery("from Membership c where registrant_id= :rid and c.deletedStatus=0")
					.setParameter("rid",id).list();
					

		} catch (Exception e) {


		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.clear();
				session.close();
			}
		}
		return memberships;
	}

	public Object upLoad(List<MultipartFile> files, String uuid) {
		try {
			String destination = "memberships";
			File f = new File(destination);
			List<String> paths = new ArrayList<>();
			for (MultipartFile file : files) {
				String filename = uuid + "_" + file.getOriginalFilename();
				if (!f.exists())
					f.mkdir();

				byte[] bytes = file.getBytes();
				Path path = Paths.get(destination + "/" + filename);
				Files.write(path, bytes);
				paths.add(path.toString());
			}
			return paths;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}
	

}
