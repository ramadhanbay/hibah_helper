package com.apps.pu.hibah.entity;
// Generated 04-Feb-2018 21:45:47 by Hibernate Tools 3.6.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Roles generated by hbm2java
 */
@Entity
@Table(name = "roles", catalog = "hibah", uniqueConstraints = { @UniqueConstraint(columnNames = "role_code"),
		@UniqueConstraint(columnNames = "role_name") })
public class Roles implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idRole;
	private String roleName;
	private String description;
	private Date updateDate;
	private String updateBy;
	private Date createDate;
	private String createBy;
	private String roleCode;
	private Set<RoleUser> roleUsers = new HashSet<RoleUser>(0);

	public Roles() {
	}

	public Roles(String roleName, Date updateDate, String updateBy, Date createDate, String createBy, String roleCode) {
		this.roleName = roleName;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.createDate = createDate;
		this.createBy = createBy;
		this.roleCode = roleCode;
	}

	public Roles(String roleName, String description, Date updateDate, String updateBy, Date createDate,
			String createBy, String roleCode, Set<RoleUser> roleUsers) {
		this.roleName = roleName;
		this.description = description;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.createDate = createDate;
		this.createBy = createBy;
		this.roleCode = roleCode;
		this.roleUsers = roleUsers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_role", unique = true, nullable = false)
	public Integer getIdRole() {
		return this.idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	@Column(name = "role_name", unique = true, nullable = false, length = 45)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "description", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "update_by", nullable = false, length = 45)
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", nullable = false, length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_by", nullable = false, length = 45)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "role_code", unique = true, nullable = false, length = 45)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<RoleUser> getRoleUsers() {
		return this.roleUsers;
	}

	public void setRoleUsers(Set<RoleUser> roleUsers) {
		this.roleUsers = roleUsers;
	}

}