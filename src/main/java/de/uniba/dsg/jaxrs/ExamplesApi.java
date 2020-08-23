package de.uniba.dsg.jaxrs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import de.uniba.dsg.jaxrs.resources.BeverageResource;
import de.uniba.dsg.jaxrs.resources.CustomerResource;
import de.uniba.dsg.jaxrs.resources.EmployeeResource;
import de.uniba.dsg.jaxrs.resources.OrderItemResource;
import de.uniba.dsg.jaxrs.resources.OrderResource;
import de.uniba.dsg.jaxrs.resources.SwaggerUI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamplesApi extends Application {

    private static final Log logger = LogFactory.getLog(ExamplesApi.class);

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();
        logger.info("in example API");
        resources.add(CustomerResource.class);
        resources.add(EmployeeResource.class);
        resources.add(BeverageResource.class);
        resources.add(OrderResource.class);
        resources.add(OrderItemResource.class);
        resources.add(SwaggerUI.class);
        return resources;
    }
}
