package com.LibraryService.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.LibraryService.Entity.Library;

import feign.Param;

public interface LibraryRepository extends CrudRepository<Library, Integer>{

	@Query("SELECT distinct e.bookId from Library e where e.username =:username")
	public List<Integer> findAllByUsername(@Param("username") String username);
	
	@Transactional
	@Modifying
	@Query("DELETE from Library e where e.username =:username and e.bookId=:bookId")
	public void releaseBook(@Param("username") String username, @Param("bookId") int bookId);
	
	@Transactional
	@Modifying
	@Query("DELETE from Library e where e.username =:username ")
	public void releaseAllBook(@Param("username") String username);
}
