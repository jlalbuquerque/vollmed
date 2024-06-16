package med.voll.api.infra;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @Autowired
    public DefaultAuthenticationEntryPoint(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        JsonObject info = new JsonObject();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        info.addProperty("timestamp", LocalDateTime.now().format(formatter));
        info.addProperty("status", HttpServletResponse.SC_UNAUTHORIZED);
        info.addProperty("error", "Unauthorized");
        info.addProperty("message", messageSource.getMessage("erro.naoautenticado", null, localeResolver.resolveLocale(request)));

        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(info.toString());
    }
}
