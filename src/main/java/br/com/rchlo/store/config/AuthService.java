package br.com.rchlo.store.config;

import br.com.rchlo.store.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
	
	private UserRepository repository;
	
	public AuthService(final UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return this.repository.findByUsername(username)
		                      .orElseThrow(() -> new UsernameNotFoundException("Dados inválidos!"));
	}
}
