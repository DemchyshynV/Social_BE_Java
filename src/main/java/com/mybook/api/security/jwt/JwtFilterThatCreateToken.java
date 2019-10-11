package com.mybook.api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybook.api.dto.LoginationDTO;
import com.mybook.api.models.User;
import com.mybook.api.services.UserService;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

public class JwtFilterThatCreateToken extends AbstractAuthenticationProcessingFilter {
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private UserService userService;

    protected JwtFilterThatCreateToken(String defaultFilterProcessesUrl, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserService userService) {
        super(defaultFilterProcessesUrl);
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;

    }

    LoginationDTO loginationDTO = new LoginationDTO();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String body = httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        body = new String(Base64.getDecoder().decode(body.getBytes()));
        loginationDTO = new ObjectMapper().readValue(body, LoginationDTO.class);
        String email = loginationDTO.getEmail();
        String password = loginationDTO.getPassword();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return authenticate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("yes");
        User user = userService.findByEmail(loginationDTO.getEmail());
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        JSONObject jsonToken = new JSONObject();
        jsonToken.put("Authorization", "Bearer_" + token);
        response.getWriter().write(jsonToken.toString());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(502);
        response.sendError(111, "error");
    }
}
