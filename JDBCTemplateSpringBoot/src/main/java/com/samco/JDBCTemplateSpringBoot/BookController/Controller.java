package com.samco.JDBCTemplateSpringBoot.BookController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samco.JDBCTemplateSpringBoot.BookModel.Book;
import com.samco.JDBCTemplateSpringBoot.BookrepositoryImp.BookRepositoryImplement;

@RestController
@RequestMapping("/book")
public class Controller {

	@Autowired
	private BookRepositoryImplement bookRepositoryImplement;
	
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllTutorials(@RequestParam(required = false) String title) {
	    try {
	      List<Book> book = new ArrayList<Book>();
	      if (title == null)
	    	  bookRepositoryImplement.findAll().forEach(book::add);
	      else
	    	  bookRepositoryImplement.findByTitleContaining(title).forEach(book::add);
	      if (book.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(book, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  @GetMapping("/{id}")
	  public ResponseEntity<Book> getTutorialById(@PathVariable("id") long id) {
	    Book book = bookRepositoryImplement.findById(id);
	    if (book != null) {
	      return new ResponseEntity<>(book, HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  @PostMapping
	  public ResponseEntity<String> createTutorial(@RequestBody Book book) {
	    try {
	    	bookRepositoryImplement.save(new Book(book.getTitle(), book.getDescription(), false));
	      return new ResponseEntity<>("Tutorial was created successfully.", HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @PutMapping("/tutorials/{id}")
	  public ResponseEntity<String> updateTutorial(@PathVariable("id") long id, @RequestBody Book book) {
	    Book _tutorial = bookRepositoryImplement.findById(id);
	    if (_tutorial != null) {
	      _tutorial.setId(id);
	      _tutorial.setTitle(book.getTitle());
	      _tutorial.setDescription(book.getDescription());
	      _tutorial.setPublished(book.isPublished());
	      bookRepositoryImplement.update(_tutorial);
	      return new ResponseEntity<>("Tutorial was updated successfully.", HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>("Cannot find Tutorial with id=" + id, HttpStatus.NOT_FOUND);
	    }
	  }
	  @DeleteMapping("/tutorials/{id}")
	  public ResponseEntity<String> deleteTutorial(@PathVariable("id") long id) {
	    try {
	      int result = bookRepositoryImplement.deleteById(id);
	      if (result == 0) {
	        return new ResponseEntity<>("Cannot find Tutorial with id=" + id, HttpStatus.OK);
	      }
	      return new ResponseEntity<>("Tutorial was deleted successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>("Cannot delete tutorial.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  @DeleteMapping("/tutorials")
	  public ResponseEntity<String> deleteAllTutorials() {
	    try {
	      int numRows = bookRepositoryImplement.deleteAll();
	      return new ResponseEntity<>("Deleted " + numRows + " Tutorial(s) successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>("Cannot delete tutorials.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  @GetMapping("/tutorials/published")
	  public ResponseEntity<List<Book>> findByPublished() {
	    try {
	      List<Book> tutorials = bookRepositoryImplement.findByPublished(true);
	      if (tutorials.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
