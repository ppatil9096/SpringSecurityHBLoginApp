package com.pravin.spring.secuity.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pravin.spring.secuity.dao.UserInfoDAO;
import com.pravin.spring.secuity.entity.User;
import com.pravin.spring.secuity.entity.UserRole;
import com.pravin.spring.secuity.model.UserInfo;

@Service
@Transactional
public class UserInfoDAOImpl extends JdbcDaoSupport implements UserInfoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public UserInfoDAOImpl(DataSource dataSource) {
	this.setDataSource(dataSource);
    }

    public UserInfo findUserInfo(String userName) {
	String sql = "Select new " + UserInfo.class.getName() + "(u.username,u.password) " + " from "
		+ User.class.getName() + " u where u.username = ?1 ";
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery(sql);
	query.setParameter(1, userName);
	return (UserInfo) query.getSingleResult();
    }

    public List<String> getUserRoles(String userName) {
	String sql = "Select r.userRole " + " from " + UserRole.class.getName() + " r where r.user.username = ?1 ";

	Session session = sessionFactory.getCurrentSession();

	Query query = session.createQuery(sql);
	query.setParameter(1, userName);

	@SuppressWarnings("unchecked")
	List<String> roles = query.getResultList();

	return roles;
    }

}
