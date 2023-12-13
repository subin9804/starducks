package org.kosta.starducks.header.dto;

public class PasswordCheckRequest {

  private String currentPassword;

  public PasswordCheckRequest() {
  }

  // getter and setter
  public String getCurrentPassword() {
    return currentPassword;
  }

  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }

}
