//// package com.ecommerce.authservice.client
//package com.ecommerce.authservice.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Configuration
//public class UserFeignConfig {
//
//    @Bean
//    public RequestInterceptor requestInterceptor(Environment env) {
//        return new RequestInterceptor() {
//            @Override
//            public void apply(RequestTemplate template) {
//                // 1) Try to forward incoming Authorization header (if present):
//                RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
//                if (attrs instanceof ServletRequestAttributes sra) {
//                    String auth = sra.getRequest().getHeader("Authorization");
//                    if (auth != null) {
//                        template.header("Authorization", auth);
//                        return;
//                    }
//                }
//                // 2) Fallback: use an internal token (configured in properties)
//                String svcToken = env.getProperty("services.internal-token");
//                if (svcToken != null) {
//                    template.header("X-Service-Token", svcToken);
//                }
//            }
//        };
//    }
//}
