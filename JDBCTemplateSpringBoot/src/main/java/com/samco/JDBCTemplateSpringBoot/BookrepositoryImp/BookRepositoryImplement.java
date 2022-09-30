package com.samco.JDBCTemplateSpringBoot.BookrepositoryImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.samco.JDBCTemplateSpringBoot.BookModel.Book;
import com.samco.JDBCTemplateSpringBoot.Bookrepository.BookRepository;

@Repository
public class BookRepositoryImplement implements BookRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(Book book) {
		return jdbcTemplate.update("INSERT INTO BOOKS (title, description, published) VALUES(?,?,?)",
		        new Object[] { book.getTitle(), book.getDescription(), book.isPublished() });
		  }

	@Override
	public int update(Book book) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("UPDATE BOOKS SET title=?, description=?, published=? WHERE id=?",
				new Object[] { book.getTitle(), book.getDescription(), book.isPublished(), book.getId() });
	}

	@Override
	public Book findById(Long id) {
		// TODO Auto-generated method stub
		try {
			Book book = jdbcTemplate.queryForObject("SELECT * FROM BOOKS WHERE id=?",
					BeanPropertyRowMapper.newInstance(Book.class), id);
			return book;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("DELETE FROM BOOKS WHERE id=?", id);
	}

	@Override
	public List<Book> findAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("SELECT * from BOOKS", BeanPropertyRowMapper.newInstance(Book.class));
	}

	@Override
	public List<Book> findByPublished(boolean published) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("SELECT * from BOOKS WHERE published=?",
				BeanPropertyRowMapper.newInstance(Book.class), published);
	}

	@Override
	public List<Book> findByTitleContaining(String title) {
		String q = "SELECT * from BOOKS WHERE title LIKE '%" + title + "%'";
		return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Book.class));
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("delete from BOOKS");
	}

}
