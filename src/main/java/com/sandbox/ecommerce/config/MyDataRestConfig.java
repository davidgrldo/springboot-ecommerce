package com.sandbox.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
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

        // call an internal helper method
        exposeIds(config);

        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // expose entity ids
        //

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entites = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entites) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/doman type
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }

}
