package cn.gotom.service.impl;

import java.util.List;

import javax.persistence.Query;

import cn.gotom.dao.jpa.GenericDaoJpa;
import cn.gotom.pojos.User;
import cn.gotom.pojos.UserCustom;
import cn.gotom.service.UserService;
import cn.gotom.util.PasswordEncoder;

public class UserServiceImpl extends GenericDaoJpa<User, String> implements UserService
{
	public UserServiceImpl()
	{
		super(User.class);
	}

	@Override
	public void init()
	{
		try
		{
			User user = getByUsername(User.admin);
			if (user == null)
			{
				user = new User();
				user.setUsername(User.admin);
				user.setName("超级管理员");
				PasswordEncoder passwordEncoder = new PasswordEncoder("MD5");
				user.setPassword(passwordEncoder.encode("a"));
				save(user);
			}
		}
		catch (Exception ex)
		{
			log.error("", ex);
		}
	}

	@Override
	public User getByUsername(String username)
	{
		User user = this.get("username", username);
		return user;
	}

	@Override
	public List<UserCustom> getUserCustomByUserId(String userId)
	{
		String jpql = "select p from " + UserCustom.class.getSimpleName() + " p where 1 = 1";
		jpql += " and p.user.id = :userId";
		Query q = getEntityManager().createQuery(jpql);
		q.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<UserCustom> list = q.getResultList();
		return list;
	}
}