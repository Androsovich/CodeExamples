package com.epam.summer.lab.demonstrations;

import com.epam.summer.lab.models.Author;
import com.epam.summer.lab.models.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.epam.summer.lab.constants.Constants.FILE_NAME_AUTHORS;
import static com.epam.summer.lab.constants.Constants.FILE_NAME_BOOKS;
import static com.epam.summer.lab.utils.RandomUtils.pickNRandomElements;
import static com.epam.summer.lab.utils.RandomUtils.randomizer;
import static com.epam.summer.lab.utils.generators.ObjectGenerator.getListObjects;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class DemonstrateTask {
    private static final Logger logger = LoggerFactory.getLogger(DemonstrateTask.class);
    private final List<Author> authors;
    private final List<Book> books;

    public DemonstrateTask() {
        this.authors = getListObjects(FILE_NAME_AUTHORS, Author::new);
        this.books = getListObjects(FILE_NAME_BOOKS, Book::new);
    }

    public void run() {
        this.initAuthorBooks();
        this.initAuthorsOfBook();

        final int numberPages = 200;
        // 1) check if there are books with more than 200 pages;
        this.checkBooksNumberPages(book -> numberPages < book.getNumberPages());

        // 2) check if all books have more than 200 pages;
        this.checkAllBooksNumberPages(book -> numberPages < book.getNumberPages());

        // 3) find the books with the largest and smallest number of pages
        this.BooksWithMaxNumberPages(this::getMapNumberPagesBooks);
        this.BooksWithMinNumberPages(this::getMapNumberPagesBooks);

        // 4) find books with one author;
        this.findBooksWithOneAuthor();

        // 5) sort books by number of pages, and then by title;
        this.compareBooksByNumberPagesThenName();

        //6) get a list of all unique book titles;
        this.getUniqueBookNames();

        //7) get a unique list of authors of books with fewer than 200 pages.
        this.getUniqueAuthorsByBookPredicate(book -> numberPages > book.getNumberPages());
    }

    private void initAuthorsOfBook() {
        for (Book book : books) {
            book.setAuthors(pickNRandomElements(authors, randomizer()));
        }
        books.forEach(book -> logger.info("Books init Authors : {}", book));
    }

    private void initAuthorBooks() {

        for (Author author : authors) {
            author.setBooks(pickNRandomElements(books, randomizer()));
        }

        authors.forEach(author -> logger.info("Author init Books : {} ", author));
    }

    private void checkBooksNumberPages(Predicate<Book> isNumberPagesGreaterN) {
        boolean isBooksExist = books
                .stream()
                .anyMatch(isNumberPagesGreaterN);
        logger.info("books number pages greater than 200 : {} ", isBooksExist);
    }

    private void checkAllBooksNumberPages(Predicate<Book> isNumberPagesGreaterN) {
        boolean isAllBooksGreaterThanN = books
                .stream()
                .allMatch(isNumberPagesGreaterN);
        logger.info("all books number pages greater than 200 : {} ", isAllBooksGreaterThanN);
    }

    private TreeMap<Integer, List<Book>> getMapNumberPagesBooks(List<Book> bookList) {
        return
                bookList
                        .stream()
                        .collect(groupingBy(Book::getNumberPages, TreeMap::new, toList()));
    }

    private void BooksWithMinNumberPages(Function<List<Book>, TreeMap<Integer, List<Book>>> listTreeMapFunction) {
        listTreeMapFunction
                .apply(books)
                .firstEntry()
                .getValue()
                .forEach(book-> logger.info("book with min number pages : {}", book));
    }

    private void BooksWithMaxNumberPages(Function<List<Book>, TreeMap<Integer, List<Book>>> listTreeMapFunction) {
        listTreeMapFunction
                .apply(books)
                .lastEntry()
                .getValue()
                .forEach(book-> logger.info("book with max number pages : {}", book));
    }

    private void compareBooksByNumberPagesThenName() {
        books
                .stream()
                .sorted(Comparator
                        .comparing(Book::getNumberPages)
                        .thenComparing(Book::getName))
                .forEach(book-> logger.info("comparing books : {}", book));
    }

    private void getUniqueBookNames() {
        books
                .stream()
                .map(Book::getName)
                .distinct()
                .forEach(name-> logger.info("unique names books : {}", name));
    }

    private void getUniqueAuthorsByBookPredicate(Predicate<Book> isNumberPagesLowerN) {
        books
                .stream()
                .filter(isNumberPagesLowerN)
                .flatMap(book -> book.getAuthors().stream())
                .distinct()
                .forEach(author-> logger.info("unique list of authors of books with fewer than 200 pages : {}", author));
    }

    private void findBooksWithOneAuthor() {
        final int ONE = 1;
        books
                .stream()
                .filter(book -> book.getAuthors().size() == ONE)
                .collect(Collectors.toList())
                .forEach(book-> logger.info(" books  with one author : {}", book));
    }
}