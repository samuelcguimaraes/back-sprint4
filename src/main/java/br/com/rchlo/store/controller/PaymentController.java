package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Payment;
import br.com.rchlo.store.dto.PaymentDto;
import br.com.rchlo.store.form.PaymentForm;
import br.com.rchlo.store.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/payments/{id}")
    public PaymentDto detail(@PathVariable("id") Long id) {
        Payment payment = this.paymentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new PaymentDto(payment);
    }

    @Transactional
    @PostMapping("/payments")
    public ResponseEntity<PaymentDto> create(@RequestBody @Valid PaymentForm form, UriComponentsBuilder uriBuilder) {
        Payment payment = form.converter();
        this.paymentRepository.save(payment);
        URI uri = uriBuilder.path("/payments/{id}").buildAndExpand(payment.getId()).toUri();
        return ResponseEntity.created(uri).body(new PaymentDto(payment));
    }

    @Transactional
    @PutMapping("/payments/{id}")
    public ResponseEntity<PaymentDto> confirm(@PathVariable("id") Long id) {
    
        Payment payment = this.paymentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
        return payment.confirm() ? ResponseEntity.ok(new PaymentDto(payment)) : ResponseEntity.badRequest().build();
    }

    @Transactional
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<PaymentDto> cancel(@PathVariable("id") Long id) {
    
        Payment payment = this.paymentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
        return payment.cancel() ? ResponseEntity.ok(new PaymentDto(payment)) : ResponseEntity.badRequest().build();
    }

}
