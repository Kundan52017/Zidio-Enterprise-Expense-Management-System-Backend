package com.eems.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eems.exception.ResourceNotFoundException;
import com.eems.model.Expense;
import com.eems.model.ExpenseStatus;
import com.eems.repository.ExpenseRepository;
import com.eems.repository.UserRepository;
import com.eems.service.ExpenseService;

import jakarta.transaction.Transactional;

@Service

public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private UserRepository userRepository;
    private final ExpenseRepository expenseRepository;


 // Explicit constructor initialization (manually)
    public ExpenseServiceImpl(ExpenseRepository expenseRepository,UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }


//<--Create Method is working --->
   @Override
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
   /* @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + id));

        existingExpense.setTitle(expense.getTitle());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setExpenseDate(expense.getExpenseDate());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setReceiptUrl(expense.getReceiptUrl());
        existingExpense.setStatus(expense.getStatus());

        return expenseRepository.save(existingExpense);
    }*/
   //<---Update method is working--->
   @Override
   @Transactional
   public Expense updateExpense(Long id, Expense expense) {
       Expense existingExpense = expenseRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + id));

       // Preserve the existing user and ID
       expense.setId(existingExpense.getId());
       expense.setUser(existingExpense.getUser());

       // Copy properties except ID and User
       BeanUtils.copyProperties(expense, existingExpense, "id", "user");

       return expenseRepository.save(existingExpense);
   }
   // <---Delete method is working ---->
    @Override
    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {

            throw new ResourceNotFoundException("Expense not found with ID: " + id);
        }
        expenseRepository.deleteById(id);
    }
    //<---This method is working by id -->
    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
   //<---- all expenses method is showing ---> 
    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
  //<--user/1 working --->//Get-> http://localhost:8093/api/expenses/user/1
    @Override
    public List<Expense> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }
   //<--- status working --->//postman-  Get-> http://localhost:8093/api/expenses/status/APPROVED
    @Override
    public List<Expense> getExpensesByStatus(ExpenseStatus status) {
        return expenseRepository.findByStatus(status);
    }

    @Override
    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }
}
