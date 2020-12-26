package me.jumen.demospringbootweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /* Formatter를 추가하는 방법 */
    /* spring boot에서는 formatter를 bean으로 등록하기만하면 끝이다 */
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter(new PersonFormatter());
//    }


    /* Interceptor를 추가하는 방법  */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GreetingInterceptor()).order(Ordered.LOWEST_PRECEDENCE);
        registry.addInterceptor(new AnotherInterceptor())
                .addPathPatterns(("/jpaTest"))
                .order(Ordered.HIGHEST_PRECEDENCE);
    }
}
