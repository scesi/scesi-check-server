package scesi.org.check.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.user.model.entity.UserEntity;
import scesi.org.check.user.model.projection.IRolesOfUserProjection;
import scesi.org.check.user.model.request.CreateUserRequest;
import scesi.org.check.user.model.request.UpdateUserRequest;
import scesi.org.check.user.model.response.RolesOfUserResponse;
import scesi.org.check.user.model.response.UserResponse;
import scesi.org.check.user.service.IUserService;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<StandardResponse<UserResponse>> getUserById(
            @Validated
            @PathVariable("userId") final long userId
    ) {
        final UserEntity user = iUserService.getUserById(userId);
        final UserResponse userResponse = generateUserResponse(user);
        final StandardResponse<UserResponse> standardResponse = StandardResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("user retrieved successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @GetMapping("/")
    public ResponseEntity<StandardResponse<List<UserResponse>>> getAllUsers() {
        final List<UserEntity> user = iUserService.getAllUsers();
        final List<UserResponse> userListResponse = user.stream()
                .map(this::generateUserResponse)
                .toList();
        final StandardResponse<List<UserResponse>> standardResponse = StandardResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Users Retrieved")
                .data(userListResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PostMapping("/")
    public ResponseEntity<StandardResponse<UserResponse>> createUser(
            @Validated
            @RequestBody final CreateUserRequest request
    ) {
        final UserEntity userCreated = iUserService.createUser(request);
        final UserResponse userResponse = generateUserResponse(userCreated);
        final StandardResponse<UserResponse> standardResponse = StandardResponse.<UserResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<StandardResponse<Boolean>> deleteUser(
            @PathVariable("userId") final Long userId
    ) {
        final Boolean userDeletedResult = iUserService.deleteUser(userId);
        final StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User deleted successfully")
                .data(userDeletedResult)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<StandardResponse<UserResponse>> updateUser(
            @PathVariable("userId") final Long userId,
            @Validated
            @RequestBody final UpdateUserRequest request
    ) {
        final UserEntity user = iUserService.updateUser(userId, request);
        final UserResponse userResponse = generateUserResponse(user);
        final StandardResponse<UserResponse> standardResponse = StandardResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User updated successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PostMapping("/{userId}/role/{rolId}")
    public ResponseEntity<StandardResponse<Boolean>> addRoleToUser(
            @PathVariable("userId") final Long userId,
            @PathVariable("rolId") final Long rolId
    ) {
        final Boolean rolAssigned = iUserService.assignRol(userId, rolId);
        final StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Rol assigned successfully")
                .data(rolAssigned)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @DeleteMapping("/{userId}/rol/{rolId}")
    public ResponseEntity<StandardResponse<Boolean>> removeRoleToUser(
            @PathVariable("userId") final Long userId,
            @PathVariable("rolId") final Long rolId
    ) {
        final Boolean rolDeleted = iUserService.removeRolAssigned(userId, rolId);
        final StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Rol removed successfully")
                .data(rolDeleted)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @GetMapping("/{userId}/rol/")
    public ResponseEntity<StandardResponse<List<RolesOfUserResponse>>> getAllRolesOfUser(
            @PathVariable("userId") final Long userId
    ) {
        final List<IRolesOfUserProjection> assignedUserRoles = iUserService.getAllAssignedUserRoles(userId);
        final List<RolesOfUserResponse> assignedUserRolesResponse = assignedUserRoles.stream()
                .map(this::generateRolesOfUserResponse)
                .toList();

        final StandardResponse<List<RolesOfUserResponse>> standardResponse = StandardResponse.<List<RolesOfUserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Roles of users retrieved successfully")
                .data(assignedUserRolesResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    private RolesOfUserResponse generateRolesOfUserResponse(IRolesOfUserProjection rolesOfUser) {
        return RolesOfUserResponse.builder()
                .rol(rolesOfUser.getRol())
                .creationDate(rolesOfUser.getCreationDate())
                .build();
    }

    private UserResponse generateUserResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .active(user.getActive())
                .build();
    }

}
