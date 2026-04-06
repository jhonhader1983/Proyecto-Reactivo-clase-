package com.lamaison.auth.dto.response;

import com.lamaison.auth.model.Role;
import java.time.LocalDateTime;

public class UserResponse {

    private String id;
    private String nombre;
    private String correo;
    private Role rol;
    private boolean activo;
    private LocalDateTime creadoEn;

    public UserResponse() {}

    public UserResponse(String id, String nombre, String correo, Role rol, boolean activo, LocalDateTime creadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.activo = activo;
        this.creadoEn = creadoEn;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Role getRol() { return rol; }
    public void setRol(Role rol) { this.rol = rol; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}