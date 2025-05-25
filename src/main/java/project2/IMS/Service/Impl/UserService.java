package project2.IMS.Service.Impl;

import project2.IMS.Request.LoginRequest;
import project2.IMS.Request.RegisterRequest;
import project2.IMS.DTO.Response;
import project2.IMS.DTO.UserDTO;
import project2.IMS.Entity.User;
import project2.IMS.exceptions.InvalidCredentialsException;
import project2.IMS.exceptions.NotFoundException;
import project2.IMS.Repository.UserRepository;
import project2.IMS.Request.UpdateUserRequest;
import project2.IMS.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements project2.IMS.Service.UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;


    @Override
    public Response registerUser(RegisterRequest registerRequest) {

        User.Role role = User.Role.staff;

        User userToSave = User.builder()
                .name(registerRequest.getName())
                .username(registerRequest.getUsername())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .workingShift(registerRequest.getWorkingShift())
                .build();

        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("user created successfully")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
       User user = userRepository.findByUsername(loginRequest.getUsername())
               .orElseThrow(()-> new NotFoundException("Username not Found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("password does not match");
        }
        String token = jwtUtils.generateToken(user.getUsername());

        return Response.builder()
                .status(200)
                .message("user logged in successfully")
                .name(user.getName())
                .username(user.getUsername())
                .userId(user.getId())
                .role(user.getRole().name())
                .token(token)
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<UserDTO> userDTOS = modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .users(userDTOS)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException("User Not Found"));

        return user;

    }

    @Override
    public Response updateUser(Integer id, UpdateUserRequest updateUserRequest) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User Not Found"));

        if (updateUserRequest.getUsername() != null) existingUser.setUsername(updateUserRequest.getUsername());
        if (updateUserRequest.getName() != null) existingUser.setWorkingShift(updateUserRequest.getWorkingShift());
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(updateUserRequest.getPassword()));
        }
        if (updateUserRequest.getWorkingShift() != null) existingUser.setWorkingShift(updateUserRequest.getWorkingShift());

        userRepository.save(existingUser);

        return Response.builder()
                .status(200)
                .message("User Successfully updated")
                .build();
    }

    @Override
    public Response deleteUser(Integer id) {

         userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
         userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("User Successfully Deleted")
                .build();
    }

    @Override
    public Response getUserTransactions(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User Not Found"));

        List<UserDTO> userDTOs = modelMapper.map(user, new TypeToken<List<UserDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .users(userDTOs)
                .build();
    }

    @Override
    public Response logoutUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username != null) {
            SecurityContextHolder.clearContext();
            return Response.builder()
                    .status(200)
                    .message("user logged out successfully")
                    .build();
        } else {
            return Response.builder()
                    .status(401)
                    .message("user not logged in")
                    .build();
        }
    }

}
