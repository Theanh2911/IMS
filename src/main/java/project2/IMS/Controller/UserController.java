package project2.IMS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project2.IMS.DTO.Response;
import project2.IMS.Entity.User;
import project2.IMS.Request.UpdateUserRequest;
import project2.IMS.Service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser() {
        User currentUser = userService.getCurrentLoggedInUser();
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest updateUserRequest) {
        Response response = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Integer id) {
        Response response = userService.deleteUser(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }



}
