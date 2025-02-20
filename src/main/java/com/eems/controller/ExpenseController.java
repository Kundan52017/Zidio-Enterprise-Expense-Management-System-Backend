package com.eems.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eems.model.Expense;
import com.eems.model.ExpenseStatus;
import com.eems.service.ExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {


	private final ExpenseService expenseService;
	//private final UserService userService;

    ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /*@PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }*/
    /*<-----This is correct code for creating expense method-----> */
    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        if (expense.getExpenseDate() == null) {
            throw new IllegalArgumentException("Expense date cannot be null");
        }
        Expense savedExpense = expenseService.createExpense(expense);
        return ResponseEntity.ok(savedExpense);
    }
  //<-------Update method is working ---->
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(id, expense);
        return ResponseEntity.ok(updatedExpense);
    }
  //<---Delete Method is working--->
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
   //<----GetMapping by Id will working--->
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.getExpenseById(id);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
   //<---GetMapping Expense---- directly all expense is working---> 
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }
    //<---user/1 This method is working --->
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable Long userId) {
        List<Expense> expenses = expenseService.getExpensesByUser(userId);
        return ResponseEntity.ok(expenses);
    }
  //<--- get mapping --http://localhost:8093/api/expenses/status/APPROVED --->
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Expense>> getExpensesByStatus(@PathVariable ExpenseStatus status) {
        List<Expense> expenses = expenseService.getExpensesByStatus(status);
        return ResponseEntity.ok(expenses);
    }
   //<---GetMethod-  http://localhost:8093/api/expenses/category/Food  --->
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable String category) {
        List<Expense> expenses = expenseService.getExpensesByCategory(category);
        return ResponseEntity.ok(expenses);
    }
}
