package com.example.demo.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Model.Order;
import com.example.demo.Service.IOrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    RestTemplate restTemplate;
    /**
     * To Add Order Details by Using User Token
     * @param orderdto
     * @return
     */
    @PostMapping("/addOrder")
    public ResponseEntity<ResponseDTO> addOrder(@Valid @RequestBody OrderDTO orderdto){
        Order order = orderService.addOrder(orderdto);
        ResponseDTO dto = new ResponseDTO("Your Order Has Placed Successfully ",  order.getOrderId());
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }
    /**
     * To Cancel the Order Details by Using User Token
     * @param orderId
     * @param userId
     * @return
     */
    @PutMapping("/cancelOrder/{orderId}/{userId}")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable int orderId,@PathVariable int userId) {
        String order = orderService.cancelOrder(orderId,userId);
        ResponseDTO response= new ResponseDTO("Cancelled Successfully ",  order);
        return new ResponseEntity<> (response,HttpStatus.OK);
    }

    /**
     * To Get All Order Details
     * @return
     */
    @GetMapping("/getAllOrder")
    public ResponseEntity<ResponseDTO> getAllOrder(){
        List<Order> Order = orderService.getAllOrder();
        ResponseDTO dto = new ResponseDTO("All records retrieved successfully !",Order);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    /**
     * To get All User Order Using User Token
     * @param token
     * @return
     */
    @GetMapping("/userOrders/{userId}")
    public ResponseEntity<ResponseDTO> getUserOrders(@PathVariable int userId){
        List<Order> userOrders = orderService.getUserOrders(userId);
        ResponseDTO response = new ResponseDTO("Orders of user ", userOrders);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    /**
     * To Get Particular Order Details by Id
     * @param id
     * @return
     */
    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable Integer orderId){
        Order newOrder = orderService.getOrderById(orderId);
        ResponseDTO dto = new ResponseDTO("Record retrieved successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.OK);
    }


    /**
     * To Delete Order Details
     * @param id
     * @return
     */
    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<ResponseDTO> deleteOrder(@PathVariable Integer orderId){
        Order deleteOrder = orderService.deleteOrder(orderId);
        ResponseDTO dto = new ResponseDTO("Record deleted successfully !",deleteOrder);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
}