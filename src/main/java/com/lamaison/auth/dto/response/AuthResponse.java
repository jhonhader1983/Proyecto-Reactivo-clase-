package com.lamaison.auth.dto.response;

import com.lamaison.auth.model.Role;

public class AuthResponse {

    private String token;
    private String nombre;
    private String correo;
    private Role rol;

    public AuthResponse() {}

    public AuthResponse(String token, String nombre, String correo, Role rol) {
        this.token = token;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Role getRol() { return rol; }
    public void setRol(Role rol) { this.rol = rol; }
}