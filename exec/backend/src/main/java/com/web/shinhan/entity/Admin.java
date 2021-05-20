package com.web.shinhan.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity(name = "admin")
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int adminId;

  private String email;
  private String password;

  @Builder
  public Admin(int adminId, String email, String password) {
    this.adminId = adminId;
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "Admin [adminId=" + adminId + ", email=" + email + ", password=" + password + "]";
  }

}