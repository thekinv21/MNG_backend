package com.mng.Mng.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "actions")
public class Action extends BaseEntity {

    private String photographName;
    private String imageUrl;
    @Getter
    private LocalDateTime createdDate;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "action",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions;

    public Action(String photographName, String imageUrl,LocalDateTime createdDate, boolean isActive, User user) {
        this.photographName = photographName;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.isActive = isActive;
        this.user = user;
    }


    public void addQuestion(Question question1){
        if(questions==null) questions=new ArrayList<>();
        questions.add(question1);
    }

    @Override
    public String toString() {
        return "Action{" +
                "photographName='" + photographName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdDate=" + createdDate +
                ", isActive=" + isActive +
                ", questions=" + questions +
                '}';
    }
}
