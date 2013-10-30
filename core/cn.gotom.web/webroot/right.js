Ext.define('Ext.app.RightWindow',
{
    extend : 'Ext.window.Window',
    requires : [ 'Ext.form.*', 'Ext.data.*', 'Ext.tip.QuickTipManager'
    ],
    height : 300,
    width : 400,
    title : '菜单编辑',
    titleCollapse : false,
    closeAction : true,
    modal : true,
    datalist : '',
    layout :
    {
	type : 'border'
    },
    form : Ext.create('Ext.form.Panel',
    {
	region : 'center',
	// padding: 20,
	bodyPadding : 20,
	defaults :
	{
	    labelAlign : 'right',
	    anchor : '100%',
	    labelWidth : 100
	},
	items : [
	{
	    xtype : 'hiddenfield',
	    anchor : '100%',
	    fieldLabel : '未节点',
	    name : 'leaf'
	},
	{
	    xtype : 'hiddenfield',
	    anchor : '100%',
	    fieldLabel : '菜单标识',
	    name : 'id'
	},
	{
	    xtype : 'hiddenfield',
	    anchor : '100%',
	    fieldLabel : '父节点标识',
	    name : 'parentId'
	},
	{
	    xtype : 'hiddenfield',
	    anchor : '100%',
	    fieldLabel : '所属应用',
	    name : 'appCode'
	},
	{
	    xtype : 'textfield',
	    anchor : '100%',
	    allowBlank : false,
	    msgTarget : 'side',
	    name : 'text',
	    fieldLabel : '菜单名称'
	},
	{
	    xtype : 'textfield',
	    anchor : '100%',
	    name : 'iconCls',
	    fieldLabel : '图标样式'
	},
	{
	    xtype : 'textfield',
	    anchor : '100%',
	    name : 'component',
	    fieldLabel : '连接或控件'
	},
	{
	    xtype : 'numberfield',
	    anchor : '100%',
	    fieldLabel : '排列顺序',
	    name : 'sort'
	},
	{
	    xtype : 'textareafield',
	    anchor : '100%',
	    name : 'resource',
	    fieldLabel : '菜单资源'
	}
	]
    }),

    initComponent : function()
    {
	var me = this;
	var btnsave =
	{
	    xtype : 'button',
	    border : true,
	    iconCls : 'icon-save',
	    text : '保存',
	    handler : function(button, e)
	    {
		if (me.form.isValid())
		{
		    me.form.submit(
		    {
			url : urlprefix+'!save.do',
			method : 'POST',
			waitMsg : '正在保存数据，稍后...',
			success : function(f, action)
			{
			    Ext.Msg.alert('信息提示', '保存成功');
			    me.close();
			    location.reload();
			},
			failure : function(f, action)
			{
			    Ext.Msg.alert('信息提示', '保存失败！');
			}
		    });
		}
	    }
	};
	var btnClose =
	{
	    xtype : 'button',
	    border : true,
	    iconCls : 'icon-cancel',
	    text : '关闭',
	    handler : function(button, e)
	    {
		me.hide();
	    }
	};
	Ext.applyIf(me,
	{
	    items : [ this.form
	    ],
	    buttons : [ btnsave, btnClose
	    ]
	});

	me.callParent(arguments);
    }

});
var urlprefix = 'p/right';
var RightModel = Ext.define("RightModel",
{// 定义树节点数据模型
    extend : "Ext.data.Model",
    fields : [
    {
	name : "id",
	type : "string"
    },
    {
	name : "parentId",
	type : "string"
    },
    {
	name : 'sort',
	type : 'int'
    },

    {
	name : "iconCls",
	type : "string"
    },
    {
	name : "leaf",
	type : "boolean"
    },
    {
	name : 'type',
	type : 'string'
    },
    {
	name : 'resource',
	type : 'string'
    },
    {
	name : 'component',
	type : "string"
    },
    {
	name : "text",
	type : "string"
    },
    {
	name : "appCode",
	type : "string"
    },
    {
	name : 'checked',
	type : "boolean"
    }
    ]
});

function rightTreeStore(url, pid)
{
    return Ext.create("Ext.data.TreeStore",
    {
	defaultRootId : pid,
	model : RightModel,
	proxy :
	{
	    type : "ajax",
	    url : url
	},
	clearOnLoad : true,
	nodeParam : "id"
    });
};
var treeStore = rightTreeStore(urlprefix+'!tree.do', '');
Ext.onReady(function()
{
    var view = Ext.create("Ext.container.Viewport",
    {
	layout : "border"
    });

    var tree = Ext.create('Ext.tree.Panel',
    {
	// title: '菜单列表',
	region : 'center',
	animate : true,
	border : false,
	bodyborder : false,
	lines : true,
	split : true,
	stateful : true,
	collapsible : false,
	frame : false,
	enableDD : true,
	autoScroll : true,
	autoHeight : false,
	rootVisible : false,
	store : treeStore,
	multiSelect : false,
	root :
	{
	    expanded : true
	},
	tbar : [
	{
	    xtype : 'button',
	    iconCls : 'icon-refresh',
	    text : '刷新',
	    handler : function(button, e)
	    {
		location.reload();
	    }
	},
	{
	    xtype : 'button',
	    iconCls : 'icon-edit',
	    text : '修改',
	    handler : function(button, e)
	    {
		handleredit(button, e);
	    }
	},
	{
	    xtype : 'button',
	    iconCls : 'icon-del',
	    text : '删除',
	    handler : function(button, e)
	    {
		handlerdel(button, e);
	    }
	}
	],
	columns : [
	{
	    xtype : 'treecolumn',
	    dataIndex : 'text',
	    text : '菜单名称',
	    sortable : false,
	    flex : 1,
	    menuDisabled : true
	},
	{
	    xtype : 'gridcolumn',
	    dataIndex : 'iconCls',
	    text : '使用样式'
	},
	{
	    xtype : 'gridcolumn',
	    dataIndex : 'component',
	    text : '控件或连接'
	},
	{
	    xtype : 'gridcolumn',
	    dataIndex : 'resource',
	    text : '数据资源'
	},
	{
	    xtype : 'actioncolumn',
	    menuDisabled : true,
	    align : 'center',
	    text : '修改',
	    width : 40,
	    iconCls : 'icon-edit',
	    handler : function(grid, rowIndex, colIndex, actionItem, event, record, row)
	    {
		showform(record);
	    }
	}
	]
    });

    var formwin = null;
    function handleredit(button, e)
    {
	showform(getSelectedNode(tree));
    }

    function handlerdel(button, e)
    {
	Ext.Msg.confirm("警告", "确定要删除吗？", function(button)
	{
	    if (button == "yes")
	    {
		var ids = getAllChecked(tree);
		Ext.Ajax.request(
		{
		    url : urlprefix+'!remove.do',
		    method : 'POST',
		    params :
		    {
			id : ids
		    },
		    success : function(response, options)
		    {
			Ext.Msg.alert("删除提示", "删除成功");
			window.location.reload();
		    },
		    failure : function(response, options)
		    {
			Ext.Msg.alert("删除提示", "删除失败");
		    }
		});
	    }
	});
    }

    function showform(record)
    {
	if (record != null && record != '')
	{
	    if (!formwin)
		formwin = Ext.create('Ext.app.RightWindow');
	    formwin.show();
	    formwin.form.loadRecord(record);
	}
    }
    tree.expandAll();
    view.add(tree);
});