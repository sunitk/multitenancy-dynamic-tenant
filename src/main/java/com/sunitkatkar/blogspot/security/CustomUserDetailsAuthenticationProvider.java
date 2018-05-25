/* Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
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

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * {@link CustomUserDetailsAuthenticationProvider} extends
 * {@link AbstractUserDetailsAuthenticationProvider} and delegates to the
 * {@link CustomUserDetailService} to retrieve the User. The most important
 * feature of this class is the implementation of the <code>retrieveUser</code>
 * method.
 * 
 * Note that the authentication token must be cast to CustomAuthenticationToken
 * to access the custom field - tenant
 * 
 * 
 * @author Sunit Katkar
 * @version 1.0
 * @since 1.0 (May 2018)
 */
public class CustomUserDetailsAuthenticationProvider
        extends AbstractUserDetailsAuthenticationProvider {

    /**
     * The plaintext password used to perform
     * PasswordEncoder#matches(CharSequence, String)} on when the user is not
     * found to avoid SEC-2056
     * (https://github.com/spring-projects/spring-security/issues/2280).
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    /**
     * For encoding and/or matching the encrypted password stored in the
     * database with the user submitted password
     */
    private PasswordEncoder passwordEncoder;

    private CustomUserDetailsService userDetailsService;

    /**
     * The password used to perform
     * {@link PasswordEncoder#matches(CharSequence, String)} on when the user is
     * not found to avoid SEC-2056. This is necessary, because some
     * {@link PasswordEncoder} implementations will short circuit if the
     * password is not in a valid format.
     */
    private String userNotFoundEncodedPassword;

    public CustomUserDetailsAuthenticationProvider(
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.authentication.dao.
     * AbstractUserDetailsAuthenticationProvider#additionalAuthenticationChecks(
     * org. springframework.security.core.userdetails.UserDetails,
     * org.springframework.security.authentication.
     * UsernamePasswordAuthenticationToken)
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        // Get the password submitted by the end user
        String presentedPassword = authentication.getCredentials().toString();

        // If the password stored in the database and the user submitted
        // password do not match, then signal a login error
        if (!passwordEncoder.matches(presentedPassword,
                userDetails.getPassword())) {
            logger.debug(
                    "Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService,
                "A UserDetailsService must be set");
        this.userNotFoundEncodedPassword = this.passwordEncoder
                .encode(USER_NOT_FOUND_PASSWORD);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.authentication.dao.
     * AbstractUserDetailsAuthenticationProvider#retrieveUser(java.lang.String,
     * org.springframework.security.authentication.
     * UsernamePasswordAuthenticationToken)
     */
    @Override
    protected UserDetails retrieveUser(String username,
            UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        CustomAuthenticationToken auth = (CustomAuthenticationToken) authentication;
        UserDetails loadedUser;

        try {
            loadedUser = this.userDetailsService
                    .loadUserByUsernameAndTenantname(
                            auth.getPrincipal().toString(), auth.getTenant());
        } catch (UsernameNotFoundException notFound) {
            if (authentication.getCredentials() != null) {
                String presentedPassword = authentication.getCredentials()
                        .toString();
                passwordEncoder.matches(presentedPassword,
                        userNotFoundEncodedPassword);
            }
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, "
                            + "which is an interface contract violation");
        }
        return loadedUser;
    }
}