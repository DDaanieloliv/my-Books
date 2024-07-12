package com.DDaaniel.My_Books.controller;


import com.DDaaniel.My_Books.Model.domain.dto.BookDTO;
import com.DDaaniel.My_Books.Model.domain.dto.MessageResponseDTO;
import com.DDaaniel.My_Books.Model.exception.BookNotFoundException;
import com.DDaaniel.My_Books.Model.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Create a new Book",
            description = "Creates a new Book entity with the provided data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Book created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "Example 201", value =
                                            "{\n" +
                                                    "  \"message\": \"Book created successfully with ID 1\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content()
                    )
            }
    )
    @Transactional
    @PostMapping
    public MessageResponseDTO create(@RequestBody @Valid BookDTO book) {

       return bookService.create(book);

    }

    @Operation(
            summary = "Get Book by ID",
            description = "Fetches a Book entity by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "Example 200", value =
                                            "{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"name\": \"Book Example\",\n" +
                                                    "  \"pages\": 100,\n" +
                                                    "  \"chapters\": 10,\n" +
                                                    "  \"isbn\": \"123-4567890123\",\n" +
                                                    "  \"publisherName\": \"Publisher Example\",\n" +
                                                    "  \"author\": {\n" +
                                                    "    \"id\": 2,\n" +
                                                    "    \"name\": \"Author Example\"\n" +
                                                    "  }\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid ID supplied",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) throws BookNotFoundException {
        return bookService.findById(id);
    }

}
