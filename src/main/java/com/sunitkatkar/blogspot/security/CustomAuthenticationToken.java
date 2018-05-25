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

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * {@link CustomAuthenticationToken} is provided to the
 * {@link AuthenticationProvider} so that the user can be authenticated. This
 * token is enhanced by including the additional <code>tenant</code> field
 * extracted by the {@link CustomAuthenticationFilter} from the user submitted
 * login form.
 * 
 * @author Sunit Katkar
 * @version 1.0
 * @since 1.0 (May 2018)
 */
public class CustomAuthenticationToken
        extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    /**
     * The tenant i.e. database identifier
     */
    private String tenant;

    /**
     * @param principal
     * @param credentials
     * @param tenant
     */
    public CustomAuthenticationToken(Object principal, Object credentials,
            String tenant) {
        super(principal, credentials);
        this.tenant = tenant;
        super.setAuthenticated(false);
    }

    /**
     * @param principal
     * @param credentials
     * @param tenant
     * @param authorities
     */
    public CustomAuthenticationToken(Object principal, Object credentials,
            String tenant, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.tenant = tenant;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getTenant() {
        return this.tenant;
    }
}