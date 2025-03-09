package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.AuthenticationDTO;
import com.example.betmdtnhom3.dto.IntrospectDTO;
import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.IntrospectRequest;
import com.example.betmdtnhom3.dto.request.LogoutRequest;
import com.example.betmdtnhom3.entity.InvalidatedToken;
import com.example.betmdtnhom3.entity.User;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.responsitory.InvalidatedTokenReponsitory;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.AuthenticationServiceImpl;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationService implements AuthenticationServiceImpl {
    @Autowired
    UserReponsitory userReponsitory;
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY;
    @Autowired
    InvalidatedTokenReponsitory invalidatedTokenReponsitory;

    @Override
    public AuthenticationDTO authenticate(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        var users = userReponsitory.findByTel(authenticationRequest.getTel())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticate = passwordEncoder.matches(authenticationRequest.getPassword(), users.getPassword());
        if (!authenticate){
            throw new AppException(ErrorCode.AUTHENTICATED);
        }
        authenticationDTO.setAuthenticated(authenticate);
        authenticationDTO.setToken(generateToken(users));
        authenticationDTO.setId(users.getId());
        return authenticationDTO;
    }

    @Override
    public IntrospectDTO introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        IntrospectDTO introspectDTO = new IntrospectDTO();
        if (invalidatedTokenReponsitory.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            introspectDTO.setValid(false);
        } else {
            Date expiriTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            String role = signedJWT.getJWTClaimsSet().getStringClaim("scope");
            var verified = signedJWT.verify(verifier);
            introspectDTO.setValid(verified && expiriTime.after(new Date()));
            introspectDTO.setScope(role);
        }

        return introspectDTO ;
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException {
        var token = request.getToken();
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiriTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        String jit = signedJWT.getJWTClaimsSet().getJWTID();

        InvalidatedToken invalidatedToken = new InvalidatedToken();
        invalidatedToken.setId(jit);
        invalidatedToken.setExpiryTime(expiriTime);
        invalidatedTokenReponsitory.save(invalidatedToken);
    }

    private String generateToken(User users){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getTel())
                .issuer("sneakerStudio.vn")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", users.getRole())
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }
}
