package com.web.shinhan.model;

import java.time.LocalDateTime;

import com.web.shinhan.entity.User;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class UserDto {
	private int userId;
	private int employeeNum;
	private String email;
	private String userName;
	private String password;
	private String department;
	private String position;
	private String contact;
	private String days;
	private int balance;
	private int cardLimit;
	private int active;
	private LocalDateTime accessTime;
	private LocalDateTime limitTime;

	@Builder
	public UserDto(int userId, int employeeNum, String email, String userName, String password, String department,
			String position, String contact, String days, int balance, int cardLimit, int active,
			LocalDateTime accessTime, LocalDateTime limitTime) {
		this.userId = userId;
		this.employeeNum = employeeNum;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.department = department;
		this.position = position;
		this.contact = contact;
		this.days = days;
		this.balance = balance;
		this.cardLimit = cardLimit;
		this.active = active;
		this.accessTime = accessTime;
		this.limitTime = limitTime;
	}

	public User toEntity() {
		return User.builder().userId(userId).employeeNum(employeeNum).email(email).userName(userName).password(password)
				.department(department).position(position).contact(contact).days(days).balance(balance)
				.cardLimit(cardLimit).active(active).accessTime(accessTime).limitTime(limitTime).build();
	}

	public static UserDto of(User user) {
		return UserDto.builder().userId(user.getUserId()).employeeNum(user.getEmployeeNum()).email(user.getEmail())
				.userName(user.getUserName()).password(user.getPassword()).department(user.getDepartment())
				.position(user.getPosition()).contact(user.getContact()).days(user.getDays()).balance(user.getBalance())
				.cardLimit(user.getCardLimit()).active(user.getActive()).accessTime(user.getAccessTime())
				.limitTime(user.getLimitTime()).build();
	}

}
