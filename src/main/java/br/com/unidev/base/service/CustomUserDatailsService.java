package br.com.unidev.base.service;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.unidev.base.model.Permissao;
import br.com.unidev.base.model.Usuario;
import br.com.unidev.base.repository.UsuarioRepository;

@Service
public class CustomUserDatailsService  implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuarioSalvo = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos"));
		return new User(email, usuarioSalvo.getSenha(), true,true,true,true, getAuthorities(usuarioSalvo));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuarioSalvo) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (Permissao permissao : usuarioSalvo.getPermissoes()) {
			authorities.add(new SimpleGrantedAuthority(permissao.getDescricao()));			
		}
		return authorities;
	}

}
