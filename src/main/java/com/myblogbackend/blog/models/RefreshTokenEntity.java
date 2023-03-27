package com.myblogbackend.blog.models;

import java.time.Instant;
import java.util.UUID;


import com.myblogbackend.blog.models.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "refresh_token")
public class RefreshTokenEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_device_id", unique = true)
    private UserDeviceEntity userDevice;

    @Column(name = "refresh_count")
    private Long refreshCount;

    @Column(name = "expiry_dt", nullable = false)
    private Instant expiryDate;
    
    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }
}