package scesi.org.check.rol.config.seed;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import scesi.org.check.rol.model.entity.RolEntity;
import scesi.org.check.rol.model.enumerate.RoleEnum;
import scesi.org.check.rol.model.repository.IRolRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleSeed implements ApplicationRunner {

    private final IRolRepository iRolRepository;

    public RoleSeed(IRolRepository iRolRepository) {
        this.iRolRepository = iRolRepository;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) {
        if (iRolRepository.count() == 0) {
            List<RolEntity> roles = new ArrayList<>();
            roles.add(RolEntity.builder().id(RoleEnum.ADMIN.getId()).rol(RoleEnum.ADMIN.getName()).build());
            roles.add(RolEntity.builder().id(RoleEnum.MEMBER.getId()).rol(RoleEnum.MEMBER.getName()).build());
            iRolRepository.saveAll(roles);
        }
    }
}