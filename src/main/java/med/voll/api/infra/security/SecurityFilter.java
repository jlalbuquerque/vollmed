package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.infra.exception.InvalidAuthenticationDTO;
import med.voll.api.infra.exception.InvalidTokenException;
import med.voll.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static med.voll.api.util.Jsoniffier.toJson;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final RequestMatcher endpointsForFiltersToIgnore;

    @Autowired
    public SecurityFilter(TokenService tokenService, UserRepository userRepository, RequestMatcher endpointsForFiltersToIgnore) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.endpointsForFiltersToIgnore = endpointsForFiltersToIgnore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (endpointsForFiltersToIgnore.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        var tokenJWT = retrieveToken(request);
        if (tokenJWT != null) {
            try {
                tokenService.validateToken(tokenJWT, request);
            } catch (InvalidTokenException e) {
                handleInvalidTokenException(response, e);
                return;
            }

            var subject = tokenService.getSubject(tokenJWT);
            var user = userRepository.findByLogin(subject).get();

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String retrieveToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private void handleInvalidTokenException(HttpServletResponse response, InvalidTokenException e) throws IOException {
        InvalidAuthenticationDTO info = new InvalidAuthenticationDTO(
                Instant.now(Clock.system(ZoneId.of("-03:00"))).toString(),
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized",
                e.getMessage()
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(toJson(info));
    }
}
