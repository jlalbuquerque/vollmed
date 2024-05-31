package med.voll.api.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LanguageConfig {

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver lrs = new AcceptHeaderLocaleResolver();
        lrs.setDefaultLocale(Locale.of("pt", "BR"));
        return lrs;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasenames("classpath:paciente/messages", "classpath:medico/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
