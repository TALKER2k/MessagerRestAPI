package ru.relex.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.relex.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

//    List<User> findByLastActivityTimeBefore(LocalDateTime localDateTime);

    @Query("SELECT u FROM User u WHERE u.lastActivityTime < :beforeTime AND u.active = 'No active'")
    List<User> findInactiveUsers(@Param("beforeTime") LocalDateTime beforeTime);
}