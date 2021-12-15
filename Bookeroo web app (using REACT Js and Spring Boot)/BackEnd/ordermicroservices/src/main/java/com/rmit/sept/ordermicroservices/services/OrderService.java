package com.rmit.sept.ordermicroservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.paypal.payments.CapturesRefundRequest;
import com.rmit.sept.ordermicroservices.Repositories.PurchasedBookRepository;
import com.rmit.sept.ordermicroservices.Repositories.TransactionRepository;
import com.rmit.sept.ordermicroservices.config.PayPalClient;
import com.rmit.sept.ordermicroservices.model.PurchasedBook;
import com.rmit.sept.ordermicroservices.model.Transaction;
import com.paypal.payments.Refund;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    PayPalClient payPalClient;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PurchasedBookRepository purchasedBookRepository;


    // Retrieve order from Paypal API
    public Order getOrder(String orderId) {
        log.warn("Can cause IOException");
        OrdersGetRequest request = new OrdersGetRequest(orderId);
        try {
            HttpResponse<Order> response = payPalClient.client().execute(request);
            log.info("Successfully retrieved PayPal Order from ID");
            return response.result();
        } catch (IOException e) {
            log.error("IOException occurred: Invalid PayPal ID");
        }
        return null;
    }

    // Store necessary information from PayPal transaction to our database
    public void saveOrder(String orderId, String userId) {

        // Check if the PayPal orderId already exists in the database
        AtomicBoolean orderExists = new AtomicBoolean(false);
        transactionRepository.findAll().forEach(transaction -> {
            if (transaction.getPayPalOrderId().equals(orderId)) {
                orderExists.set(true);
                return;
            }
        });

        Order paypalOrder = getOrder(orderId);
        if (!orderExists.get() && paypalOrder != null) {
            // Extracting PayPal transaction detail into a new transaction entry.
            List<PurchasedBook> list = new ArrayList<>();
            Transaction userTransaction = new Transaction();
            userTransaction.setPayPalOrderId(paypalOrder.id());
            userTransaction.setPayPalEmail(paypalOrder.payer().email());
            userTransaction.setUserId(userId);
            int totalCost = 0;
            for (PurchaseUnit unit : paypalOrder.purchaseUnits()) {
                for (Item item : unit.items()) {
                    PurchasedBook book = new PurchasedBook();
                    book.setIsbn(item.sku());
                    book.setSeller(unit.referenceId());
                    book.setCondition(item.description());
                    book.setQuantity(Integer.parseInt(item.quantity()));
                    book.setCost(Double.parseDouble(item.unitAmount().value()) * book.getQuantity());
                    totalCost += book.getCost();
                    book.setCurrency(item.unitAmount().currencyCode());
                    book.setTransaction(userTransaction);
                    book.setDeliveryStatus("Processing");
                    list.add(book);
                }
            }
            userTransaction.setTotalCost(totalCost);
            userTransaction.setCurrency(paypalOrder.purchaseUnits().get(0).items().get(0).unitAmount().currencyCode());

            // Concatenating the address
            userTransaction.setShippingName(paypalOrder.purchaseUnits().get(0).shippingDetail().name().fullName());
            String address = paypalOrder.purchaseUnits().get(0).shippingDetail().addressPortable().addressLine1() +  " " +
                    paypalOrder.purchaseUnits().get(0).shippingDetail().addressPortable().adminArea2() + " " +
                    paypalOrder.purchaseUnits().get(0).shippingDetail().addressPortable().adminArea1() + " " +
                    paypalOrder.purchaseUnits().get(0).shippingDetail().addressPortable().postalCode() + " " +
                    paypalOrder.purchaseUnits().get(0).shippingDetail().addressPortable().countryCode();
            userTransaction.setAddress(address);
            userTransaction.setCreate_At(new Date());

            transactionRepository.save(userTransaction);
            for (PurchasedBook book : list) {
                purchasedBookRepository.save(book);
            }
            log.info("Successfully stored the transaction to database");
        }
    }

    public List<Transaction> getTransactionsByUserId(String userId) {
        List<Transaction> userTransactions = new ArrayList<>();
        transactionRepository.findAll().forEach(transaction -> {
            if (!transaction.getUserId().equals(userId)) {
                userTransactions.add(transaction);
            }
        });
        if (userTransactions.isEmpty()) {log.info("No transaction for users is returned.");}
        return userTransactions;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        transactionRepository.findAll().forEach(transaction -> allTransactions.add(transaction));
        if (allTransactions.isEmpty()) {log.info("No transaction is returned.");}
        return allTransactions;
    }

    public String refundOrder(String orderID) {
        Transaction order = transactionRepository.getById(Long.parseLong(orderID,10));

        // Check if 2 hours have passed since ordering
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getCreate_At());
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        Calendar calendar2 = Calendar.getInstance();
        int result = calendar2.compareTo(calendar);
        log.info("Date result: " + result + "if result is 1, then it has been over 2 hours");

        // Proceed if the time hasn't been 2 hours yet
        if (result <= 0) {
            Order paypalOrder = getOrder(order.getPayPalOrderId());
            // Iterate through the PayPal order to get each refund id
            List<String> refundIds = new ArrayList<>();
            Iterator<PurchaseUnit> it = paypalOrder.purchaseUnits().iterator();
            while (it.hasNext()) {
                PurchaseUnit unit = it.next();
                Iterator<Capture> captureIterator = unit.payments().captures().iterator();
                while (captureIterator.hasNext()) {
                    Capture cap = captureIterator.next();
                    refundIds.add(cap.id());
                }
            }

            // Calling PayPal API to refund each capture sales individually
            for (String refundId : refundIds) {
                CapturesRefundRequest request = new CapturesRefundRequest(refundId);
                request.prefer("return=representation");
                try {
                    HttpResponse<Refund> response = payPalClient.client().execute(request);
                    log.info("Status Code: " + response.statusCode());
                    log.info("Status: " + response.result().status());
                    log.info("Refund Id: " + response.result().id());
                } catch (IOException e) {
                    log.error("IOException Occurred");
                }
            }

            for(PurchasedBook bookOrder: order.getPurchases()) {
                bookOrder.setDeliveryStatus("Cancelled");
                purchasedBookRepository.save(bookOrder);
            }
        } else {
            return "Error: Order has been placed for more than 2 hours";
        }
        return "Refund Successful";
    }
}
