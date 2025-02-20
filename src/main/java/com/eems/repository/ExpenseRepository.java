package com.eems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eems.model.Expense;
import com.eems.model.ExpenseStatus;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);

    List<Expense> findByStatus(ExpenseStatus status);

    List<Expense> findByCategory(String category);
}
