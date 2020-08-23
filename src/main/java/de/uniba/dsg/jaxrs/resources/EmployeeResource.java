package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.CustomerService;
import de.uniba.dsg.jaxrs.controller.EmployeeService;
import de.uniba.dsg.jaxrs.model.dto.CustomerDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Customer;
import de.uniba.dsg.jaxrs.model.logic.Employee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/employee")
public class EmployeeResource {

    private static final Log logger = LogFactory.getLog(EmployeeResource.class);
    @GET
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEmployee(@Context final UriInfo info,
                                @QueryParam("firstName") String firstName,
                                @QueryParam("lastName") String lastName) {
        logger.info("getEmployee" + " " + firstName);
        List<Employee> list = EmployeeService.instance.getAllEmployees();
        logger.info(list);
        String firstNameDB = null;
        String lastNameDB = null;

        for(Employee emp: list) {
            firstNameDB = emp.getFirstName();
            lastNameDB = emp.getLastName();
        }
        logger.info("firstNameDB" + " " + firstNameDB);

        if (firstNameDB.equalsIgnoreCase(firstName) && lastNameDB.equalsIgnoreCase(lastName)) {
            return Response.ok().build();
        }
        else {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER,
                    "Employee Login Failed, Please provide correct login credentials");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CreateEmployee(@Context final UriInfo info,
                                   @QueryParam("userName")String userName,
                                   @QueryParam("firstName") String firstName,
                                   @QueryParam("lastName")String lastName) {
        logger.info("Register Employee" + " " + userName);
        if ((firstName != null && lastName != null && userName != null)
                && ((firstName != "" && lastName != "" && userName != "")))
        {
            Employee employee =  new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setUserName(userName);
            employee.setId(EmployeeService.instance.getAndIncrementEmployeeID());
            EmployeeService.instance.addNewEmployee(employee);
            return Response.ok().build();
        } else {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER,
                    "Please provide all mandatory values");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();

        }
    }

    /*
     * Delete Employee by Id
     */
    @DELETE
    @Path("{employeeId}")
    public Response deleteCustomer(@PathParam("employeeId") final int id) {
        logger.info("Delete Employee with id " + id);
        final boolean deleted = EmployeeService.instance.deleteEmployee(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }

    /*
     * Get Employee by Id
     */
    @GET
    @Path("{empId}")
    public Response getEmployee(@Context final UriInfo info, @PathParam("empId") final int id) {
        logger.info("get employee with id " + id);
        Employee employee = EmployeeService.instance.getEmployee(id);
        if (null == employee) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(new CustomerDTO(employee.getFirstName(), employee.getLastName(), employee.getUserName())).build();
        }
    }
}