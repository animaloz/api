package com.example.openapi.core.config;

import com.example.openapi.core.adapter.AppWebMvcConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class AppConfig implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{AppWebMvcConfigurerAdapter.class.getCanonicalName()};
    }
}
