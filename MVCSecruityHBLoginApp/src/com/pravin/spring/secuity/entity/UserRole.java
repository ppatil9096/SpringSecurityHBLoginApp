package com.pravin.spring.secuity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "User_Roless", uniqueConstraints = @UniqueConstraint(columnNames = { "Username", "User_Role" }))
public class UserRole {
    @Id
    @Column(name = "Role_Id", nullable = false)
    private String roleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Username")
    private User user;
    @Column(name = "User_Role", length = 30, nullable = false)
    private String userRole;

    public String getRoleId() {
	return roleId;
    }

    public void setRoleId(String roleId) {
	this.roleId = roleId;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public String getUserRole() {
	return userRole;
    }

    public void setUserRole(String userRole) {
	this.userRole = userRole;
    }
}
