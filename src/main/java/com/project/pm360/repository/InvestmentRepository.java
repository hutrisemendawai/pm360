package com.project.pm360.repository;

import com.project.pm360.model.Investment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    Optional<Investment> findFirstByAssetOrderByRecordedAtDesc(String asset);
}