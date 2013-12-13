/*
    This file is generated and updated by Sencha Cmd. You can edit this file as
    needed for your application, but these edits will have to be merged by
    Sencha Cmd when it performs code generation tasks such as generating new
    models, controllers or views and when running "sencha app upgrade".

    Ideally changes to this file would be limited and most work would be done
    in other places (such as Controllers). If Sencha Cmd cannot merge your
    changes and its generated code, it will produce a "merge conflict" that you
    will need to resolve manually.
*/

Ext.application({
    name: 'app',

    requires: [
        'Ext.MessageBox','app.view.Login','app.controller.Login', 'app.view.UserInfo','app.view.MerchantInfo','app.model.UserInfo','app.model.MerchantInfo','app.view.Results'
    ],

  models: ["UserInfo", "MerchantInfo","UserAccount","Items", "MerchantAccount"],
   stores: ["UserInfo", "MerchantInfo","UserAccount","Items","MerchantAccount"],
    controllers: [
  	 "Login","UserInfo",'MerchantInfo','UserMainMenu','MerchantMainMenu',"Items"
    ],
  views: [
        'UserInfo','MerchantInfo','Login', 'Results','AddMoney',"UserAccount","UserInvoice","MerchantList","MerchantBank","UserAccountDetail","MerchantTransaction",
        "MerchantTransactionDetail","MerchantAccount","UserMerchantInfo","UserMerchantItemList","UserMerchantItemDetail"
    ],

    icon: {
        '57': 'resources/icons/Icon.png',
        '72': 'resources/icons/Icon~ipad.png',
        '114': 'resources/icons/Icon@2x.png',
        '144': 'resources/icons/Icon~ipad@2x.png'
    },

    isIconPrecomposed: true,

    startupImage: {
        '320x460': 'resources/startup/320x460.jpg',
        '640x920': 'resources/startup/640x920.png',
        '768x1004': 'resources/startup/768x1004.png',
        '748x1024': 'resources/startup/748x1024.png',
        '1536x2008': 'resources/startup/1536x2008.png',
        '1496x2048': 'resources/startup/1496x2048.png'
    },

    launch: function() {
        // Destroy the #appLoadingIndicator element
        Ext.fly('appLoadingIndicator').destroy();

        // Initialize the main view

 var userInfoView = {
         	xtype: "userinfoview" 
        };
     /*    var merchantInfoView = {
         	xtype: "merchantinfoview" 
        };       
        
      var loginView = {
		xtype: "loginview" 
        }; */
        
       var addMoneyView = {
			xtype: 'addmoneyview'
        }; 
 //   Ext.Viewport.add(addMoneyView);
    
  //Ext.Viewport.add(Ext.create('app.view.UserMerchantItemList'));
         Ext.Viewport.add(Ext.create('app.view.Login'));
  //      Ext.Viewport.add(Ext.create('app.view.UserInfo'));
        Ext.Viewport.add(Ext.create('app.view.UserAccountDetail'));
        Ext.Viewport.add(Ext.create('app.view.ItemEditor'));
        Ext.Viewport.add(Ext.create('app.view.MerchantTransactionDetail'));
        Ext.Viewport.add(Ext.create('app.view.UserMerchantInfo'));
       Ext.Viewport.add(Ext.create('app.view.UserMerchantItemList'));
       Ext.Viewport.add(Ext.create('app.view.UserMerchantItemDetail'));
       
       
    },

    onUpdated: function() {
        Ext.Msg.confirm(
            "Application Update",
            "This application has just successfully been updated to the latest version. Reload now?",
            function(buttonId) {
                if (buttonId === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
