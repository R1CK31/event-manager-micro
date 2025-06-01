package com.example.eventmanager.service;

import com.example.eventmanager.client.TicketServiceClient; // Import the Feign Client
import com.example.eventmanager.dto.TicketDto; // Import the Ticket DTO
import com.example.eventmanager.entity.Payment;
import com.example.eventmanager.entity.PaymentLog;
import com.example.eventmanager.repository.PaymentLogRepository;
import com.example.eventmanager.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentLogRepository paymentLogRepository;
    private final TicketServiceClient ticketServiceClient; // Inject the Feign Client

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          PaymentLogRepository paymentLogRepository,
                          TicketServiceClient ticketServiceClient) { // Inject Feign Client
        this.paymentRepository = paymentRepository;
        this.paymentLogRepository = paymentLogRepository;
        this.ticketServiceClient = ticketServiceClient; // Assign Feign Client
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Transactional
    // Modified createPayment to take ticketId and userId
    public Payment createPayment(Long ticketId, Long userId) {
        // Fetch ticket details to get the price
        TicketDto ticket = ticketServiceClient.getTicketById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));

        // Create payment with the ticket's price
        Payment payment = new Payment();
        payment.setTicketId(ticketId); // Assuming Payment entity has ticketId
        payment.setUserId(userId);
        payment.setAmount(ticket.getPrice()); // Set amount from ticket price
        payment.setPaymentDate(LocalDateTime.now());
        // You might need to set eventId on Payment based on the ticket's eventId
        payment.setEventId(ticket.getEventId());


        Payment savedPayment = paymentRepository.save(payment);

        PaymentLog log = new PaymentLog();
        log.setPaymentId(savedPayment.getId());
        log.setLogMessage("Payment created for ticket ID " + ticketId + " with amount: " + savedPayment.getAmount());
        log.setCreatedAt(LocalDateTime.now());
        paymentLogRepository.save(log);

        return savedPayment;
    }

    @Transactional
    public Payment updatePayment(Long id, Payment paymentDetails) {
        // Consider if updating payment details after creation is allowed or makes sense
        // If allowed, you might want to re-validate against the ticket price
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // For update, you might allow updating other details but perhaps not the amount
        // or enforce that the amount must still match the ticket price.
        // Here, I'll keep the existing update logic but be mindful of its implications.
        payment.setAmount(paymentDetails.getAmount()); // This might need adjustment
        payment.setPaymentDate(paymentDetails.getPaymentDate());
        payment.setEventId(paymentDetails.getEventId());
        payment.setUserId(paymentDetails.getUserId());

        Payment updatedPayment = paymentRepository.save(payment);

        PaymentLog log = new PaymentLog();
        log.setPaymentId(updatedPayment.getId());
        log.setLogMessage("Payment updated. New amount: " + updatedPayment.getAmount());
        log.setCreatedAt(LocalDateTime.now());
        paymentLogRepository.save(log);

        return updatedPayment;
    }

    @Transactional
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
        PaymentLog log = new PaymentLog();
        log.setPaymentId(id);
        log.setLogMessage("Payment with ID " + id + " deleted.");
        log.setCreatedAt(LocalDateTime.now());
        paymentLogRepository.save(log);
    }
}
