var l = window.location;
var base_url = l.protocol + "//" + l.host + "/" + l.pathname.split('/')[1]+"/app/";

 $( document ).ready(function() {
	 //alert(base_url);
$(".modal").find(".modal-header").css("background", "#000");
$(".modal").find(".modal-content").css("border","4px solid #000");
$(".modal").find(".modal-title").css("color","white");
 $('html, body').find("input[type='file']").css("border","none"); 
$('html, body').find(".c-select").addClass("btn-custom").css("margin-top","18px"); 
$('.c-select').find('option').css("height", "25px");
//$('html, body').find(".panel_toolbox").css("border-radius","5px").css("background","#169F85"); 
$('html, body').find(".glyphicon-pencil").css("color","white"); 
$('html, body').find(".glyphicon-trash").css("color","white"); 
//$('html, body').find(".btn-success").css("border-radius","5px");
//$('.modal').find(".btn-info").css("border-radius","5px");
//$('html, body').find(".btn").css("border-radius","5px");
$('html, body').find(".disableButton").prop('disabled', false);
$('#action').hide();
$('html, body').find("iframe").css("width","500px").css("width","147px");
 //$("#txtEditor").Editor(); 
});

//----------Dynamic Function Start Here-----------------		
	function showModal(modalId){
		$('.err-msg').remove();
		$('#'+modalId).modal('show');
	}
		
	function lockModal(modalId){
		$('#'+modalId).modal({
            backdrop: 'static',
            keyboard: false,
        })
	}
	
	function close_modal(modalId){
	
		$('#'+modalId).modal('hide');	
		$("#action option:selected").removeAttr("selected");
		$('input[type="checkbox"]:checked').removeAttr('checked');	
		$('#customButton').hide();	

    }

	//-------------------------for cancel save --------------

    function cancelSave(formId){
	    $('#'+formId).find(".btn-warning").click();
		$('#aboutUsGridView').show();
		$('#'+formId).hide();
	    $('#customButton').hide();	
	    $('input[type="checkbox"]:checked').prop("checked", false )
    }

    //--------For Select all checkbox--------------------

    function selectAll(){
	  $('.checkall').parent()
			.parent()
			.parent()
			.parent()
			.find('input[class=surveyorcheck]:checkbox')
			.each(function(){ 
				if($('input[class=checkall]:checkbox:checked').length == 0){ 
					$(this).prop("checked", false );
					 $('#customButton').hide();	
                    //$(this).val('0');
				} else {
					$(this).prop("checked", true);
					$('#customButton').show();
                   // $(this).val('1');
				} 
			});
	 
    }
 
    //--------For Select single checkbox--------------------
 
    function singleSelect(){
    	if($('input[type="checkbox"]:checked').length>0){
    			 
    			$('#customButton').show();
    			$('#action').show();
    		 }else{
    			
    		 $('#customButton').hide();	
    		 $('#action').hide();
    		 } 
    		if($('input[type="checkbox"]:checked').length>1){
    			swal('Please select at least one Record')
    			$('input[type="checkbox"]:checked').prop("checked", false );
    			$('#customButton').hide();	
    		}
    		 
             /* $('.surveyorcheck').val('.surveyorcheck'.checked ? "1" : "0" );
    		 if($('input[type="checkbox"]:checked').length>0){
    			$('#allowActiveButton').show();
    		 }else{
    		 $('#allowActiveButton').hide();	
    		 }  */
     }


function postAndRedirect(url, postData)
	{
		
		var postFormStr = "<form method='POST' action='" + url + "'>\n";

		for (var key in postData)
		{
			if (postData.hasOwnProperty(key))
			{
				postFormStr += "<input type='hidden' name='" + key + "' value='" + postData[key] + "'></input>";
				
			}
		}
		
		postFormStr += "</form>";
		var formElement = $(postFormStr);
		$('body').append(formElement);
		$(formElement).submit();
	}
	
	function resetForm(formId){
			$('#'+formId)[0].reset();
			$('#'+formId).find('input[type=file]').attr('src',base_url+'assets/img/images.png');
			$('.imagePreview').attr('src',base_url+'assets/img/images.png')
		}

	function showLoader(){
			$.blockUI({message:'<h1> Please wait...</h1>'});
			$("#flash").show();
		}

	function hideLoader(){
			$("#flash").hide();
			 $(document).ajaxStop($.unblockUI);
		}
	
 
	function appendMessageBody(formId){
		$('.err-msg').remove();
		$('#'+formId+' .messageAppend').remove();
		$('#'+formId).before("<div class='messageAppend'><div class='portlet-body form' id='scr2'>"+
			"<div id='succ2' style='display: none;'>"+
				"<div class='alert alert-success'>"+
					"<strong>Success!</strong><span id='success2'></span>"+ 
				"</div>"+
			"</div>"+
			"<div style='display: none;' id='err2'>"+
				"<div class='alert alert-danger'>"+
					"<strong>Error!</strong> <span id='error2'></span>"+
				"</div>"+
			"</div>"+
			
		"</div></div>");
		}
	 function showSuccessMessage(formId,msg){
	 			$('.err-msg').remove();
				$("#error2").html("");
                $("#success2").html(msg);
                $('#err2').css({'display': 'none'});
                $('#succ2').css({'display': 'block'});
                $('html, body').animate({
                    scrollTop: $("#scr2").first().offset().top - 250
                }, 1000);
                $('#'+formId)[0].reset();
					setTimeout(
                        function ()
                        {	
                        	$('.err-msg').remove();
							$('#succ2').css({'display': 'none'})
							//$('#'+formId).find(".btn-default").click();
						//location.reload();
						
                        }, 4000);	
				}
	 function showErrorMessage(formId,msg){
		 $('.err-msg').remove();
		  //$('.err-msg').remove();
		$.each(msg,function (index, val) {
                 var type = $("#" + index).prop("tagName");
                    var parent = $("#" + index).parent();
					//$(parent' .append').remove();
           $('<span class="required err-msg append" id="err-pan" aria-required="true"> ' + val + ' </span>').appendTo(parent);
                    //$("" + type + "[name=" + index + "]").parent().addClass("has-error") 
               });
			   
			   /* setTimeout(function () {
                  $('.err-msg').fadeOut();
				  $('html, body').find(".disableButton").prop('disabled', false);
             }, 5000); */
		}
		
		
	function showDatabaseErrorMessage(formId,msg){
		$('.err-msg').remove();
		$("#error2").html(msg);
		$("#success").html("");
		$('#err2').css({'display': 'block'});
		$('#succ2').css({'display': 'none'});
		
		$('html, body').animate({
			scrollTop: $("#scr2").first().offset().top - 250
		}, 1000);
		setTimeout(
				function ()
				{	
				 $('#err2').css({'display': 'none'})
				
				}, 5000);
		}
	
//--------------------------Selected Checkbox id-----------------//
	function getSelectdCheckboxId(clas)
	{
		
		var Ids=[];
			$('input[class="'+clas+'"]:checked').each(function(){
				var Id=$(this).attr("id");
				 Ids.push(Id);
			});	
		
		return Ids
	}
	
	
//--------For Show selected image --------------->

function showimagepreview(input) {
	// alert(input);
    if (input.files && input.files[0]) {
    var filerdr = new FileReader();
    filerdr.onload = function(e) {
    $('#imgprvw').attr('src', e.target.result);
	//console.log($('#imgprvw').attr('src', e.target.result));
    }
    filerdr.readAsDataURL(input.files[0]);
    }
	
    }
//----------To Save data -----------------		
		
function saveRecord(formId, url, return_value)
{
	var form = $('#'+formId)[0]; // You need to use standart javascript object here
    var formData = new FormData(form);
    
    $.ajax({
        url: base_url+url,
        type: "POST",
        dataType: "json",
        contentType: false,
        processData: false,
        data: formData,
		beforeSend: function(){
		    if(return_value == 1) {
		        showLoader();    
		    }
		    if(return_value == 2) {
		        showLoader();    
		    }
		    if(return_value == 5) {
		        showLoader();    
		    }
		}, 
        success: function (obj)
        {	
		    if(return_value == 1) {
		        hideLoader();
		        
		        var str = "";
    		    if(obj.err == 0) {
    		        if(obj.msg == ""){
    		            str = "Success!"
    		        }else{
    		          
    		        }
        		        
    		        $.toast({
                      heading: 'Success',
                      text: str,
                      position: String("top-right"),
                      icon: 'success',
                      stack: false,
                      loaderBg: '#f96868'
                    });
                    
                    setTimeout(function(){
    					location.reload();
    				},2000);
    		    }
    		    if(obj.err == 100) {
    		        if(obj.msg == ""){
    		            str = "Success!"
    		        }else{
    		            str = obj.msg;
    		        }
        		        
    		        $.toast({
                      heading: 'Success',
                      text: str,
                      position: String("top-right"),
                      icon: 'success',
                      stack: false,
                      loaderBg: '#f96868'
                    });
                    
                    setTimeout(function(){
    					window.location.href = obj.url;
    				},2000);
    		    }if(obj.err == 1111) {
    		        $.toast({
                      heading: 'Required Field',
                      text: obj.msg,
                      position: String("top-right"),
                      icon: 'error',
                      stack: false,
                      loaderBg: '#46c35f'
                    });
    		    }
    		    if(obj.err == 1) {
    		        $.each(obj.msg,function (index, val) {
    		            str += val + "<br/>"; 
            		});
    		        $.toast({
                      heading: 'Required Field',
                      text: str,
                      position: String("top-right"),
                      icon: 'error',
                      stack: false,
                      loaderBg: '#46c35f'
                    });
    		    }if(obj.err == 2) {
    		        
    		        $.toast({
                      heading: 'Email Error',
                      text: obj.msg,
                      position: String("top-right"),
                      icon: 'error',
                      stack: false,
                      loaderBg: '#f2a654'
                    });
    		    }if(obj.err == 3) {
    		        $.toast({
                      heading: 'Success',
                      text: 'Success!',
                      position: String("top-right"),
                      icon: 'info',
                      stack: false,
                      loaderBg: '#f96868'
                    });
                    
                    setTimeout(function(){
    					location.reload();
    				},2000);
    		    }   
		    }else if(return_value == 2) {
		        hideLoader();
		        
		        if (obj.err == 0) {
    				appendMessageBody(formId);
    				showSuccessMessage(formId,obj.msg);
    					//close_modal('add-multi-doc');
    					$('#uploadID-'+obj.lineItemID+'').css("color", "green");
    					close_modal('add-multi-doc');
    				
    			}
              
    			if (obj.err == 1) {
    				$.toast({
                      heading: 'Required Field',
                      text: obj.msg,
                      position: String("top-right"),
                      icon: 'danger',
                      stack: false,
                      loaderBg: '#f96868'
                    });
                    
    				$('html, body').find(".disableButton").prop('disabled', true);				
    			}
    			
    			if (obj.err == 2) {
    				appendMessageBody(formId);
    				showDatabaseErrorMessage(formId,obj.msg);  
    			}
    			if(obj.err == 3) {
    				swal(obj.msg);
    				setTimeout(function(){
    					location.reload();
                    },2000) 
    			}   
    			if(obj.err == 5) {
    				$.toast({
                      heading: 'Success',
                      text: obj.msg,
                      position: String("top-right"),
                      icon: 'success',
                      stack: false,
                      loaderBg: '#f96868'
                    });
                    
    				setTimeout(function(){
    					location.reload();
                    },2000) 
    			}   
		    }else if(return_value == 3) {
		        if (obj.err == 0)
				{
				    appendMessageBody(formId);
				    //showSuccessMessage(formId,obj.msg); 
					setTimeout(function(){
						
					window.location.href = base_url+'/manage_draft';
						
					},1000) 
				
				}
          
				if (obj.err == 1)
				{
				    showErrorMessage(formId,obj.msg); 
				    $('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);  
				}
		    }else if(return_value == 4) {
		        if (obj.err == 0)
				{
				    appendMessageBody(formId);
				    //showSuccessMessage(formId,obj.msg); 
					setTimeout(function(){
						
					window.location.href = base_url+'/manage_costsheet';
						
					},1000) 
				
				}
          
				if (obj.err == 1)
				{
				    showErrorMessage(formId,obj.msg); 
				    $('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);  
				}
		    }else if(return_value == 5) {
		        hideLoader();
		        if (obj.err == 0)
				{
				    appendMessageBody(formId);
					setTimeout(function(){
					    window.location.href = base_url + 'manage_quotations';
					},1000) 
				
				}if (obj.err == 10000)
				{
				    appendMessageBody(formId);
				    setTimeout(function(){
					    window.location.href = base_url + 'manage_template';
					},1000)
				
				}
				if (obj.err == 20000)
				{
				    appendMessageBody(formId);
				    setTimeout(function(){
					    window.location.href = obj.msg;
					},1000)
				
				}if (obj.err == 180)
				{
				    appendMessageBody(formId);
				    setTimeout(function(){
					    window.location.href = base_url + 'manage_jobs';
					},1000)
				
				}
				if (obj.err == 18)
				{
				    appendMessageBody(formId);
				    setTimeout(function(){
					    window.location.href = base_url + 'manage_jobs';
					},1000)
				
				}if (obj.err == 9)
				{
				    appendMessageBody(formId);
				    $.toast({
                      heading: 'Success',
                      text: str,
                      position: String("top-right"),
                      icon: 'success',
                      stack: false,
                      loaderBg: '#f96868'
                    });
				}
          
				if (obj.err == 1)
				{
				    showErrorMessage(formId,obj.msg); 
				    $('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);  
				}
		    }else if(return_value == 600) {
		        if (obj.err == 0)
				{
				    appendMessageBody(formId);
				    setTimeout(function() {
				        location.reload();
				    }, 2000)
				}
          
				if (obj.err == 1)
				{
    				showErrorMessage(formId,obj.msg); 
    				$.toast({
                      heading: 'Required',
                      text: obj.msg.exclus,
                      position: String("top-right"),
                      icon: 'danger',
                      stack: false,
                      loaderBg: '#f96868'
                    });
    				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);
					setTimeout(function(){
						location.reload();
					},2000) 
				}
		    }else if(return_value == 6) {
		        if (obj.err == 0)
				{
				    $.toast({
                      heading: 'Success',
                      text: obj.msg,
                      position: String("top-right"),
                      icon: 'success',
                      stack: false,
                      loaderBg: '#f96868'
                    });
                    
				    appendMessageBody(formId);
				}
          
				if (obj.err == 1)
				{
    				showErrorMessage(formId,obj.msg); 
    				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);
					setTimeout(function(){
						location.reload();
					},2000) 
				}
				if (obj.err == 202)
				{
				    $.toast({
                      heading: 'Error',
                      text: obj.msg,
                      position: String("top-right"),
                      icon: 'danger',
                      stack: false,
                      loaderBg: '#f96868'
                    });
				}
		    }else if(return_value == 7) {
		        if (obj.err == 0)
				{
				appendMessageBody(formId);
				    $( ".currencyC" ).each(function( i ) {
      					this.innerHTML = val; 
  				    });
				    if(val == 'USD')
				    {
				   		$("span.currencyConvert").each(function(index) { 
	            		$(this).text(Math.round($(this).html() / usdToEuroExchRate).toFixed(2));
	        		 	});
				    }
				    if(val == 'AED')
				    {
				   		$("span.currencyConvert").each(function(index) { 
	            		$(this).text(Math.round($(this).html() * usdToEuroExchRate).toFixed(2));
	        		 	});
				    }
				}
          
				if (obj.err == 1)
				{
				showErrorMessage(formId,obj.msg); 
				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);
					setTimeout(function(){
								
							location.reload();
								
							},2000) 
				}
		    }else if(return_value == 8) {
		        if (obj.err == 0)
				{
					$('#contactPerson').find('option').remove();
					$('#payment_terms').find('option').remove();
					appendMessageBody(formId);
					if(obj.data)
					{
						 var objr = JSON.parse(obj.data)
						 var objp = JSON.parse(obj.paymentdata)
						 $.each(objr,function(key, value)
			                {
			                    $("#contactPerson").append('<option value=' + value.id + '>' + value.name + '</option>');
			               });
						  if(objp)
						  {
						  	$("#payment_terms").append('<option value=' + objp.payment_terms + '>' + objp.payment_term + '</option><option value=' + objp.payment_terms2 + '>' + objp.payment_term2 + '</option><option value=' + objp.payment_terms3 + '>' + objp.payment_term3 + '</option>');
						  }
					}
				
				}
          
				if (obj.err == 1)
				{
				showErrorMessage(formId,obj.msg); 
				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);
					setTimeout(function(){
								
							location.reload();
								
							},2000) 
				}
		    }else if(return_value == 9) {
		        if (obj.err == 0)
				{
				appendMessageBody(formId);
				showSuccessMessage(formId,obj.msg); 
					setTimeout(function(){
						
					// location.reload();
					window.location.href = base_url+'/login';
						
					},2000) 
				
				}
          
				if (obj.err == 1)
				{
				showErrorMessage(formId,obj.msg); 
				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);
					setTimeout(function(){
								
							location.reload();
								
							},2000) 
				}
		    }else if(return_value == 10) {
		        if (obj.err == 0)
				{
				//appendMessageBody(formId);
				//showSuccessMessage(formId,obj.msg); 
				swal(obj.msg);
					setTimeout(function(){
						
					window.location.href=base_url;
						
					},2000) 
				
				}
          
				if (obj.err == 1)
				{
				showErrorMessage(formId,obj.msg); 
				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
		    }else if(return_value == 11) {
		        if (obj.err == 0)
				{
				appendMessageBody(formId);
				showSuccessMessage(formId,obj.msg); 
					setTimeout(function(){
						
					location.reload();
						
					},2000) 
				
				}
          
				if (obj.err == 1)
				{
				showErrorMessage(formId,obj.msg); 
				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
				if(obj.err == 3)
				{
					swal(obj.msg);
					setTimeout(function(){
								
							location.reload();
								
							},2000) 
				}
		    }else if(return_value == 12) {
		        if (obj.err == 0)
				{
				appendMessageBody(formId);
				showSuccessMessage(formId,obj.msg); 
					setTimeout(function(){
						
					location.reload();
						
					},2000) 
				
				}
          
				if (obj.err == 1)
				{
				showErrorMessage(formId,obj.msg); 
				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
		    }else if(return_value == 13) {
		        if (obj.err == 0)
				{
				//appendMessageBody(formId);
				swal(obj.msg); 
					setTimeout(function(){
						
					location.reload();
						
					},2000) 
				
				}
          
				if (obj.err == 1)
				{
				showErrorMessage(formId,obj.msg); 
				$('html, body').find(".disableButton").prop('disabled', true);				
				}
			
				if (obj.err == 2)
				{
					appendMessageBody(formId);
					showDatabaseErrorMessage(formId,obj.msg);  
				}
		    }
        }
    });
}
 

function saveLikes(userId,url,postID)
{
   
    var formData = {'user_id':userId,'post_id':postID};

    return $.ajax({
        url: base_url+url,
        type: "POST",
        
        data: formData,
		beforeSend: function(){
		// showLoader();
			}, 
        success: function (obj)
        {	
		 hideLoader();
        }

    });
} 
function savefollows(profile_id,url)
{
   
    var formData = {'profile_id':profile_id};

    return $.ajax({
        url: base_url+url,
        type: "POST",
        
        data: formData,
		beforeSend: function(){
		// showLoader();
			}, 
        success: function (obj)
        {	
		 hideLoader();
        }

    });
} 
//-------------To Delete data -----------

function deleteRecord(formId,url,modelid)
{
   var form = $('#'+formId)[0];
   var formData = new FormData(form);

   return $.ajax({
	   url : base_url+url,
	   type: "POST",
	   dataType: "json",
	   contentType: false,
	   processData: false,
	   data: formData,
	   beforeSend: function(){
	   	   close_modal(modelid);	
		   // showLoader();
	   },
	   success: function(obj){
		   hideLoader();
	   }
   });
}
 //<!--------------For View Data By Selected id------------>
 
 function viewDetails(id,url, modelid, return_value, output=null){

	data={id:id};
	return $.ajax({
    	type:"POST",
    	url:base_url+url,
    	data:data,
	 	beforeSend: function(){
            // showLoader();
		},
		success:function(obj){
		    //  hideLoader();
		    if(return_value ==1) {
    		    res = JSON.parse(obj);
        		if(res) {
        			$.each(res,function(key, value) {
                        $("#category").append('<option value=' + value.id + '>' + value.title + '</option>');
                    });
                    //showModal(modelid);
                    $('.addCostCat').show();
        		}
		    }else if(return_value == 2) {
		        res = JSON.parse(obj);
        		if(res)
        		{
        			 $.each(res,function(key, value) {
                    	if(value.doc_type == '.pdf') {
                    		imgURL = 'pdf-image.png';
                    	} else if(value.doc_type == '.png' || value.doc_type == '.jpg' || value.doc_type == '.jpeg' || value.doc_type == '.gif') {
                    		imgURL =value.doc_image;
                    	}else{
                    	    imgURL = "download.png";
                    	}
                    	
                        var html =  '<div class="preview-image preview-show-' + value.id + '">' +
                                      '<div class="image-cancel" data-no="' + value.id + '">x</div>' +
                                      '<div class="image-zone"><img id="pro-img-' + value.id + '" src="http://costestimator.projexonlineservices.com/uploads/document/' + imgURL + '" title="'+value.doc_image+'"></div>' +
                                      '<div class="tools-edit-image"><a href="http://costestimator.projexonlineservices.com/uploads/document/' + value.doc_image + '" download data-no="' + value.id + '" class="btn btn-light btn-edit-image"><i class="fa fa-download" aria-hidden="true"></i></a></div>' +
                                      '</div>';
    
                        output.append(html);
                    });
        			showModal(modelid);
        		}
		    }else if(return_value ==3) {
		        res = JSON.parse(obj);
        		if(res)
        		{
        			$('#subpcategory_id').val(id);
        			$('#editsubcategory').val(res.title);
        			$('#editsubqty').val(res.quantity);
        			$('#editsub_product_unit').val(res.unit);
        			showModal(modelid);
        		}	
		    }else if(return_value = 4) {
		        res = JSON.parse(obj);
        		$('#pcategory_id').val(id);
        		showModal(modelid);
		    }else if(return_value == 5) {
		        res = JSON.parse(obj);
    			if (res.err == 0)
    				{
    				 $('#lngid').val(id);
    				 $('#edit_language').val(res.data.language_name);
    				 $('#edit_proficiency option[value="'+res.data.proficiency+'"]').attr('selected', 'selected');
    				 showModal(modelid);			
    				}          
    				if (res.err == 1)
    				{
    				showErrorMessage(formId,obj.msg); 
    				$('html, body').find(".disableButton").prop('disabled', true);				
    				}
    			
    				if (res.err == 2)
    				{
    					appendMessageBody(formId);
    					showDatabaseErrorMessage(formId,obj.msg);  
    				}
    				if(res.err == 3)
    				{
    					swal(res.msg);
    					setTimeout(function(){
    								
    							location.reload();
    								
    							},2000) 
    				}
		    }else if(return_value == 6) {
		        res = JSON.parse(obj);
    			if (res.err == 0)
    				{
    				 $('#cgradid').val(id);
    				 $('#edit_current_grade').val(res.data.current_grade);
    				 $('#edit_current_school_name').val(res.data.current_school_name);
    				 $('#edit_current_school_add1').val(res.data.current_school_address_1);
    				 $('#edit_current_school_add12').val(res.data.current_school_address_2);
    				 $('#current-grade-zipcode').val(res.data.zip_code);
    				 $('#current-grd-country option[value="'+res.data.country_id+'"]').attr('selected', 'selected');
    				 stateListEdit('current-grd-country','current-grd-state','current-grd-city',res.data.state_id);
    				cityListEdit(res.data.state_id,'current-grd-city',res.data.city_id);
    				 showModal(modelid);			
    				}          
    				if (res.err == 1)
    				{
    				showErrorMessage(formId,obj.msg); 
    				$('html, body').find(".disableButton").prop('disabled', true);				
    				}
    			
    				if (res.err == 2)
    				{
    					appendMessageBody(formId);
    					showDatabaseErrorMessage(formId,obj.msg);  
    				}
    				if(res.err == 3)
    				{
    					swal(res.msg);
    					setTimeout(function(){
    								
    							location.reload();
    								
    							},2000) 
    				}
		    }else if(return_value == 7) {
		        res = JSON.parse(obj);
        		if(res.err == 0)
        		{
        			$('#first_name').val(res.data.first_name);
        			$('#last_name').val(res.data.last_name);
        			$('#headline').val(res.data.headline);
        			$('#dob').val(res.data.dob);
        			$('#zipcode').val(res.data.zipcode);
        			$('#gender').val(res.data.gender);
        			$('#introduction').val(res.data.introduction);
        			$('#citizenship option[value='+res.data.citizenship+']').attr('selected', 'selected');
        			$('#country option[value='+res.data.country+']').attr('selected', 'selected');
        			stateListEdit('country','state','city',res.data.state);
        			cityListEdit(res.data.state,'city',res.data.city);
        			
        			showModal(modelid);
        		}
        		if(res.err == 1)
        		{
        			swal(res.msg);
        		}
        		if(res.err == 3)
        		{
        			swal(res.msg);
        			setTimeout(function(){
        						
        					location.reload();
        						
        					},2000) 
        		}
		    }
	    }
	});

}

 function viewAllDetails(id,url,table){

	data={id:id,table:table};
	return $.ajax({
	type:"POST",
	url:base_url+url,
	data:data,
	 	beforeSend: function(){
			// showLoader();
			},
		success:function(r){
			 hideLoader();
		}
	});

}


 //<!--------------For View Grid view ------------>

function viewGridView(url){

	return $.ajax({
	type:"POST",
	url:base_url+url,
	 beforeSend: function(){
		// showLoader();
			},
	success:function(r){
			 hideLoader();
		}
	});

} 
 
 
 
 
 
 
/* var isCtrl = false;
document.onkeyup=function(e)
{
    if(e.which == 17)
    isCtrl=false;
}
document.onkeydown=function(e)
{
    if(e.which == 17)
    isCtrl=true;
    if((e.which == 85) || (e.which == 67) && (isCtrl == true))
    {
        return false;
    }
}
var isNS = (navigator.appName == "Netscape") ? 1 : 0;
if(navigator.appName == "Netscape") document.captureEvents(Event.MOUSEDOWN||Event.MOUSEUP);
function mischandler(){
    return false;
}
function mousehandler(e){
    var myevent = (isNS) ? e : event;
    var eventbutton = (isNS) ? myevent.which : myevent.button;
    if((eventbutton==2)||(eventbutton==3)) return false;
}
document.oncontextmenu = mischandler;
document.onmousedown = mousehandler;
document.onmouseup = mousehandler;  */