package scesi.org.check.rol.controller;

import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.rol.model.entity.Rol;
import scesi.org.check.rol.model.request.CreateRolRequest;
import scesi.org.check.rol.model.request.UpdateRolRequest;
import scesi.org.check.rol.model.response.RolResponse;
import scesi.org.check.rol.service.IRolService;

import java.util.List;

@RestController
@RequestMapping("/rol")
public class RolController {
    private final IRolService iRolService;

    public RolController(IRolService iRolService) {
        this.iRolService = iRolService;
    }

    @GetMapping("/{rolId}")
    public ResponseEntity<StandardResponse<RolResponse>> getRolById(
            @PathParam("rolId") final Long id
    ) {
        final Rol rol = iRolService.getRolById(id);
        final RolResponse rolResponse = generateRolResponse(rol);
        final StandardResponse<RolResponse> standardResponse = StandardResponse.<RolResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Rol retrieved successfully")
                .data(rolResponse).build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @GetMapping("/")
    public ResponseEntity<StandardResponse<List<RolResponse>>> getAllRoles() {
        final List<Rol> rol = iRolService.getAllRoles();
        final List<RolResponse> rolResponseList = rol.stream().map(this::generateRolResponse).toList();
        final StandardResponse<List<RolResponse>> standardResponse = StandardResponse.<List<RolResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Roles retrieved successfully")
                .data(rolResponseList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @DeleteMapping("/{rolId}")
    public ResponseEntity<StandardResponse<Boolean>> deleteRolById(
            @PathParam("rolId") final Long id
    ) {
        final Boolean rolDeleted = iRolService.deleteRol(id);
        final StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("rol deleted successfully")
                .data(rolDeleted)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PostMapping("/")
    public ResponseEntity<StandardResponse<RolResponse>> createRol(
            @Validated
            @RequestBody final CreateRolRequest request
    ) {
        final Rol rolCreated = iRolService.createRol(request);
        final RolResponse rolResponse = generateRolResponse(rolCreated);
        final StandardResponse<RolResponse> standardResponse = StandardResponse.<RolResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("rol created successfully")
                .data(rolResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @PatchMapping("/{rolId}")
    public ResponseEntity<StandardResponse<RolResponse>> updateRole(
            @PathParam("rolId") final Long rolId,
            @Validated
            @RequestBody final UpdateRolRequest request
    ) {
        final Rol rolUpdated = iRolService.updateRol(rolId, request);
        final RolResponse rolResponse = generateRolResponse(rolUpdated);
        final StandardResponse<RolResponse> standardResponse = StandardResponse.<RolResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("rol updated successfully")
                .data(rolResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    private RolResponse generateRolResponse(Rol rol) {
        return RolResponse.builder()
                .id(rol.getId())
                .rol(rol.getRol())
                .build();
    }
}
