package com.gopivotal.bookshop.buslogic;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gopivotal.bookshop.domain.BookMaster;

public interface BookMasterRepository extends CrudRepository<BookMaster, Integer> {

	List<BookMaster> findByAuthor(String authorName);
}
