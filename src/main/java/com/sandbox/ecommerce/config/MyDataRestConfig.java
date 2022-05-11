package com.sandbox.ecommerce.config;

import javax.persistence.EntityManager;
import com.sandbox.ecommerce.entity.Product;
import com.sandbox.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
            CorsRegistry cors) {
        HttpMethod[] theUnsupportedAction = {HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.POST};

        // disable HTTP method for Product: PUT, POST, DELETE
        config.getExposureConfiguration().forDomainType(Product.class)
                .withItemExposure(
                        (metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction))
                .withCollectionExposure(
                        (metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction));

        // disable HTTP method for ProductCategory: PUT, POST, DELETE
        config.getExposureConfiguration().forDomainType(ProductCategory.class)
                .withItemExposure(
                        (metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction))
                .withCollectionExposure(
                        (metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction));

        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
    }

}
