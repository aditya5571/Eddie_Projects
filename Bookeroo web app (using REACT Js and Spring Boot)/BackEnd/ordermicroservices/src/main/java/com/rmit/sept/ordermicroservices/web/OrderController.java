package com.rmit.sept.ordermicroservices.web;

import com.paypal.http.HttpResponse;
import com.paypal.payments.Refund;
import com.rmit.sept.ordermicroservices.model.Cart;
import com.rmit.sept.ordermicroservices.model.PurchasedBook;
import com.rmit.sept.ordermicroservices.model.Transaction;
import com.rmit.sept.ordermicroservices.payload.CheckoutRequest;
import com.rmit.sept.ordermicroservices.payload.RefundRequest;
import com.rmit.sept.ordermicroservices.security.JwtUtil;
import com.rmit.sept.ordermicroservices.services.CartService;
import com.rmit.sept.ordermicroservices.services.MapValidationErrorService;
import com.rmit.sept.ordermicroservices.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    // Saves all successful PayPal transaction sent from the client
    @PostMapping("/checkout")
    public void saveOrder(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        log.info("POST Request to  /checkout, PayPalID " + checkoutRequest.getPaypalOrderId() + " UserID: " + checkoutRequest.getUserId());
        orderService.saveOrder(checkoutRequest.getPaypalOrderId(), checkoutRequest.getUserId());
        cartService.deleteCart(Long.parseLong(checkoutRequest.getUserId()));
    }

    @GetMapping("/user/all")
    public List<Transaction> getAllTransactions(){
        log.info("GET Request to /user/all");
        return orderService.getAllTransactions();
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable String userId) {
        log.info("GET Request /user" + userId);
        return orderService.getTransactionsByUserId(userId);
    }

    // Retrieves all users for frontend
    @PostMapping("/refund")
    public String sendRefund(@Valid @RequestBody RefundRequest refundRequest) {
        log.info("GET Request to /refund, orderID: " + refundRequest.getOrderId());
        return orderService.refundOrder(refundRequest.getOrderId());
    }

    @PostMapping("/addToCart")
    private ResponseEntity<?> addToCart(@Valid @RequestBody List<Long> ads, BindingResult result, @RequestHeader("authorization") String token){
        if(token==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Cart newCart = cartService.saveCart(JwtUtil.getUserIdFromJWT(token.replace("Bearer","").trim()), ads);
        return new ResponseEntity<Cart>(newCart, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCartItem/{adId}")
    private ResponseEntity<?> deleteCartItem(@RequestHeader("authorization") String token, @PathVariable String adId){

        if(token==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        cartService.deleteItem(JwtUtil.getUserIdFromJWT(token.replace("Bearer","").trim()), Long.valueOf(adId));
        return new ResponseEntity<>(HttpStatus.OK);
//        cartService.saveCart(1L, adIds);
    }

    @GetMapping("/cartItems")
    private List<Long> getCartItems(@RequestHeader("authorization") String token){
        if(token==null){
            return null;
        }
        return cartService.getCartItems(JwtUtil.getUserIdFromJWT(token.replace("Bearer","").trim()));
    }
}
