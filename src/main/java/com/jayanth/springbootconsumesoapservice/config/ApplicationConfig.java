package com.jayanth.springbootconsumesoapservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter {
 
    private final String serviceEndpoint = "http://localhost:8080/soapWS";
    private final String marshallerPackagesToScan= "com.jayanth.springbootconsumesoapservice.wsdl";
    private final String unmarshallerPackagesToScan= "com.jayanth.springbootconsumesoapservice.wsdl";
 
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
 
    @Bean
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.afterPropertiesSet();
        return messageFactory;
    }
 
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(marshallerPackagesToScan);
        return marshaller;
    }
 
    @Bean
    public Jaxb2Marshaller unmarshaller() {
        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setPackagesToScan(unmarshallerPackagesToScan);
        return unmarshaller;
    }
 
    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(
                messageFactory());
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(unmarshaller());
        webServiceTemplate.setMessageSender(messageSender());
        webServiceTemplate.setDefaultUri(serviceEndpoint);
        return webServiceTemplate;
    }
 
    @Bean
    public HttpComponentsMessageSender messageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        return httpComponentsMessageSender;
    }
}