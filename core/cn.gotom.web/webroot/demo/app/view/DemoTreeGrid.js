/*
 * File: app/view/DemoTreeGrid.js
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

Ext.define('MyApp.view.DemoTreeGrid', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.DemoTreeGrid',

    id: 'treeGridPanel',
    title: 'Demo',
    rootVisible: false,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            viewConfig: {

            },
            columns: [
                {
                    xtype: 'treecolumn',
                    dataIndex: 'text',
                    text: '名称',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'code',
                    text: '编码'
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    id: 'demoToolbar',
                    items: [
                        {
                            xtype: 'button',
                            id: 'btnNew',
                            text: '添加',
                            listeners: {
                                click: {
                                    fn: me.onBtnNewClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            id: 'btnEdit',
                            text: '修改',
                            listeners: {
                                click: {
                                    fn: me.onBtnEditClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            id: 'btnDel',
                            text: '删除'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onBtnNewClick: function(button, e, eOpts) {
        var win = Ext.create('MyApp.view.DemoWindow');
        win.setTitle('ssss');
        win.show();
    },

    onBtnEditClick: function(button, e, eOpts) {
        this.store.reload();
    }

});