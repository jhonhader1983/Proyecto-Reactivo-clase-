package com.lamaison.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    private String id;
    private String userId;
    private String token;
    private LocalDateTime expiracion;
    private boolean usado;

    public PasswordResetToken() {}

    public PasswordResetToken(String id, String userId, String token, LocalDateTime expiracion, boolean usado) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiracion = expiracion;
        this.usado = usado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LocalDateTime getExpiracion() { return expiracion; }
    public void setExpiracion(LocalDateTime expiracion) { this.expiracion = expiracion; }
    public boolean isUsado() { return usado; }
    public void setUsado(boolean usado) { this.usado = usado; }
}