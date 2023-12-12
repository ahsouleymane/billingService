package net.sancfis.billingService.query;

public class RoleQuery {
    public static final String INSERT_ROLE_TO_USER_QUERY = "INSERT INTO UsersRoles (user_id, role_id) VALUES (:user_id, :role_id)";
    public static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * FROM Roles WHERE name = :name";
}