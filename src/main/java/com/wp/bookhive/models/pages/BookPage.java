package com.wp.bookhive.models.pages;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class BookPage {
    private Integer currentPage = 0;
    private Integer pageSize = 9;   // change it to 10 when you're done testing
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "name";
}
