package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Card;
import br.com.rchlo.store.domain.Payment;
import br.com.rchlo.store.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class PaymentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PaymentRepository paymentRepository;
	
	private Payment payment;
	private URI uri;
	private static final Long SEARCHED_ID = 1L;
	
	@BeforeEach
	public void setUp() throws URISyntaxException {
		
		this.payment = this.buildPayment();
		
		this.uri = new URI("/payments/1");
	}
	
	@Test
	public void shouldNotCancelPaymentAlreadyConfirmed() throws Exception {
		
		doReturn(Optional.of(this.payment)).when(this.paymentRepository).findById(SEARCHED_ID);
		this.payment.confirm();
		
		this.mockMvc
				.perform(MockMvcRequestBuilders.delete(this.uri))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldNotConfirmPaymentAlreadyCanceled() throws Exception {
		
		doReturn(Optional.of(this.payment)).when(this.paymentRepository).findById(SEARCHED_ID);
		this.payment.cancel();
		
		this.mockMvc
				.perform(MockMvcRequestBuilders.put(this.uri))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldNotFindNonExistingPayment() throws Exception {
		
		doReturn(Optional.empty()).when(this.paymentRepository).findById(SEARCHED_ID);
		this.payment.cancel();
		
		this.mockMvc
				.perform(MockMvcRequestBuilders.put(this.uri))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	public void shouldCancelPayment() throws Exception {
		
		doReturn(Optional.of(this.payment)).when(this.paymentRepository).findById(SEARCHED_ID);
		
		this.mockMvc
				.perform(MockMvcRequestBuilders.delete(this.uri))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void shouldConfirmPayment() throws Exception {
		
		doReturn(Optional.of(this.payment)).when(this.paymentRepository).findById(SEARCHED_ID);
		
		this.mockMvc
				.perform(MockMvcRequestBuilders.put(this.uri))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	private Payment buildPayment() {
		
		final var card = new Card("Fulano", "1234555567890000", "2022-12", "123");
		
		return new Payment(BigDecimal.TEN, card);
	}
}