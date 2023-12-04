package org.kosta.starducks.auth.service;

public interface AuthenticationService {
  boolean authenticate(String username, String password);
}
