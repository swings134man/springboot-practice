package boot.bootprac.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    // Model Mapper
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
