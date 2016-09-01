package com.github.rkbalgi.ldblv2;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;

/**
 * Created by ragha on 9/1/2016.
 */
public class Ldblv2 {

    public static void main(String[] args) throws Exception {

        RouteBuilder builder = new RouteBuilder() {

            @Override
            public void configure() {


                from("http://abc.com").loadBalance().failover(-1, true, Exception.class).to("dest://dest1", "dest://dest2");
            }
        };

        CamelContext camelContext = new DefaultCamelContext();
        //camelContext.set
        camelContext.start();


        //RouteDefinition route = builder.getRouteCollection().getRoutes().get(0);
        //route.

    }
}



