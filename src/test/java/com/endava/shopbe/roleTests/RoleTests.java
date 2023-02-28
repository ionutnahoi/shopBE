package com.endava.shopbe.roleTests;

import com.endava.shopbe.entity.Role;
import com.endava.shopbe.repository.RoleRepo;
import com.endava.shopbe.service.RoleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleTests {

    @Mock
    private RoleRepo roleRepo;

    private RoleService roleService;

    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        roleService = new RoleService(roleRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    public Role createRole() {
        return Role.builder()
                .id(2L)
                .name("ADMIN").build();
    }

    @Test
    void saveRoleTest() {
        Role rol = createRole();
        roleRepo.save(rol);

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepo).save(roleArgumentCaptor.capture());

        Role capturedRole = roleArgumentCaptor.getValue();
        Assertions.assertThat(capturedRole).isEqualTo(rol);
    }


    @Test
    void DeleteRole() {
        Role rol = createRole();
        roleRepo.save(rol);

        roleRepo.delete(rol);
        Assertions.assertThat(roleRepo.findRoleByName("ADMIN")).isNull();

    }

    @Test
    void getRoleByName() throws CloneNotSupportedException {

        Role rol = createRole();

        roleService.save(rol);

        doReturn(Optional.of(rol)).when(roleRepo).findRoleByName("ADMIN");
        Role rol2 = roleService.getRoleByName("ADMIN");
        Role rolClone = (Role) rol2.clone();

//        rolClone.setName("USER");
        Assertions.assertThat(rol.getId()).isEqualTo(rolClone.getId());
        Assertions.assertThat(rol.getName()).isEqualTo(rolClone.getName());

    }

    @Test
    void getRoleByNameException() {
        Role rol = createRole();

        roleService.save(rol);

        doReturn(Optional.of(rol)).when(roleRepo).findRoleByName("USER");
        Role rol2 = roleService.getRoleByName("USER");
        Assertions.assertThat("Role could not be found with this name: ADMIN");
    }

    @Test
    void checkIfRoleExists() {
        Assertions.assertThat(Assertions.assertThat(roleRepo.findRoleByName("ADMIN"))).isNotNull();
    }


}
