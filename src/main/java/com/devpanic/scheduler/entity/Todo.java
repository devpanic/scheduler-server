package com.devpanic.scheduler.entity;

import com.devpanic.scheduler.util.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "todos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @Column(nullable = false)
    private LocalDateTime updateDate;

    private LocalDateTime completedAt;
}
