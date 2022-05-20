package com.sandbox.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import com.sandbox.ecommerce.entity.Product;
import com.sandbox.ecommerce.entity.ProductCategory;
import com.sandbox.ecommerce.entity.Country;
import com.sandbox.ecommerce.entity.State;
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

        // disable HTTP method for Product and ProductCategory: PUT, POST, DELETE
        disableHttpMethods(Product.class, config, theUnsupportedAction);
        disableHttpMethods(ProductCategory.class, config, theUnsupportedAction);
        disableHttpMethods(Country.class, config, theUnsupportedAction);
        disableHttpMethods(State.class, config, theUnsupportedAction);

        // call an internal helper method
        exposeIds(config);

        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
    }

	private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedAction) {
		config.getExposureConfiguration()
        		.forDomainType(theClass)
                .withItemExposure(
                        (metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction))
                .withCollectionExposure(
                        (metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction));
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
