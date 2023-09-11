package com.sivalabs.bookmarker.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkMapper bookmarkMapper;

    @Transactional(readOnly = true)
    public BookmarksDTO getBookmarks(Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
        Page<BookmarkDTO> bookmarkPage = bookmarkRepository.findBookmarks(pageable);
        return new BookmarksDTO(bookmarkPage);
    }

    @Transactional
    public BookmarksDTO searchQuery(String query, Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
        Page<BookmarkDTO> bookmarkPage = bookmarkRepository.searchQuery(query, pageable);
        //вместо dto можно использовать интерфейс BookmarkVM. Hibernate сам создаст прокси и будет такая виртуальная dto.
        Page<BookmarkVM> bookmarkVMPage = bookmarkRepository.findByTitleContainingIgnoreCase(query, pageable);
        return new BookmarksDTO(bookmarkPage);
    }

    public BookmarkDTO createBookmark(CreateBookmarkRequest request) {
        Bookmark bookmark = new Bookmark(null, request.getTitle(), request.getUrl(), Instant.now());
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        return bookmarkMapper.toDTO(savedBookmark);
    }
}
