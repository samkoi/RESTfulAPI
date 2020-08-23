package de.uniba.dsg.jaxrs.resources;


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
import de.uniba.dsg.jaxrs.model.api.PaginatedOrderItems;
import de.uniba.dsg.jaxrs.model.dto.OrderItemDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("orderitem")
public class OrderItemResource {

	private static final Log logger = LogFactory.getLog(OrderItemResource.class);

	/*
	 * Get orderItem by id
	 */
	@GET
	@Path("{orderId}/{orderItemId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getOrderItem(@Context final UriInfo info, @PathParam("orderItemId") final int id,
			@PathParam("orderId") final int orderId) {
		logger.info("Get order item with id " + id);
		
		if(BeverageService.instance.getOrderById(orderId) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		final OrderItem orderItem = BeverageService.instance.getOrderItemById(orderId, id);

		if (orderItem == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(new OrderItemDTO(orderId, orderItem, info.getBaseUri())).build();
	}

	/*
	 * Get All OrderItems
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{orderId}")
	public Response getOrderItems(@Context final UriInfo info,
			@QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
			@QueryParam("page") @DefaultValue("1") final int page, @PathParam("orderId") final int orderId) {
		logger.info("Get all order items. Pagination parameter: page-" + page + " pageLimit-" + pageLimit);

		if(BeverageService.instance.getOrderById(orderId) == null ) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		// Parameter validation
		if (pageLimit < 1 || page < 1) {
			return returnPaginationInvalidParameterResponse();
		}

		final PaginationHelper<OrderItem> helper = new PaginationHelper<>(
				BeverageService.instance.getOrderItems(orderId));
		final PaginatedOrderItems response = new PaginatedOrderItems(helper.getPagination(info, page, pageLimit),
				OrderItemDTO.marshall(orderId, helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
		return Response.ok(response).build();
	}

	/*
	 * update an OrderItem --> Check if enough Capacity available to update. False --> Error Response, True --> Update Beverage Stock and Order Price
	 */
	@PUT
	@Path("{orderId}/{orderItemId}")
	public Response updateOrderItem(@Context final UriInfo uriInfo, @PathParam("orderItemId") final int orderItemId,
			@PathParam("orderId") final int orderId, @QueryParam("newOrderAmount") final int newOrderAmount) {
		logger.info("Update order Item with order id:" + orderId + " order number:" + orderItemId);

		if(BeverageService.instance.getOrderById(orderId) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		if (isOrderAlreadyProcessed(orderId)) {
			return returnForbiddenResponse();
		}

		final OrderItem orderItem = BeverageService.instance.getOrderItemById(orderId, orderItemId);
		if (orderItem == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		int stockChange = newOrderAmount - orderItem.getQuantity();
		if (stockChange > BeverageService.instance.getAvailableBeverageStock(orderItem.getBeverage())) {
			return returnStockInvalidOperationResponse(newOrderAmount, orderItem);
		}else {
			final OrderItem resultOrderItem = BeverageService.instance.updateStockAndOrderPrice(orderId, orderItemId, newOrderAmount);
			return Response.ok().entity(new OrderItemDTO(orderId, resultOrderItem, uriInfo.getBaseUri())).build();
		}
	}

	/*
	 * Create bottle Order Item --> Check if enough Capacity available to create Order Item. False --> Error Response, True --> Update Beverage Stock and Order Price
	 */
	@POST
	@Path("bottle/{orderId}/{bottleId}")
	public Response createBottleOrderItem(@Context final UriInfo uriInfo,
			@PathParam("orderId") final int orderId,
			@PathParam("bottleId") final int bottleId,
			@QueryParam("amount") final int amount) {
		logger.info("Create bottle order item");
		if(BeverageService.instance.getOrderById(orderId) == null || BeverageService.instance.getBottleById(bottleId) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		if (isOrderAlreadyProcessed(orderId)) {
			return returnForbiddenResponse();
		}

		int availableQuantity = BeverageService.instance.getBottleById(bottleId).getInStock();
		if (amount > availableQuantity) {
			return Response.status(Response.Status.CONFLICT)//
					.entity(new ErrorMessage(ErrorType.INVALID_OPERATION,
							"Not Enough Stock. Requested:" + amount//
									+ " but only " + availableQuantity + " were available"))//
					.build();
		}else {
		OrderItem orderItem = BeverageService.instance.addBottleOrderItem(orderId, bottleId, amount);
		return Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderItemResource.class)
				.path(OrderItemResource.class, "getOrderItem").build(orderId, orderItem.getNumber())).build();
		}
	}

	/*
	 * Create bottle Order Item --> Check if enough Capacity available to create Order Item. False --> Error Response, True --> Update Beverage Stock and Order Price
	 */
	@POST
	@Path("crate/{orderId}/{crateId}")
	public Response createCrateOrderItem(@Context final UriInfo uriInfo,
			@PathParam("orderId") final int orderId,
			@PathParam("crateId") final int crateId,
			@QueryParam("amount") final int amount) {
		logger.info("Create new crate order item ");
		if(BeverageService.instance.getOrderById(orderId) == null || BeverageService.instance.getCrateById(crateId) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		if (isOrderAlreadyProcessed(orderId)) {
			return returnForbiddenResponse();
		}
		
		int availableQuantity = BeverageService.instance.getCrateById(crateId).getInStock();
		if (amount > availableQuantity) {
			return Response.status(Response.Status.CONFLICT)//
					.entity(new ErrorMessage(ErrorType.INVALID_OPERATION,
							"Not Enough Stock. Requested:" + amount//
									+ " but only " + availableQuantity + " were available"))//
					.build();
		}else {
		OrderItem orderItem = BeverageService.instance.addCrateOrderItem(orderId, crateId, amount);
		return Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderItemResource.class)
				.path(OrderItemResource.class, "getOrderItem").build(orderId, orderItem.getNumber())).build();
		}
	}

	/*
	 * Delete Order Item --> increment Beverage Stock and Decrement Order Price
	 */
	@DELETE
	@Path("{orderId}/{orderItemId}")
	public Response deleteSpecificOrder(@PathParam("orderItemId") final int id, @PathParam("orderId") final int orderId) {
		logger.info("Delete order Item with id " + id);
		
		if(BeverageService.instance.getOrderById(orderId) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		if (isOrderAlreadyProcessed(orderId)) {
			return returnForbiddenResponse();
		}
		final boolean deleted = BeverageService.instance.deleteOrderItem(orderId, id);

		if (!deleted) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.ok().build();
		}
	}
	
	/*
	 * Delete Order Item --> increment Beverage Stock and Decrement Order Price
	 */
	@DELETE
	public Response deleteOrderItem() {
		logger.info("Delete order Item without");
		return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
	}
	

	private Response returnStockInvalidOperationResponse(int newOrderAmount,
			final OrderItem orderItem) {
		return Response.status(Response.Status.CONFLICT)//
				.entity(new ErrorMessage(ErrorType.INVALID_OPERATION,
						"Not Enough Stock. Requested:" + newOrderAmount//
								+ " but only " + orderItem.getQuantity() + " were available"))//
				.build();
	}


	private Response returnForbiddenResponse() {
		return Response.status(Response.Status.FORBIDDEN)//
				.entity(new ErrorMessage(ErrorType.INVALID_OPERATION,
						"Order is Already Processed and can't be changed anymore"))//
				.build();
	}
	
	private Response returnPaginationInvalidParameterResponse() {
		final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER,
				"PageLimit or page is less than 1. Read the documentation for a proper handling!");
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

	

	private boolean isOrderAlreadyProcessed(int orderId) {
		return BeverageService.instance.isOrderAlreadyProcessed(orderId);
	}


}
