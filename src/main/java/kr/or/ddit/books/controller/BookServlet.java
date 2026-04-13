package kr.or.ddit.books.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.dto.BookRequestDto;
import kr.or.ddit.books.dto.BookResponseDto;
import kr.or.ddit.books.service.BookService;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/books")
public class BookServlet extends HttpServlet {
    ViewResolver resolver = new ViewResolverComposite();
    BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchQuery = StringUtils.defaultIfBlank(req.getParameter("search"), req.getParameter("searchQuery"));
        String searchType = req.getParameter("searchType");
        log.info("Received search request - Type: {}, Query: {}", searchType, searchQuery);
        if (StringUtils.isBlank(searchQuery) || StringUtils.isBlank(searchType)) {
            List<BookResponseDto> books = bookService.getAllBooks();
            req.setAttribute("books", books);
        } else {
            BookRequestDto requestDto = BookRequestDto.builder()
                    .searchQuery(searchQuery)
                    .searchType(searchType)
                    .build();
            log.info("Searching books with query: {}", searchQuery);
            List<BookResponseDto> books = bookService.searchBooks(requestDto);
            req.setAttribute("books", books);
        }
        String lvn = "books/books";
        resolver.resolveViewName(lvn, req, resp);

    }
}
