package de.uniba.dsg.jaxrs.resources;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.model.api.PaginatedOrders;
import de.uniba.dsg.jaxrs.model.dto.OrderDTO;
import de.uniba.dsg.jaxrs.model.dto.OrderPostDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Order;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("order")
public class OrderResource {

	private static final Log logger = LogFactory.getLog(OrderResource.class);

	
	/*
	 * Get All Orders
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrders(@Context final UriInfo info,
			@QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
			@QueryParam("page") @DefaultValue("1") final int page,
			@QueryParam("search") final String searchTerm,
			@QueryParam("filter") final FilterFunction filterFunction) {
		logger.info("Get all orders. Pagination parameter: page-" + page + " pageLimit-" + pageLimit);

		// Parameter validation
		if (pageLimit < 1 || page < 1 || page > pageLimit) {
			return createPageErrorResponse(pageLimit, page);
		}

		final PaginationHelper<Order> helper = new PaginationHelper<>(BeverageService.instance.getOrders(searchTerm,filterFunction));
		final PaginatedOrders response = new PaginatedOrders(helper.getPagination(info, page, pageLimit),
				OrderDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
		return Response.ok(response).build();
	}

	
	/*
	 * Get Order by id
	 */
	@GET
	@Path("/{orderId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getOrder(@Context final UriInfo info, @PathParam("orderId") final int id) {
		logger.info("Get order with id " + id);
		final Order order = BeverageService.instance.getOrderById(id);

		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(new OrderDTO(order, info.getBaseUri())).build();
	}



	@PUT
	@Path("{orderId}")
	public Response updateOrder(@Context final UriInfo uriInfo, @PathParam("orderId") final int id,
			final OrderPostDTO updatedOrder) {
		logger.info("Update order " + updatedOrder);
		if (updatedOrder == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
		}

		final Order order = BeverageService.instance.getOrderById(id);

		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		final Order resultOrder = BeverageService.instance.updateOrder(id);
		return Response.ok().entity(new OrderDTO(resultOrder, uriInfo.getBaseUri())).build();
	}

	/*
	 * Create Order
	 */
	@POST
	public Response createOrder(@Context final UriInfo uriInfo) {
		logger.info("Create new order ");
		
		Order order = new Order(BeverageService.instance.getAndIncrementOrdersID(),//
				new ArrayList<OrderItem>(), 0, OrderStatus.SUBMITTED);
		BeverageService.instance.addOrder(order);

		logger.info("getOrder by Id" + order.getOrderId());
		return Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderResource.class)
				.path(OrderResource.class, "getOrder").build(order.getOrderId())).build();
	}

	/*
	 * I Think this function never should be used for documentation reasons
	 */
	@DELETE
    @Path("{orderId}")
    public Response deleteSpecificOrder(@PathParam("orderId") final int id) {
        logger.info("Delete cat with id " + id);
        final boolean deleted = BeverageService.instance.deleteOrder(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }
	
	
	private Response createPageErrorResponse(final int pageLimit, final int page) {
		final ErrorMessage errorMessage;
		if(pageLimit <1) {
			errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER,
					"PageLimitis less than 1. Read the documentation for a proper handling!");
		}else if (page < 1) {
			errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER,
					"Page less than 1. Read the documentation for a proper handling!");
		}else {
			errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER,
					"PageLimit is " + pageLimit + " but page number "+ page + " was requested. Read the documentation for a proper handling!");
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
