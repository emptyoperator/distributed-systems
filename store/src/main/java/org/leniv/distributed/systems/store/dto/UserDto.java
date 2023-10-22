package org.leniv.distributed.systems.store.dto;

import lombok.Data;
import org.leniv.distributed.systems.store.entity.User;

@Data
public class UserDto {
    private Long id;
    private String username;
    private boolean enabled;
    private User.Role role;
}
