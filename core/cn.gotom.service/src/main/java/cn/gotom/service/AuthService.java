package cn.gotom.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.gotom.pojos.ResourceConfig;
import cn.gotom.pojos.ResourceName;
import cn.gotom.pojos.Right;
import cn.gotom.pojos.Role;
import cn.gotom.pojos.User;
import cn.gotom.util.StringUtils;
import cn.gotom.util.UrlMatcher;

import com.google.inject.Inject;

public class AuthService
{
	protected final Logger log = Logger.getLogger(getClass());
	@Inject
	private UserService userService;

	@Inject
	private RoleService roleService;

	@Inject
	private RightService rightService;

	@Inject
	private UrlMatcher urlMatcher;

	@Inject
	private ResourceConfigService resourceConfigService;

	public boolean isAuth(String username, String url)
	{
		ResourceConfig rc = resourceConfigService.getByName(ResourceName.everyone_can_access);
		if (rc == null)
		{
			rc = new ResourceConfig();
			rc.setName(ResourceName.everyone_can_access);
			rc.setResValue(Boolean.TRUE.toString());
			resourceConfigService.save(rc);
		}
		if (rc != null && Boolean.parseBoolean(rc.getResValue()))
		{
			return true;
		}
		User user = userService.get("username", username);
		return isAuth(user, url);
	}

	private boolean isAuth(User user, String url)
	{
		if (user == null)
		{
			return false;
		}
		else if (User.admin.equals(user.getUsername()))
		{
			return true;
		}
		if (user.getRoles() == null || user.getRoles().size() == 0)
		{
			user = userService.get(user.getId());
		}
		if (user.getRoles() != null)
		{
			for (Role role : user.getRoles())
			{
				if (role.getRights() == null || role.getRights().size() == 0)
				{
					role = roleService.get(role.getId());
				}
				if (role.getRights() != null)
				{
					for (Right right : role.getRights())
					{
						if (StringUtils.isNotEmpty(right.getResource()))
						{
							String[] resource = right.getResource().trim().replace("；", ";").split(";");
							for (String pattern : resource)
							{
								if (urlMatcher.pathMatchesUrl(pattern, url))
								{
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param username
	 * @param parentId
	 * @return
	 */
	public List<Right> findRightList(String username, String parentId)
	{
		List<Right> rightList = new ArrayList<Right>();
		User user = userService.getByUsername(username);
		if (user != null)
		{
			rightList = rightService.findByParentId(parentId);
			for (int i = rightList.size() - 1; i >= 0; i--)
			{
				boolean find = false;
				if (User.admin.equals(user.getUsername()))
				{
					find = true;
				}
				else
				{
					for (Role role : user.getRoles())
					{
						// role.getCompany()
						if (rightList.get(i).getRoles().contains(role))
						{
							find = true;
						}
					}
				}
				if (!find)
				{
					rightList.remove(i);
				}
			}
		}
		return rightList;
	}
}