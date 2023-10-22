package org.leniv.distributed.systems.store.controller;

import lombok.AllArgsConstructor;
import org.leniv.distributed.systems.store.dto.UserDto;
import org.leniv.distributed.systems.store.dto.UserRegistrationDto;
import org.leniv.distributed.systems.store.entity.User;
import org.leniv.distributed.systems.store.entity.User.Role;
import org.leniv.distributed.systems.store.exception.AdminUserDisablingException;
import org.leniv.distributed.systems.store.exception.DuplicateUsernameException;
import org.leniv.distributed.systems.store.exception.UserNotFoundException;
import org.leniv.distributed.systems.store.mapper.UserMapper;
import org.leniv.distributed.systems.store.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
class UserController {
    UserRepository repository;
    UserMapper mapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void register(@RequestBody UserRegistrationDto userRegistration) {
        if (repository.findByUsername(userRegistration.getUsername()).isPresent()) {
            throw new DuplicateUsernameException(userRegistration.getUsername());
        }
        User user = mapper.userRegistrationDtoToUser(userRegistration);
        repository.save(user);
    }

    @GetMapping("/{id}")
    UserDto findById(@PathVariable Long id) {
        return repository.findById(id).map(mapper::userToUserDto).orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping
    List<UserDto> findAll() {
        return mapper.usersToUserDtos(repository.findAll());
    }

    @PostMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.OK)
    void disable(@PathVariable Long id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (Role.ADMIN.equals(user.getRole())) {
            throw new AdminUserDisablingException(user.getId());
        }
        user.setEnabled(false);
        repository.save(user);
    }
}
