/*
 * File: classes/view/UserGridPanel.js
 *
 * This file was generated by Sencha Architect version 2.2.3.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('Gotom.view.UserGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.UserGridPanel',

    id: 'UserGridPanel',
    bodyBorder: false,
    title: '用户列表',
    columnLines: true,
    forceFit: true,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    sortable: false,
                    dataIndex: 'username',
                    text: '登录帐号'
                },
                {
                    xtype: 'gridcolumn',
                    sortable: false,
                    dataIndex: 'name',
                    text: '用户姓名'
                },
                {
                    xtype: 'gridcolumn',
                    sortable: false,
                    dataIndex: 'mobile',
                    text: '手机号码'
                },
                {
                    xtype: 'gridcolumn',
                    sortable: false,
                    dataIndex: 'cardId',
                    text: '身份证号码'
                },
                {
                    xtype: 'gridcolumn',
                    sortable: false,
                    dataIndex: 'cardRFID',
                    text: '工作卡号'
                },
                {
                    xtype: 'gridcolumn',
                    defaultWidth: 40,
                    dataIndex: 'status',
                    text: '状态'
                }
            ],
            viewConfig: {
                id: 'UserGridView'
            },
            selModel: Ext.create('Ext.selection.CheckboxModel', {

            }),
            tools: [
                {
                    xtype: 'tool',
                    id: 'UserTool',
                    type: 'refresh',
                    listeners: {
                        click: {
                            fn: me.onToolClick,
                            scope: me
                        }
                    }
                }
            ]
        });

        me.callParent(arguments);
    },

    onToolClick: function(tool, e, eOpts) {
        this.loadGrid();
    },

    loadData: function(query) {
        var me = this;
        Common.ajax({
            component : me,
            params:{'query':query},
            message : '正在加载......',    
            url : ctxp+'/p/user!list.do',
            callback : function(result)
            {
                var UserStore = Ext.create('Ext.data.Store', {
                    storeId:'UserStore',
                    fields: ['id','name','username','mobile','status','cardRFID','cardId'],
                    data : result.data,
                    proxy:
                    {
                        type: 'memory',
                        reader:{
                            type: 'json'
                        }
                    }
                });
                me.bindStore(UserStore);     
            }
        });
    }

});