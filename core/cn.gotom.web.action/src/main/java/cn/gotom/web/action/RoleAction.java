package cn.gotom.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cn.gotom.pojos.Organization;
import cn.gotom.pojos.Right;
import cn.gotom.pojos.Role;
import cn.gotom.service.OrganizationService;
import cn.gotom.service.RightService;
import cn.gotom.service.RoleService;
import cn.gotom.service.model.RightChecked;
import cn.gotom.util.StringUtils;

import com.google.inject.Inject;

@ParentPackage("json-default")
@Namespace(value = "/p")
@Action(value = "/role", results = { @Result(name = "success", type = "json") })
public class RoleAction extends AbsPortalAction
{
	protected final Logger log = Logger.getLogger(getClass());

	private Role role;

	private boolean success = true;

	private Object data;

	private String rightIds;

	private String id;

	@Inject
	private RoleService roleService;
	@Inject
	private OrganizationService orgService;
	@Inject
	private RightService rightService;

	public String execute()
	{
		if (role != null)
		{
			role = roleService.get(role.getId());
		}
		if (role == null)
		{
			role = new Role();
		}
		if (role.getOrganization() == null)
		{
			role.setOrganization(new Organization());
			role.getOrganization().setId("");
		}
		return "success";
	}

	public void tree()
	{
		try
		{
			if (role != null)
			{
				role = roleService.get(role.getId());
			}
			if (role == null)
			{
				role = new Role();
			}
			List<RightChecked> rightList = rightService.loadCustomCheckedTree(getCurrentCustomId(), roleService.findRight(role.getId()));
			this.setData(rightList);
			toJSON(rightList);
		}
		catch (Exception ex)
		{
			log.error(ex.getMessage(), ex);
		}
	}

	public void orgTree() throws IOException
	{

		// List<Organization> orgList = orgService.loadTree(getCurrentCustomId());
		List<Organization> orgList = orgService.findByParentId(getCurrentCustomId(), this.getId());
		this.toJSON(orgList);

		// CommonUtils.toJSON(ServletActionContext.getRequest(), ServletActionContext.getResponse(), orgList, null, new String[] { "custom" });
	}

	public String list()
	{
		List<Role> roleList = roleService.findByCustomId(this.getCurrentCustomId());
		this.setData(roleList);
		return "success";
	}

	public String save()
	{
		List<Right> selectedRights = new ArrayList<Right>();
		if (StringUtils.isNotEmpty(rightIds))
		{
			String[] rightIdArray = rightIds.split(",");
			List<Right> rightList = rightService.findAll();
			for (Right right : rightList)
			{
				for (String rightId : rightIdArray)
				{
					if (right.getId().equals(rightId.trim()))
					{
						selectedRights.add(right);
						break;
					}
				}
			}
		}
		Organization org = orgService.get(role.getOrganizationId());
		role.setOrganization(org);
		roleService.save(role, selectedRights);
		return "success";
	}

	public String remove()
	{
		try
		{
			String[] ids = role.getId().split(",");
			for (String id : ids)
			{
				roleService.removeById(id.trim());
			}
		}
		catch (Exception ex)
		{
			log.error("", ex);
		}
		return "success";
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public String getRightIds()
	{
		return rightIds;
	}

	public void setRightIds(String rightIds)
	{
		this.rightIds = rightIds;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

}
