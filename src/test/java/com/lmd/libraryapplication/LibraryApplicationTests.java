package com.lmd.libraryapplication;

import com.lmd.libraryapplication.model.dtos.AuthorDTO;
import com.lmd.libraryapplication.model.repositories.AuthorRepository;
import com.lmd.libraryapplication.model.repositories.BookRepository;
import com.lmd.libraryapplication.service.AuthorService;
import com.lmd.libraryapplication.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LibraryApplicationTests {
	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	AuthorService authorService;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BookService bookService;

	@Test
	@DisplayName("Using WebClient ")
	void usingWebClient(){
		String response=WebClient.create("http://localhost:8080/authors")
				.get()
				.retrieve()
				.toString();

		System.out.println(response);
	}
	@Test
	@DisplayName("test author endpoint")
	void testAuthorEndpoint(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AuthorDTO> response=restTemplate.getForEntity("http://localhost:8080/author/7", AuthorDTO.class);
		Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
	}
	@Test
	@DisplayName("Check for valid author when creating a book ")
	void checkForValidAuthorFalse(){
		Assertions.assertFalse(bookService.isAuthorIdValid(5));
	}

	@Test
	@DisplayName("Check for valid author id")
	void checkForValidAuthorTrue(){
	    Assertions.assertTrue(bookService.isAuthorIdValid(4));
	}

	@Test
	@DisplayName("Check if book has been added ")
	void checkAddBooks(){
	    bookService.addBook("Cat in the hat", 4);
		int lastIndex=bookRepository.findAll().size()-1;
		String bookName = bookRepository.findAll().get(lastIndex).getTitle();
		System.out.println(bookName);
	}

	@Test
	@DisplayName("Initial test")
	void initialTest() {
		System.out.println(authorRepository.findAll());
		System.out.println(authorRepository.findById(1));
	}
	@Test
	@DisplayName("Check for author with ID 1")
	void checkForAuthorByID1() {
		Assertions.assertEquals("Lanya", authorRepository.findById(1).get().getFullName());
	}

	@Test
	@DisplayName("Check for all authors")
	void checkForAllAuthor(){
		System.out.println(authorRepository.findAll());
	}

	@Test
	@DisplayName("Check for all authors")
	void checkForAllAuthorPageable(){
		System.out.println(authorRepository.findAll());//pagea
	}

	@Test
	@DisplayName("Check for number of authors")
	void checkForAuthorsCount(){
		Assertions.assertTrue(authorRepository.count()==3);
	}

	@Test
	@DisplayName("Check if delete method works")
	void checkDelete(){
		AuthorDTO lanya = new AuthorDTO();
		lanya.setId(1);
		lanya.setFullName("Lanya");
		authorRepository.delete(lanya);

//		Assertions.assertTrue(repository.count()==3);
	}

	@Test
	@DisplayName("Check if author exists")
	void checkExistById(){
		Assertions.assertTrue(authorRepository.existsById(2));
	}

	@Test
	@DisplayName("Check if author exists")
	void checkExistByName(){
		AuthorDTO lanya = new AuthorDTO();
		lanya.setId(1);
		lanya.setFullName("Lanya");
//		Assertions.assertEquals(false, repository.exists());
	}

	@Test
	@DisplayName("Check for finding author by name")
	void checkForAuthorByName(){
		Assertions.assertEquals("Miss Lou", authorRepository.findByNameSQL("Miss Lou").get().getFullName());
	}
	@Test
	@DisplayName("Check for finding author by name false")
	void checkForAuthorByNameFalse(){
		Assertions.assertEquals("Lanya", authorRepository.findByNameSQL("Lanya").get().getFullName());
	}
	@Test
	@DisplayName("Check for finding author by name false")
	void checkForAuthorByNameAndID(){
		Assertions.assertEquals(2, authorRepository.findAuthorDTOByFullNameAndId("Miss Lou",2).get().getId());
		Assertions.assertEquals("Miss Lou", authorRepository.findAuthorDTOByFullNameAndId("Miss Lou",2).get().getFullName());
	}

	@Test
	@DisplayName("Check for length of author name")
	void checkForLengthOfAuthorName(){
	    assertEquals(8, authorService.getLengthOfAuthorName(2));
	}


	@Test
	@DisplayName("Check for printing books")
	void checkForPrintingBooks(){
	    bookRepository.findById(1).ifPresent(System.out::println);
	}

	@Test
	@DisplayName("Check for Books for one Author")
	void checkForPrintAuthor(){
	    authorRepository.findById(4).ifPresent(System.out::println);
	}


}
