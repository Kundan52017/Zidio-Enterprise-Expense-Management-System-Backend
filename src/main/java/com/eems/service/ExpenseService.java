package com.eems.service;

import java.util.List;
import java.util.Optional;

import com.eems.model.Expense;
import com.eems.model.ExpenseStatus;

public interface ExpenseService {

    Expense createExpense(Expense expense);
    Expense updateExpense(Long id, Expense expense);
    void deleteExpense(Long id);
    Optional<Expense> getExpenseById(Long id);
    List<Expense> getAllExpenses();
    List<Expense> getExpensesByUser(Long userId);
    List<Expense> getExpensesByStatus(ExpenseStatus status);
    List<Expense> getExpensesByCategory(String category);


}






