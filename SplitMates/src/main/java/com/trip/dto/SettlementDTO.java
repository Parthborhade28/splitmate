package com.trip.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettlementDTO {
    private String fromUser;
    private String toUser;
    private Double amount;
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "SettlementDTO [fromUser=" + fromUser + ", toUser=" + toUser + ", amount=" + amount + "]";
	}
	public SettlementDTO(String fromUser, String toUser, Double amount) {
		super();
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.amount = amount;
	}
	public SettlementDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}

