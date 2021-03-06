package cn.gotom.vo;

public class TreeModel extends Item
{
	private String parentId;
	private int sort;
	private String iconCls;
	private boolean leaf;
	private String type;
	private boolean expand;

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}

	public String getIconCls()
	{
		return iconCls;
	}

	public void setIconCls(String iconCls)
	{
		this.iconCls = iconCls;
	}

	public boolean isLeaf()
	{
		return leaf;
	}

	public void setLeaf(boolean leaf)
	{
		this.leaf = leaf;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isExpand()
	{
		return expand;
	}

	public void setExpand(boolean expand)
	{
		this.expand = expand;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

}
