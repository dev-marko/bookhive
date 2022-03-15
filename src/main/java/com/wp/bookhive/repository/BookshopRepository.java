package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.BookShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookshopRepository extends JpaRepository<BookShop,Integer> {

}
