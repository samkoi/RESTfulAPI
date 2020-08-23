package de.uniba.dsg.jaxrs.resources;


import javax.ws.rs.*;
import javax.ws.rs.core.*;

import de.uniba.dsg.jaxrs.controller.CustomerService;
import de.uniba.dsg.jaxrs.model.dto.BeverageDTO;
import de.uniba.dsg.jaxrs.model.dto.CustomerDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.stream.Stream;


@Path("customer")
public class CustomerResource {
    private static final Log logger = LogFactory.getLog(CustomerResource.class);

    @GET
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCustomer(@Context final UriInfo info,
                                @QueryParam("firstName") String firstName,
                                @QueryParam("lastName") String lastName) {
        logger.info("firstName" + " " + firstName);
        List<Customer> list = CustomerService.instance.getAllCustomer();
        logger.info("getAllCustomer" + list);

        String firstNameDB = null;
        String lastNameDB = null;

        for(Customer cust: list) {
            firstNameDB = cust.getFirstName();
            lastNameDB = cust.getLastName();
            logger.info("firstNameDB" + firstNameDB);
            logger.info("lastNameDB" + lastNameDB);

        }
        if (firstNameDB.equalsIgnoreCase(firstName) && lastNameDB.equalsIgnoreCase(lastName)) {
                return Response.ok().build();
        }
        else {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER,
                    "Customer Login Failed, Please provide correct login credentials");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response CreateCustomer(@Context final UriInfo info, @QueryParam("firstName")String firstName,
                                @QueryParam("lastName") String lastName,
                                @QueryParam("userName")String userName) {
        logger.info("Register Customer" + " " + userName);
        if ((firstName != null && lastName != null && userName != null)
                && ((firstName != "" && lastName != "" && userName != "")))
        {
            Customer cust =  new Customer();
            cust.setFirstName(firstName);
            cust.setLastName(lastName);
            cust.setUserName(userName);
            cust.setId(CustomerService.instance.getAndIncrementCustomerID());
            CustomerService.instance.addCustomer(cust);
            return Response.ok().build();
        } else {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER,
                    "Please provide all mandatory values");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();

        }

    }

    /*
     * Delete Customer by Id
     */
    @DELETE
    @Path("{customerId}")
    public Response deleteCustomer(@PathParam("customerId") final int id) {
        logger.info("Delete Customer with id " + id);
        final boolean deleted = CustomerService.instance.deleteCustomer(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }
        /*
         * Get Customer by Id
         */
        @GET
        @Path("{custId}")
        public Response getCustomer(@Context final UriInfo info, @PathParam("custId") final int id) {
            logger.info("get Customer with id " + id);
            Customer customer = CustomerService.instance.getCustomer(id);
            if (null == customer) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(new CustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getUserName())).build();
            }
    }
}
