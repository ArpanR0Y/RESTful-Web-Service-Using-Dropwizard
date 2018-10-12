package com.rest;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.rest.resources.ContactResource;
import org.skife.jdbi.v2.DBI;

public class WebServiceApplication extends Application<WebServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new WebServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "testWebService";
    }

    @Override
    public void initialize(final Bootstrap<WebServiceConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final WebServiceConfiguration configuration,
                    final Environment environment) {

        final DBIFactory factory = new DBIFactory();

        final DBI jdbi = factory
                .build(environment, configuration.getDataSourceFactory(), "mysql");

        environment.jersey().register(new ContactResource(jdbi));
    }

}
