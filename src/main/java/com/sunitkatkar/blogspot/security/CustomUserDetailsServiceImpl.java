/* 
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sunitkatkar.blogspot.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sunitkatkar.blogspot.tenant.model.CustomUserDetails;
import com.sunitkatkar.blogspot.tenant.model.Role;
import com.sunitkatkar.blogspot.tenant.model.User;
import com.sunitkatkar.blogspot.tenant.service.UserService;

/**
 * {@link CustomUserDetailsService} contract defines a single method called
 * loadUserByUsernameAndTenantname.
 * 
 * The {@link CustomUserDetailsServiceImpl} class simply implements the contract
 * and delegates to {@link UserService} to get the
 * {@link com.example.model.User} from the database so that it can be compared
 * with the {@link org.springframework.security.core.userdetails.User} for
 * authentication. Authentication occurs via the
 * {@link CustomUserDetailsAuthenticationProvider}.
 * 
 * @author Sunit Katkar
 * @version 1.0
 * @since 1.0 (May 2018)
 *
 */
@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsernameAndTenantname(String username,
            String tenant) throws UsernameNotFoundException {
        if (StringUtils.isAnyBlank(username, tenant)) {
            throw new UsernameNotFoundException(
                    "Username and domain must be provided");
        }
        // Look for the user based on the username and tenant by accessing the
        // UserRepository via the UserService
        User user = userService.findByUsernameAndTenantname(username, tenant);

        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format(
                            "Username not found for domain, "
                                    + "username=%s, tenant=%s",
                            username, tenant));
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(
                user.getUsername(), user.getPassword(), grantedAuthorities,
                tenant);

        return customUserDetails;
    }
}