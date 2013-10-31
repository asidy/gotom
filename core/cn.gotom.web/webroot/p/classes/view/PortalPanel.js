/*
 * File: classes/view/PortalPanel.js
 *
 * This file was generated by Sencha Architect version 2.2.2.
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

Ext.define('Gotom.view.PortalPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PortalPanel',

    requires: [
        'Gotom.view.PortalHeader',
        'Gotom.view.PortalTree',
        'Gotom.view.PortalFooter',
        'Gotom.view.PortalTab'
    ],

    layout: {
        type: 'border'
    },
    header: false,
    title: 'PortalPanel',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'PortalHeader',
                    id: 'header',
                    title: 'headerPanel',
                    region: 'north'
                },
                {
                    xtype: 'PortalTree',
                    id: 'PortalTree',
                    title: 'treePanel',
                    store: 'PortalTreeStore',
                    region: 'west',
                    split: true,
                    listeners: {
                        itemclick: {
                            fn: me.onTreelItemClick,
                            scope: me
                        }
                    }
                },
                {
                    xtype: 'PortalFooter',
                    title: 'footerPanel',
                    region: 'south'
                },
                {
                    xtype: 'PortalTab',
                    region: 'center'
                }
            ]
        });

        me.callParent(arguments);
    },

    onTreelItemClick: function(dataview, record, item, index, e, eOpts) {
        var tree = Ext.getCmp('PortalTree');
        tree.setTitle(record.data.text);
    },

    getHeader: function() {
    
    },

    getTreeStore: function() {
        var jsonStore = Ext.create("Ext.data.TreeStore",
            {
                model : 'Gotom.model.TreeModel',
                proxy :
                {
                    type : "ajax",
                    url : 'http://localhost:8080/p/right!tree.do'
                },
                clearOnLoad : true,
                nodeParam : "id"
            });
        return jsonStore;
    }

});