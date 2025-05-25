package project2.IMS.Service;

import project2.IMS.Entity.User;
import project2.IMS.Request.LoginRequest;
import project2.IMS.Request.RegisterRequest;
import project2.IMS.DTO.Response;
import project2.IMS.Request.UpdateUserRequest;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getCurrentLoggedInUser();
    Response updateUser(Integer id, UpdateUserRequest updateUserRequest);
    Response deleteUser(Integer id);
    Response getUserTransactions(Integer id);
    Response logoutUser();
}
