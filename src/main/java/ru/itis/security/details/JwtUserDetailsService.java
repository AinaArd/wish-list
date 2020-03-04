package ru.itis.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private UsersRepository usersRepository;

	@Autowired
	public JwtUserDetailsService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		if (!usersRepository.findByLogin(login).isPresent()) {
			throw new UsernameNotFoundException("User not found with login: " + login);
		}
		User user = usersRepository.findByLogin(login).get();
		return new UserDetailsImpl(user);
	}
}