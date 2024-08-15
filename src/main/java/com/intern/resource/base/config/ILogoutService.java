package com.intern.resource.base.config;

import com.intern.resource.base.repository.TokenRepository;
import com.intern.resource.base.util.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ILogoutService implements LogoutHandler {
  private final TokenRepository tokenRepository;

  @Override
  public void logout(HttpServletRequest request,HttpServletResponse response,Authentication authentication) {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    jwt = authHeader.substring(7);
    var storedToken = tokenRepository.findByToken(jwt)
            .orElse(null);
    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);
      storedToken.setLogoutAt(DateUtils.getNowDate());
      tokenRepository.save(storedToken);
      SecurityContextHolder.clearContext();
    }
  }
}
