package ru.relex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relex.models.Conversation;
import ru.relex.models.User;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByFirstIdAndSecondId(User firstUser, User secondUser);

    void deleteByFirstId(User firstId);
    void deleteBySecondId(User secondId);

}
