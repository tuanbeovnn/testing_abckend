package com.myblogbackend.blog.models;

import java.time.Instant;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "refresh_token")
public class RefreshTokenEntity {

	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", allocationSize = 1)
    private Long id;

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