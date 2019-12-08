package br.com.unidev.base.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.unidev.base.model.Usuario;
import br.com.unidev.base.repository.UsuarioRepository;

@Service
public class CustomUserDatailsService  implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuarioSalvo = Optional.of(repo.findByLogin(username)).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado!"));
		return new User(usuarioSalvo.getLogin(), usuarioSalvo.getSenha(), true,true,true,true, getAuthorities(usuarioSalvo));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuarioSalvo) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authorities;
	}

}
