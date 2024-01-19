package com.example.application.data.repository;

import com.example.application.data.entity.Query;
import com.example.application.data.entity.StoredQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<StoredQuery, Long> {
}
