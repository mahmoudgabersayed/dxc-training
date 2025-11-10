package com.dxc.training.services;

import com.dxc.training.dtos.UserDTO;
import com.dxc.training.entities.User;
import com.dxc.training.exceptions.BusinessException;
import com.dxc.training.exceptions.ResourceNotFoundException;
import com.dxc.training.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepository;

    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserDTO found = findUserByUserName(userDTO.getUserName());
        if (found != null)
            throw new BusinessException("User with user name " + userDTO.getUserName() + " is already there.");
        User user = convertToEntity(userDTO);
        return convertToDTO(userRepository.save(user));
    }

    private UserDTO findUserByUserName(String userName) {
        return getAllUsers().stream().filter(u -> u.getUserName().equals(userName)).findFirst().orElse(null);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        existingUser.setUserName(userDTO.getUserName());
        existingUser.setName(userDTO.getName());
        return convertToDTO(userRepository.save(existingUser));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUserName(), user.getName(), user.getPassword());
    }

    private User convertToEntity(UserDTO dto) {
        return new User(null, dto.getName(), dto.getUserName(), dto.getPassword());
    }

    public void validateUser(UserDTO userDTO) {
        UserDTO user = findUserByUserName(userDTO.getUserName());
        if (user == null || !user.getPassword().equals(userDTO.getPassword()))
            throw new BusinessException("Invalid user.");
    }
}

