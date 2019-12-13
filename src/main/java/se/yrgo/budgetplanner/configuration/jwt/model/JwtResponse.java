package se.yrgo.budgetplanner.configuration.jwt.model;

public class JwtResponse {

    private final String jwt;

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getToken() {
        return this.jwt;
    }
}