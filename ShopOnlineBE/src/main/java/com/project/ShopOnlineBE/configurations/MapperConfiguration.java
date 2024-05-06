package com.project.ShopOnlineBE.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    //Có thế tự động chèn ModelMapper ở bất kỳ đâu
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
