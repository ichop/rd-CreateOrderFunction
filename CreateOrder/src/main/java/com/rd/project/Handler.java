package com.rd.project;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.rd.project.model.Order;
import com.rd.project.model.OrderItem;
import com.rd.project.model.User;
import com.rd.project.service.OrderService;
import com.rd.project.service.UserService;
import com.rd.project.utils.Request;
import org.springframework.dao.EmptyResultDataAccessException;


public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    private static UserService userService;
    private static OrderService orderService;


    static {
        userService = new UserService();
        orderService = new OrderService();
    }


    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent requestEvent, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {
            Gson gson = new Gson();

            Request request = gson.fromJson(requestEvent.getBody(), Request.class);
            int userId = Integer.valueOf(requestEvent.getPathParameters().get("id"));

            if(userService.userExists(userId)) {
                List<OrderItem> orderItems = request.getOrderItems();

                String orderDeliveryAddress = request.getDeliveryAddress();

                Order order = new Order(userId, LocalDateTime.now(), orderDeliveryAddress, orderItems);

                int orderId = orderService.createOrder(order);
                String output = String.format("{ \"message\": \"Order created\", \"id\": \"%s\" }", orderId);

                return response
                        .withStatusCode(200)
                        .withBody(output);

            } else throw new EmptyResultDataAccessException("No user with id " + userId + ", expected results amount: ", 1);

        } catch (Exception e) {
            return response
                    .withBody(e.getMessage())
                    .withStatusCode(500);
        }
    }
}
