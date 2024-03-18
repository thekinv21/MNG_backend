package com.mng.Mng.service;

import com.mng.Mng.model.Token;
import com.mng.Mng.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;

    }


    public boolean isTokenExpired(String tokenId) {
        return tokenRepository.findByTokenId(tokenId).stream().anyMatch(Token::isExpired);

    }
    public boolean isTokenRevoke(String tokenId) {
        return tokenRepository.findByTokenId(tokenId).stream().anyMatch(Token::isRevoked);

    }
    public void revokeToken(String tokenId) {
        var token = tokenRepository.findByTokenId(tokenId).orElseThrow(() -> new RuntimeException("token not found"));
        if(isTokenRevoke(tokenId) && isTokenExpired(tokenId)) {
            tokenRepository.delete(token);
        }
    }

}