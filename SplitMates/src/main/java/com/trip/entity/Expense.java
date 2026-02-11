package com.trip.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tripId;
    private Long paidBy;
    private Double amount;
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();

	public Expense() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Expense(Long id, Long tripId, Long paidBy, Double amount, String description, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.tripId = tripId;
		this.paidBy = paidBy;
		this.amount = amount;
		this.description = description;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public Long getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(Long paidBy) {
		this.paidBy = paidBy;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", tripId=" + tripId + ", paidBy=" + paidBy + ", amount=" + amount
				+ ", description=" + description + ", createdAt=" + createdAt + "]";
	}

}

