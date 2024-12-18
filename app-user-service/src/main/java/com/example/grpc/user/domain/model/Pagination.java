package com.example.grpc.user.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class Pagination<T> {
    private Integer currentPage;
    private Integer pageSize;
    private Long totalItems;
    private Integer totalPages;
    private List<T> data;

    @JsonIgnore
    public static <T> Pagination<T> page(Page<T> page) {
        final Pagination<T> pagination = new Pagination<>();
        pagination.setCurrentPage(page.getPageable().getPageNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalItems(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        pagination.setData(page.getContent());
        return pagination;
    }

}
