package com.sivalabs.bookmarker.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query("SELECT new com.sivalabs.bookmarker.domain.BookmarkDTO(bookmark.id, " +
            "bookmark.title, bookmark.url, bookmark.createdAt)  " +
            "FROM Bookmark bookmark")
    Page<BookmarkDTO> findBookmarks(Pageable pageable);

    @Query("""
            SELECT new com.sivalabs.bookmarker.domain.BookmarkDTO(bookmark.id, 
            bookmark.title, bookmark.url, bookmark.createdAt) FROM Bookmark bookmark
            WHERE lower(bookmark.title) like lower(concat( '%', :query, '%')) 
            """)
    Page<BookmarkDTO> searchQuery(String query, Pageable pageable);

    //method идентичен searchQuery
    Page<BookmarkVM> findByTitleContainingIgnoreCase(String query, Pageable pageable);
}
