package com.intern.resource.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern.resource.base.common.Constant;
import com.intern.resource.base.common.MSISResponse;
import com.intern.resource.base.config.IJwtService;
import com.intern.resource.base.dto.UserDTO;
import com.intern.resource.base.entity.Role;
import com.intern.resource.base.entity.Token;
import com.intern.resource.base.entity.User;
import com.intern.resource.base.entity.enums.TokenType;
import com.intern.resource.base.exception.ApplicationErrorException;
import com.intern.resource.base.mapper.UserMapper;
import com.intern.resource.base.repository.TokenRepository;
import com.intern.resource.base.repository.UserRepository;
import com.intern.resource.base.util.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public MSISResponse register(UserDTO dto) {
        var existed = repository.findByEmail(dto.getEmail());
        if(!ObjectUtils.isEmpty(existed))
            throw new ApplicationErrorException(Constant.USER_EXISTED);
        User user = UserMapper.dtoToEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDisable(true);
        if(CollectionUtils.isEmpty(user.getRoles())){
            Role role = Role.builder()
                    .roleName("USER")
                    .description("test_user")
                    .build();
            role.setUser(user);
        }else{
            for (Role role : user.getRoles()) {
                role.setUser(user);
            }
        }
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return MSISResponse.success(Constant.USER_REGISTERED,AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    public MSISResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return MSISResponse.success(Constant.AUTH_SUCCESS,AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .loginAt(DateUtils.getNowDate())
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            token.setLogoutAt(DateUtils.getNowDate());
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public MSISResponse getAll() {
        List<User> users = repository.findAll();
        return ObjectUtils.isEmpty(users)?
                MSISResponse.fail("Fail!"):
                MSISResponse.success("Success",users);
    }
}
