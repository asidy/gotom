package cn.gotom.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.gotom.pojos.Custom;
import cn.gotom.pojos.User;
import cn.gotom.sso.util.GsonUtils;
import cn.gotom.util.StringUtils;

import com.google.gson.TypeAdapterFactory;

public abstract class AbsPortalAction
{
	protected final Logger log = Logger.getLogger(getClass());

	public HttpServletRequest getRequest()
	{
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse()
	{
		return ServletActionContext.getResponse();
	}

	public String getCurrentCustomId()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String customId = (String) request.getSession().getAttribute(Custom.currentCustomId);
		if (StringUtils.isNullOrEmpty(customId))
		{
			customId = (String) request.getParameter("customId");
		}
		else
		{
			request.getSession().setAttribute(Custom.currentCustomId, customId);
		}
		if (StringUtils.isNullOrEmpty(customId))
		{
			User user = getCurrentUser();
			customId = user.getDefaultCustomId();
		}
		return customId;
	}

	protected <T> void toJSON(T value, String... excludeFields)
	{
		TypeAdapterFactory[] factorys = new TypeAdapterFactory[] { HibernateProxyTypeAdapter.FACTORY };
		String json = GsonUtils.toJson(value, GsonUtils.dateFormat, factorys, excludeFields, null);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		GsonUtils.writer(request, response, json);
	}

	public String getCurrentUsername()
	{
		return ServletActionContext.getRequest().getRemoteUser();
	}

	public User getCurrentUser()
	{
		return (User) ServletActionContext.getRequest().getAttribute(User.CurrentLoginUser);
	}
}