package org.nop.eshop.service;

import org.nop.eshop.dao.UserDAO;
import org.nop.eshop.model.Role;
import org.nop.eshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class SDBPUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;


    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User domainUser = userDAO.getUser(login);

        return new org.springframework.security.core.userdetails.User(
                domainUser.getEmail(),
                domainUser.getPassword(),
                true, //enabled
                true, //accountNonExpired
                true, //credentialsNonExpired
                true, //accountNonLocked
                getAuthorities(domainUser.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return getGrantedAuthorities(roles);
    }

//    public List<String> getRoles(Integer role) {
//        List<String> roles = new ArrayList<>();
//
//        if (role == 1) {
//            roles.add("ROLE_ADMIN");
//            roles.add("ROLE_USER");
//        } else if (role == 2) {
//            roles.add("ROLE_USER");
//        }
//
//        return roles;
//    }

    public static List<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
}
