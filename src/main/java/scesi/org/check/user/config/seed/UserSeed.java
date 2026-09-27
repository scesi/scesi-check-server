package scesi.org.check.user.config.seed;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import scesi.org.check.rol.model.enumerate.RoleEnum;
import scesi.org.check.rol.model.repository.IRolRepository;
import scesi.org.check.user.model.entity.RolUserEntity;
import scesi.org.check.user.model.entity.UserEntity;
import scesi.org.check.user.model.repository.IUserRepository;
import scesi.org.check.user.model.repository.IRolUserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserSeed implements ApplicationRunner {

    private final IUserRepository iUserRepository;
    private final IRolUserRepository iRolUserRepository;
    private final IRolRepository iRolRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeed(IUserRepository iUserRepository, IRolUserRepository iRolUserRepository, IRolRepository iRolRepository, PasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.iRolUserRepository = iRolUserRepository;
        this.iRolRepository = iRolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) {
        if (iUserRepository.count() == 0) {
            var adminRole = iRolRepository.findById(RoleEnum.ADMIN.getId()).orElseThrow();
            var memberRole = iRolRepository.findById(RoleEnum.MEMBER.getId()).orElseThrow();

            var adminUser = UserEntity.builder()
                    .name("Admin")
                    .lastName("System")
                    .email("admin@scesi.org")
                    .active(true)
                    .build();
            iUserRepository.save(adminUser);

            var memberUser = UserEntity.builder()
                    .name("Member")
                    .lastName("User")
                    .email("member@scesi.org")
                    .active(true)
                    .build();
            iUserRepository.save(memberUser);

            var bothRolesUser = UserEntity.builder()
                    .name("Admin")
                    .lastName("Member")
                    .email("admin.member@scesi.org")
                    .active(true)
                    .build();
            iUserRepository.save(bothRolesUser);

            List<RolUserEntity> rolUsers = new ArrayList<>();
            rolUsers.add(RolUserEntity.builder()
                    .user(adminUser)
                    .rol(adminRole)
                    .creationDate(Instant.now())
                    .build());
            rolUsers.add(RolUserEntity.builder()
                    .user(memberUser)
                    .rol(memberRole)
                    .creationDate(Instant.now())
                    .build());
            rolUsers.add(RolUserEntity.builder()
                    .user(bothRolesUser)
                    .rol(adminRole)
                    .creationDate(Instant.now())
                    .build());
            rolUsers.add(RolUserEntity.builder()
                    .user(bothRolesUser)
                    .rol(memberRole)
                    .creationDate(Instant.now())
                    .build());

            iRolUserRepository.saveAll(rolUsers);
        }
    }
}