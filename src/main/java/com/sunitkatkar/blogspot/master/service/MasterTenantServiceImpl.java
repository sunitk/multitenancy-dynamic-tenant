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
package com.sunitkatkar.blogspot.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunitkatkar.blogspot.master.model.MasterTenant;
import com.sunitkatkar.blogspot.master.repository.MasterTenantRepository;

/**
 * Implementation of the {@link MasterTenantService} which accesses the
 * {@link MasterTenant} entity. This is the recommended way to access the
 * entities through an interface rather than using the corresponding repository.
 * This allows for separation into repository code and the service layer.
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 */
@Service
public class MasterTenantServiceImpl implements MasterTenantService {

    @Autowired
    MasterTenantRepository masterTenantRepo;

    @Override
    public MasterTenant findByTenantId(String tenantId) {
        return masterTenantRepo.findByTenantId(tenantId);
    }

}
