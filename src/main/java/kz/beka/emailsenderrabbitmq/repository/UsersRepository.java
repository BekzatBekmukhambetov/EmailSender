package kz.beka.emailsenderrabbitmq.repository;

import kz.beka.emailsenderrabbitmq.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    boolean existsByEmail(String email);
    Users findByEmail(String email);
    void deleteByEmail(String email);
}
