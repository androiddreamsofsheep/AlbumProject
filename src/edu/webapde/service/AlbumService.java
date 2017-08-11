package edu.webapde.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import edu.webapde.bean.Album;

public class AlbumService {
	// CRUD operators
	
	// createAlbum
	public static void addAlbum(Album album){
		// connect to JDBC driver
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		// add object into db
		try{
			trans.begin();
			em.persist(album);
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		// close connections
		em.close();
	}
	
	// retrieveSingleAlbum
	public static Album getAlbum(int id){
		Album album = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		trans.begin();
		album = em.find(Album.class, id);
		trans.commit();
		
		em.close();
		
		return album;
	}
	
	// deleteAlbum
	public static void deleteAlbum(int id){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		// delete object from db
		try{
			trans.begin();
			
			// find the album
			Album a = em.find(Album.class, id);
//			a = getAlbum(id+1);
			//remove album a from em
			em.remove(a);
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		// close connections
		em.close();
	}
	
	// retrieveAllAlbums
	public static List<Album> getAllAlbums(){
		List<Album> albums = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		// delete object from db
		try{
			trans.begin();
			
			TypedQuery<Album> query = em.createQuery("select album from album album", Album.class);
			albums = query.getResultList();
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}finally{
			// close connections
			em.close();
		}
		return albums;
	}
	
	// updateAlbum
	public static boolean updateAlbum(int id, Album newinfo){
		boolean success = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			// find album
			Album a = em.find(Album.class, id);
			// change contents
			a.setName(newinfo.getName());
			a.setDescription(newinfo.getDescription());
			a.setPrivacy(newinfo.isPrivacy());
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}finally{
			em.close();
		}
		
		return success;
	}
	
	public static void main(String[] args) {
		
		deleteAlbum(3);

		List<Album> albums = getAllAlbums();
		for(Album a : albums){
			System.out.println(a.toString());
		}
		
		
	}
	
}












