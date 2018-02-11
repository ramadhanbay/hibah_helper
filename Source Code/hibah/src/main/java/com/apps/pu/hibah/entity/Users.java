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
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", catalog = "hibah", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Users implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idUser;
	private String username;
	private String password;
	private Date lastLogin;
	private Date updateDate;
	private String updateBy;
	private Date createDate;
	private String createBy;
	private Set<RoleUser> roleUsers = new HashSet<RoleUser>(0);

	public Users() {
	}

	public Users(String username, String password, Date lastLogin, Date updateDate, String updateBy, Date createDate,
			String createBy) {
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.createDate = createDate;
		this.createBy = createBy;
	}

	public Users(String username, String password, Date lastLogin, Date updateDate, String updateBy, Date createDate,
			String createBy, Set<RoleUser> roleUsers) {
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.createDate = createDate;
		this.createBy = createBy;
		this.roleUsers = roleUsers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_user", unique = true, nullable = false)
	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	@Column(name = "username", unique = true, nullable = false, length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 400)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login", nullable = false, length = 19)
	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "update_by", nullable = false, length = 100)
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

	@Column(name = "create_by", nullable = false, length = 100)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<RoleUser> getRoleUsers() {
		return this.roleUsers;
	}

	public void setRoleUsers(Set<RoleUser> roleUsers) {
		this.roleUsers = roleUsers;
	}

}
