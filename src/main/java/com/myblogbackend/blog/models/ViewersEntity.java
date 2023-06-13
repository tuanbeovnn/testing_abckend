package com.myblogbackend.blog.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "viewers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewersEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "view_ct")
    private Integer viewCounter;

    @Column(name = "post_id")
    private UUID postId;

}
