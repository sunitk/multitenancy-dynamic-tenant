package com.sunitkatkar.blogspot.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sunitkatkar.blogspot.util.TenantContextHolder;
import com.sunitkatkar.blogspot.tenant.model.User;
import com.sunitkatkar.blogspot.tenant.service.UserService;

/**
 * Rest Controller to handle all requests to the /user end point
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 */
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * @param tenantId
     * @return
     */
    @GetMapping("/user/{tenantId}")
    @ResponseBody
    public List<String> getUsersForTenant(
            @PathVariable("tenantId") String tenantId) {
        TenantContextHolder.setTenantId(tenantId);
        List<User> users = userService.findAllUsers();
        List<String> userList = users.stream().map(result -> result.toString())
                .collect(Collectors.toList());
        return userList;
    }

}
