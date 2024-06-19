package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import med.voll.api.domain.user.User;
import med.voll.api.infra.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

@Service
public class TokenService {

    private final MessageSource messageSource;

    @Value("${api.security.token.secret}")
    private String secret;
    private final String ISSUER = "API voll.med";

    public TokenService(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
    }

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getLogin())
                .withExpiresAt(expireDate())
                .sign(algorithm);
    }

    public String getSubject(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    public void validateToken(String token, HttpServletRequest request) throws InvalidTokenException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Locale locale = request.getLocale();
        String errorMessage;

        try {
            JWT.require(algorithm).build().verify(token);
        } catch (SignatureVerificationException e) {
            errorMessage = messageSource.getMessage("security.tokenjwt.signature.invalid", null, locale);
            throw new InvalidTokenException(errorMessage, e);
        } catch (TokenExpiredException e) {
            errorMessage = messageSource.getMessage("security.tokenjwt.expired", null, locale);
            throw new InvalidTokenException(errorMessage, e);
        } catch (Exception e) {
            errorMessage = messageSource.getMessage("security.tokenjwt.invalid", null, locale);
            throw new InvalidTokenException(errorMessage, e);
        }
    }

    private Instant expireDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
