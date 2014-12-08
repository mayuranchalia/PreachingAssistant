/*
 * This file is provided for custom JavaScript logic that your HTML files might need.
 * Maqetta includes this JavaScript file by default within HTML pages authored in Maqetta.
 */

require(["dojo/ready",
         "dojo/on",
         "dijit/registry",
         "dojo/request/xhr",
         "dojo/dom",
         "dojo/json",
         "dojo/store/Memory", 
         "dijit/form/FilteringSelect", 
         'dojo/dom-style',
         'dojo/dom-construct',
         'dojox/grid/DataGrid',
         'dojox/grid/_CheckBoxSelector',
         'dojo/data/ItemFileWriteStore',
         'dojox/grid/_RadioSelector',
         "dojo/domReady!"], function(ready,on,registry,xhr,dom,json,Memory,FilteringSelect,domStyle,domConstruct){
		ready(function(){
    	 var baseURL=location.pathname.replace(/\/[^/]*$/, '') ;
    	 var service_URL=baseURL+ '/services/';    	 
    	 function reportSelection(node){
				var items = this.selection.getSelected(),
					msg = "You have selected rows ";
				var tmp = dojo.map(items, function(item){
					return this.store.getValue(item, "id");
				}, this);
				alert( msg + tmp.join(", "));
				//node.innerHTML = msg + tmp.join(", ");
			}
    	 	var data1 = {
	    			items: []
						};
	    	
    		var test_store = new dojo.data.ItemFileWriteStore({data: data1});
			
    		
    		var myStructure2 = [
	    	                    {
	    	                        type: "dojox.grid._CheckBoxSelector"
	    	                    },
	    	                    [
	    	                        [    	                          
	    	                            { name: "ID", field: "id", width: "4%" },
	    	                            { name: "LegalName", field: "lname", width: "24%" },
	    	                            { name: "InitiatedName", field: "iname", width: "24%" },
	    	                            { name: "Mobile No#", field: "mobile", width: "24%" },
	    	                            { name: "Area", field: "area", width: "24%" }
	    	                        ]
	    	                    ]
	    	                ];
    		var myGrid1 = new dojox.grid.DataGrid({
    		    store: test_store,
    		    structure: myStructure2,
    		    "class": "grid",
    		    selectionMode: "multiple"
    		}, "programGrid");
    		myGrid1.startup();
    		
    		var myGrid2 = new dojox.grid.DataGrid({
    		    store: test_store,
    		    structure: myStructure2,
    		    "class": "grid",
    		    style :"position: absolute; z-index: 900; height: 111px; width: 771px; left: 9px; top: 132px;",
    		    selectionMode: "single"
    		}, "enrollProgramGrid");
    		myGrid2.startup();
    		
    		var myGrid3 = new dojox.grid.DataGrid({
    		    store: test_store,
    		    structure: myStructure2,
    		    "class": "grid",
    		    style :"position: absolute; z-index: 900; width: 751px; left: 12.5px; top: 119px; height: 137px;",
    		    selectionMode: "multiple"
    		}, "smsGrid");
    		myGrid3.startup();
    		
/*    		dojo.connect(myGrid, "onSelectionChanged",
					dojo.hitch(myGrid, reportSelection, dojo.byId("results2")));*/
    	 
    	 updateDataGrid = function(url,domId){
			 xhr(service_URL+url,{
					handelAs:"application/json"
				}).then(function(data){
					var data_list=dojo.eval(data);
					var data1 = {
	 						items: []
	 					};
					for(var i=0; i<data_list.length; i++){
						data1.items.push(data_list[i]);
					}
					var test_store = new dojo.data.ItemFileWriteStore({data: data1});
					eSelect = dijit.byId(domId);
					eSelect.setStore(test_store);
				},function(error){
				alert(error.response.text);
			});
		 }
    	 updateDataGrid('programService/getProgramParticipants','programGrid');
    	 
    		 xhr(service_URL+'programService/getProgramTypes',{
					handelAs:"application/json"
				}).then(function(data){
					var jsondata=json.parse(data,true);
					var stateStore = new Memory({
				        data: jsondata
				    });
					var select = new FilteringSelect({
				        id: "PMProgramTypeSelect",
				        name: "PMProgramTypeSelect",				        
				        store: stateStore,
				        value:"",
				        style:"position: absolute; z-index: 900; height: 18px; top: 78px; left: 180px;",
				        searchAttr: "name"
				    }, "PMProgramTypeSelect").startup();	
				},function(error){
					alert(error.response.text);
				});
    		 
    		 xhr(service_URL+'sikshaService/getSikshaLevels',{
					handelAs:"application/json"
				}).then(function(data){
					var jsondata=json.parse(data,true);
					var stateStore = new Memory({
				        data: jsondata
				    });
					var select = new FilteringSelect({
				        id: "DIDevoteeSikshaLevelSelect",
				        name: "DIDevoteeSikshaLevelSelect",				        
				        store: stateStore,
				        value: "",
				        style:"position: absolute; z-index: 900; left: 180px; top: 400.71875px;",
				        searchAttr: "name"
				    }, "DIDevoteeSikshaLevelSelect").startup();	
				},function(error){
					alert(error.response.text);
				});
    		 
    		 updateFilterSelect = function(url,domId){
    			 xhr(service_URL+url,{
 					handelAs:"application/json"
 				}).then(function(data){
 					var jsondata=json.parse(data,true);
 					var stateStore = new Memory({
 				        data: jsondata
 				    });
 					eSelect = dijit.byId(domId);
 					eSelect.set('store', stateStore);
 				},function(error){
					alert(error.response.text);
				});
    		 }
    		 
    		 updateFilterSelect = function(url,domId1,domId2){
    			 xhr(service_URL+url,{
 					handelAs:"application/json"
 				}).then(function(data){
 					var jsondata=json.parse(data,true);
 					var stateStore = new Memory({
 				        data: jsondata
 				    });
 					eSelect1 = dijit.byId(domId1);
 					eSelect1.set('store', stateStore);
 					eSelect2 = dijit.byId(domId2);
 					eSelect2.set('store', stateStore);
 				},function(error){
					alert(error.response.text);
				});
    		 }
    			 
         
          xhr(service_URL+'programService/getProgramNames',{
				handelAs:"application/json"
			}).then(function(data){
				var data1=dojo.eval(data);
				var stateStore = new Memory({
			        data: data1
			    });
				new FilteringSelect({
			        id: "PPProgramNameSelect",
			        name: "PPProgramNameSelect",
			        store: stateStore,
			        value: "",
			        style: "position: absolute; z-index: 900; left: 180px; top: 290px;",
			        searchAttr: "name"
			    }, "PPProgramNameSelect").startup();
				new FilteringSelect({
			        id: "PAProgramNameSelect",
			        name: "PAProgramNameSelect",			        
			        store: stateStore,
			        value: "",
			        style:"height: 18px; position: absolute; z-index: 900; top: 76px; left: 180px;",
			        searchAttr: "name",
			        onChange: function(){
			        	updateDataGrid('programService/getProgramParticipants?programID='+this.get("value"),'programGrid');
			        	//alert(this.get("value"));
			        }
			    }, "PAProgramNameSelect").startup();
				new FilteringSelect({
			        id: "smsProgramNameSelect",
			        name: "smsProgramNameSelect",			        
			        store: stateStore,
			        value: "",
			        style:"position: absolute; z-index: 900; top: 81.3px; width: auto; left: 180px;",
			        searchAttr: "name",
			        onChange: function(){
			        	updateDataGrid('programService/getProgramParticipants?programID='+this.get("value"),'smsGrid');
			        	//alert(this.get("value"));
			        }
			    }, "smsProgramNameSelect").startup();
					
			},function(error){
				alert(error.response.text);
			});
          xhr(service_URL+'programService/getMentors',{
				handelAs:"application/json"
			}).then(function(data){
				var data1=dojo.eval(data);
				var stateStore = new Memory({
			        data: data1
			    });
				new FilteringSelect({
			        id: "PMPrimaryMentorSelect",
			        name: "PMPrimaryMentorSelect",			        
			        store: stateStore,
			        value: "",
			        style:"width: 200px; height: 17px; position: absolute; z-index: 900; left: 180px; top: 268px;",
			        searchAttr: "name"
			    }, "PMPrimaryMentorSelect").startup();
				
				new FilteringSelect({
			        id: "PMassistanMentorSelect",
			        name: "PMassistanMentorSelect",			        
			        store: stateStore,
			        style:style="width: 200px; height: 17px; position: absolute; z-index: 900; left: 180px; top: 317px;",
			        searchAttr: "name"
			    }, "PMassistanMentorSelect").startup();
					
			},function(error){
				alert(error.response.text);
			});
    	 var PTProgramTypeSubmit = registry.byId("PTProgramTypeSubmit");
    	 
    	 on(PTProgramTypeSubmit, "click", 
    	            function(event) {
    	               var PTProgramTypeTextBox = registry.byId("PTProgramTypeTextBox");
    	               var PTProgramDescriptionTextArea = registry.byId("PTProgramDescriptionTextArea");
    	               var url = service_URL+'programService/addNewProgramType?programType='+PTProgramTypeTextBox.value+'&programDesc='+PTProgramDescriptionTextArea.value;
    	               xhr(url,{
							handelAs:"application/json"
						}).then(function(data){
							dom.byId("programTypeSuccessResponse").innerHTML=data;
							updateFilterSelect('/programService/getProgramTypes','PMProgramTypeSelect');
						},function(error){
							alert(error.response.text);
						});
    	        });
    	 
    	 var PTProgramMasterSubmit = registry.byId("PTProgramMasterSubmit");
    	 
    	 on(PTProgramMasterSubmit, "click", 
 	            function(event) {
 	               var PMProgramTypeSelect = registry.byId("PMProgramTypeSelect").get("value");
 	               var PMProgramNameTextBox = registry.byId("PMProgramNameTextBox").get("value");
 	               var PMProgramDescriptionTextArea = registry.byId("PMProgramDescriptionTextArea").get("value");
 	               var PMProgramIntervalTextBox = registry.byId("PMProgramIntervalTextBox").get("value");
 	               var PMPrimaryMentorSelect = registry.byId("PMPrimaryMentorSelect").get("value");
 	               var PMassistanMentorSelect = registry.byId("PMassistanMentorSelect").get("value");
 	               
 	               var url = service_URL+'programService/programMaster?programType='+PMProgramTypeSelect+'&programDesc='+PMProgramNameTextBox
 	               +'&programInterval='+PMProgramIntervalTextBox+'&mentorId='+PMPrimaryMentorSelect+'&assmentorId='+PMassistanMentorSelect
 	               +'&programName='+PMProgramNameTextBox;
 	               xhr(url,{
							handelAs:"application/json"
						}).then(function(data){
							dom.byId("programMasterSuccessResponse").innerHTML=data;
							updateFilterSelect('/programService/getProgramNames','PPProgramNameSelect');
							updateFilterSelect('/programService/getProgramNames','PAProgramNameSelect');
						},function(error){
							alert(error.response.text);
						});
 	        });
    	 
    	 //Enroll Devotee
    	 var PPEnrollDevoteeSubmit = registry.byId("PPEnrollDevoteeSubmit");
    	 
    	 on(PPEnrollDevoteeSubmit, "click", 
 	            function(event) {
    		 		var enrollProgramGrid = registry.byId("enrollProgramGrid"); 
    		 		var items = enrollProgramGrid.selection.getSelected();
    		 		if(items.length>1){
    		 			alert("Multiple devotees can't be selected");
    		 			return;
    		 		}
    		 		var id = dojo.map(items, function(item){
    		 			return enrollProgramGrid.store.getValue(item, "id");
    		 		}, enrollProgramGrid);
    		 		var lname = dojo.map(items, function(item){
    		 			return enrollProgramGrid.store.getValue(item, "lname");
    		 		}, enrollProgramGrid);
 	               /*var PPDevoteeNameTextBox = registry.byId("PPDevoteeNameTextBox").get("value");
 	               var PPDevoteeNoTextBox = registry.byId("PPDevoteeNoTextBox").get("value");*/
 	               var PPProgramNameSelect = registry.byId("PPProgramNameSelect").get("value");
 	               var PPEnrolementDateSelect = registry.byId("PPEnrolementDateSelect").get("value");
 	               if(PPEnrolementDateSelect!=null){
 	            	 PPEnrolementDateSelect= dojo.date.locale.format(PPEnrolementDateSelect, 
   	 	            		 {datePattern: "dd-MM-yyyy", selector: "date"});
  	               }
 	               var url = service_URL+'programService/programParticipation?programId='+PPProgramNameSelect+'&enrolementDate='+PPEnrolementDateSelect
 	               +'&devoteeId='+id+'&devoteeName='+lname;
 	               xhr(url,{
							handelAs:"application/json"
						}).then(function(data){
							dom.byId("enrollDevoteeSucessResponse").innerHTML=data;
						},function(error){
							alert(error.response.text);
							//dom.byId("enrollDevoteeErrorResponse").innerHTML=error.response.text;
							
						});
 	        });

    	 //Add Devotee
    	 var DIDevoteeAddSubmit = registry.byId("DIDevoteeAddSubmit");
    	 
    	 on(DIDevoteeAddSubmit, "click", 
 	            function(event) {
 	               var DIDevoteeNameTextBox = registry.byId("DIDevoteeNameTextBox").get("value");
 	               var DIDevoteeInitiatedNameTextBox = registry.byId("DIDevoteeInitiatedNameTextBox").get("value");
 	               var DIDevoteeDobSelect = registry.byId("DIDevoteeDobSelect").get("value");
 	               if(DIDevoteeDobSelect!=null){
 	            	  DIDevoteeDobSelect= dojo.date.locale.format(DIDevoteeDobSelect, 
  	 	            		 {datePattern: "dd-MM-yyyy", selector: "date"});
 	               }
 	               var DIDevoteeMobileNoTextBox = registry.byId("DIDevoteeMobileNoTextBox").get("value"); 	           
 	               var DIDevoteeAltMobileNoTextBox = registry.byId("DIDevoteeAltMobileNoTextBox").get("value"); 
 	               var DIDevoteeAddressTextArea = registry.byId("DIDevoteeAddressTextArea").get("value"); 
 	               var DIDevoteeIntroDateSelect = registry.byId("DIDevoteeIntroDateSelect").get("value");
 	               if(DIDevoteeIntroDateSelect!=null){
 	            	 DIDevoteeIntroDateSelect= dojo.date.locale.format(DIDevoteeIntroDateSelect, 
  	 	            		 {datePattern: "dd-MM-yyyy", selector: "date"});
 	               }
 	               var DIDevoteeSikshaLevelSelect = registry.byId("DIDevoteeSikshaLevelSelect").get("value");
 	               var DIDevoteeEmailIdTextArea = registry.byId("DIDevoteeEmailIdTextArea").get("value");
 	               var isDevoteeMentorCheckBox = registry.byId("isDevoteeMentorCheckBox").get("checked");
 	               var url = service_URL+'devoteeService/addNewDevotee?devoteeName='+DIDevoteeNameTextBox+'&devoteeInitiatedName='+DIDevoteeInitiatedNameTextBox
 	               +'&devoteeDob='+DIDevoteeDobSelect+'&devoteeSMSPhone='+DIDevoteeMobileNoTextBox+'&devoteeALTPhone='+DIDevoteeAltMobileNoTextBox
 	               +'&devoteeIntroDate='+DIDevoteeIntroDateSelect+'&devoteeArea='+DIDevoteeAddressTextArea+'&devoteeEmail='+DIDevoteeEmailIdTextArea
 	               +'&isMentor='+isDevoteeMentorCheckBox;
 	               xhr(url,{
							handelAs:"application/json"
						}).then(function(data){
							dom.byId("devoteeAddSuccessResponse").innerHTML=data;													
						},function(error){
							alert(error.response.text);
						});
 	        });   
    	 
    	 //Attendance
    	 var PAProgramAttendanceSubmit = registry.byId("PAProgramAttendanceSubmit");
    	 on(PAProgramAttendanceSubmit, "click", 
  	           function(event) {
    		 var PAProgramNameSelect = registry.byId("PAProgramNameSelect").get("value");
    		 var PAprogramDateSelect = registry.byId("PAprogramDateSelect").get("value");
              if(PAprogramDateSelect!=null){
            	  PAprogramDateSelect = dojo.date.locale.format(PAprogramDateSelect, 
 	            		 {datePattern: "dd-MM-yyyy", selector: "date"});
              }
            var programGrid = registry.byId("programGrid"); 
          	var items = programGrid.selection.getSelected();
          	var tmp = dojo.map(items, function(item){
			return programGrid.store.getValue(item, "id");
          		}, programGrid);
          	tmp =tmp.join(",");
          	 var url = service_URL+'programService/programAttendance?programId='+PAProgramNameSelect+'&devoteeIds='+tmp
          	 +'&programdate='+PAprogramDateSelect;
          	 alert(url);
          	xhr(url,{
				handelAs:"application/json"
          		}).then(function(data){
          			dom.byId("programAttendanceSuccessResponse").innerHTML=data;
          		},function(error){
				alert(error.response.text);
			});

    	 });
    	 
    	 query_store = function(data){
    		 if(data.length>=1){    			 
    			 updateDataGrid('devoteeService/getDevotee?text='+data,'enrollProgramGrid');
    		 
    		 }
    	 }
     });
		 window.onload = function() 
		  { document.getElementById("hideAll").style.display = "none"; }
});
