package org.androsovich.applications.config;

import org.androsovich.applications.auditing.ApplicationAuditAware;
import org.androsovich.applications.dto.bid.BidRequest;
import org.androsovich.applications.entities.Bid;
import org.androsovich.applications.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.androsovich.applications.constants.Constants.CREATE;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<BidRequest, Bid> propertyMapper = mapper.createTypeMap(BidRequest.class, Bid.class, CREATE);
        propertyMapper.addMappings(modelMapper -> modelMapper.skip(Bid::setId));
        return mapper;
    }

    @Bean
    public AuditorAware<User> auditorAware() {
        return new ApplicationAuditAware();
    }
}
