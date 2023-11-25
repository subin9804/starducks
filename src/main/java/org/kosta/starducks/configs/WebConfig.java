package org.kosta.starducks.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Value("${file.upload.url}")
    private String fileUploadUrl;


    @Bean
    public MessageSource messageSource () {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.commons", "messages.errors");
        return ms;
    }

    /**
     * 정적 리소스(이미지, CSS 파일, JavaScript 파일 등)에 대한 요청을 처리
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileUploadUrl + "**")
                .addResourceLocations("file:///" + fileUploadPath);

        /* '/images/**'로 호출하는 자원은 '/static/images/**' 폴더 아래에서 찾는다. */
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
