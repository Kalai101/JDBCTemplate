package com.samco.JDBCTemplateSpringBoot.Bookrepository;

import java.util.List;

import com.samco.JDBCTemplateSpringBoot.BookModel.Book;

public interface BookRepository {
	
	int save(Book book);
	  int update(Book book);
	  Book findById(Long id);
	  int deleteById(Long id);
	  List<Book> findAll();
	  List<Book> findByPublished(boolean published);
	  List<Book> findByTitleContaining(String title);
	  int deleteAll();
	}
