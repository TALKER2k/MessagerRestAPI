package ru.relex.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversations_seq")
    @SequenceGenerator(name = "conversations_seq", sequenceName = "conversations_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_id")
    private User firstId;

    @ManyToOne
    @JoinColumn(name = "second_id")
    private User secondId;
}
