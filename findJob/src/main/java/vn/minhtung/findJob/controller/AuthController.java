package vn.minhtung.findJob.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.minhtung.findJob.domain.User;
import vn.minhtung.findJob.domain.dto.LoginDTO;
import vn.minhtung.findJob.domain.dto.ResLoginDTO;
import vn.minhtung.findJob.service.UserService;
import vn.minhtung.findJob.util.SecutiryUtil;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationMangerBuilder;
    private final SecutiryUtil secutiryUtil;

    private final UserService userService;

    @Value("${minhtung.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpriation;


    public AuthController(AuthenticationManagerBuilder authenticationMangerBuilder, SecutiryUtil secutiryUtil, UserService userService){
        this.authenticationMangerBuilder = authenticationMangerBuilder;
        this.secutiryUtil = secutiryUtil;
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getName(), loginDto.getPassword());

        Authentication authentication = authenticationMangerBuilder.getObject().authenticate(authenticationToken);

        String access_token = this.secutiryUtil.createAccessToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResLoginDTO res = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(loginDto.getName());
        if (currentUserDB != null){
            ResLoginDTO.UserLogin userLogin = res.new UserLogin(currentUserDB.getId(), currentUserDB.getEmail(),currentUserDB.getName());
            res.setUser(userLogin);
        }
        res.setAccessToken(access_token);

        String refresh_token = this.secutiryUtil.createRefreshToken(loginDto.getName(), res);
        this.userService.updateUserToken(refresh_token, loginDto.getName());

        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpriation)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(res);
    }


}
