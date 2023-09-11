package com.sivalabs.bookmarker.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/bookmarks")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookmarkService bookmarkService;
    private final BookmarkRepository bookmarkRepository;

    @GetMapping
    //http://localhost:8080/api/bookmarks?query=boot
    public BookmarksDTO getBookmarks(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "query", defaultValue = "") String query) {
        if (query == null || query.trim().length() == 0){
            return bookmarkService.getBookmarks(page);
        } else {
            return bookmarkService.searchQuery(query, page);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookmarkDTO createBookmark(@RequestBody @Valid CreateBookmarkRequest request){
        return bookmarkService.createBookmark(request);
    }
}
