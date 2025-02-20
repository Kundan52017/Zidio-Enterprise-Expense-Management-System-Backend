package com.eems.model;


import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expenses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String category;  // Travel, Food, Office Supplies, etc.

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate expenseDate;

    private String description;

    @Column(nullable = false)
    private String receiptUrl;  // Link to PDF/Image of the receipt

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)// Foreign Key linking expense to user
    @JsonIgnore //ADD THIS LINE TO IGNORE LAZY-LOADED USER FIELD<---Update method--->
    private User user;  // The employee who created the expense

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseStatus status ;//= ExpenseStatus.PENDING; // Default status

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

   public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ExpenseStatus getStatus() {
		return status;
	}

	public void setStatus(ExpenseStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", title=" + title + ", amount=" + amount + ", category=" + category
				+ ", expenseDate=" + expenseDate + ", description=" + description + ", receiptUrl=" + receiptUrl
				+ ", user=" + user + ", status=" + status + "]";
	}

}
