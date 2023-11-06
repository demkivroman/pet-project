package com.demkiv.pet.project.service.security;

import com.demkiv.pet.project.service.entity.security.Privilege;
import com.demkiv.pet.project.service.entity.security.Role;
import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.repository.security.PrivilegeRepository;
import com.demkiv.pet.project.service.repository.security.RoleRepository;
import com.demkiv.pet.project.service.repository.security.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>, AuthorityInterface {
    boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("=========  ON APPLICATION EVENT  =============");
        if (alreadySetup) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, deletePrivilege);

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        Role userRole = createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        User adminUser = new User();
        adminUser.setName("admin_user");
        adminUser.setPassword("admin_password");
        adminUser.setRoles(Arrays.asList(adminRole));

        User userUser = new User();
        userUser.setName("user_user");
        userUser.setPassword("user_password");
        userUser.setRoles(Arrays.asList(userRole));

        User petUser = new User();
        petUser.setName("pet_user");
        petUser.setPassword("pet_password");
        petUser.setRoles(Arrays.asList(adminRole));

        userRepository.saveAll(Arrays.asList(adminUser, userUser, petUser));

        alreadySetup = true;
    }

    @Override
    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);

        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Override
    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByNameNative(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
