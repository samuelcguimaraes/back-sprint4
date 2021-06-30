package br.com.rchlo.store.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Embedded
    @AttributeOverride(name = "clientName", column = @Column(name = "card_client_name"))
    @AttributeOverride(name = "number", column = @Column(name = "card_number"))
    @AttributeOverride(name = "expiration", column = @Column(name = "card_expiration"))
    @AttributeOverride(name = "verificationCode", column = @Column(name = "card_verification_code"))
    private Card card;

    /** @deprecated */
    protected Payment() {
    }

    public Payment(BigDecimal value,Card card) {
        this.value = value;
        this.card = card;
        this.status = PaymentStatus.CREATED;
    }

    public Long getId() {
        return this.id;
    }
    
    public BigDecimal getValue() {
        return this.value;
    }
    
    public PaymentStatus getStatus() {
        return this.status;
    }
    
    public boolean confirm() {
        
        if (this.isCanceled()) {
            return false;
        }
        
        this.status = PaymentStatus.CONFIRMED;
        
        return true;
    }
    
    public boolean cancel() {
        
        if (this.isConfirmed()) {
            return false;
        }
        
        this.status = PaymentStatus.CANCELED;
        
        return true;
    }
    
    public boolean isConfirmed() {
        return PaymentStatus.CONFIRMED.equals(this.getStatus());
    }
    
    public boolean isCanceled() {
        return PaymentStatus.CANCELED.equals(this.getStatus());
    }
}
