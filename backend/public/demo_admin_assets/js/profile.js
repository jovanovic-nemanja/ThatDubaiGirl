//var d = new customFunction();
var l = window.location;
// var base_url = "http://costestimator.theindustrialparts.com/admin/app/";
var base_url = l.protocol + "//" + l.host + "/" + l.pathname.split('/')[1]+"/app/";

$(document).ready(function(){
	//alert(base_url);
	
/* swal({
  title: 'Error!',
  text: 'Do you want to continue',
  type: 'error',
  confirmButtonText: 'Cool'
}); */

});



function oneStepBack(){
window.history.go(-1);
return false;

}

function updateProfile(){
	lockModal('myModal');
	showModal('myModal');
	//alert('fsd');
}
 function showimagepreview(input) {
	
	 //alert('hello');
    if (input.files && input.files[0]) {
    var filerdr = new FileReader();
    filerdr.onload = function(e) {
    $('#imgprvw').attr('src', e.target.result);
	//console.log($('#imgprvw').attr('src', e.target.result));
    }
    filerdr.readAsDataURL(input.files[0]);
    }
	
    }

$(function() {
	
     $('#profileimg, #profileimgs').change(function (){
       var ext = $(this).val().split('.').pop().toLowerCase();
	   if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
		   $('.file-message').html("Warning! Upload only image").css({"width":"51%","color": "red"});;
		   return false;
		}else{
		 $('.file-message').html("");
		}
     });
});
	 
//-------------------------for cancel save --------------

 function cancelSave(formId){
		$('#'+formId).find(".btn-warning").click();
		$('#aboutUsGridView').show();
		$('#'+formId).hide();
		 $('#customButton').hide();	
		 $('input[type="checkbox"]:checked').prop("checked", false )
}



	
//--------For Save About us------------------

    function saveProfileImg(formId,url){
	    var pro=saveRecord(formId,url, 13);
	}


	function saveProfileDoc(formId,url){
	    var pro=saveRecord(formId,url, 2);
	}

    function saveProfile(formId,url){
	    var pro=saveRecord(formId,url, 1);
	}

	function saveTemplateDraft(formId,url){
	    var pro=saveRecord(formId,url, 3);
	}


	function saveTemplate(formId,url){
	    var pro=saveRecord(formId,url, 4);
	}

	function genrateTemplate(formId,url){
	    var pro=saveRecord(formId,url, 5);
	}

	function updateFormData(formId,url){
	    var pro=saveRecord(formId,url, 6);
	}

	function updateData(formId,url){
	    var pro=saveRecord(formId, url, 6);
	}

	function updateData1(formId,url){
	    var pro=saveRecord(formId, url, 600);
	}

	function updateDataCurrency(formId,url,val, usdToEuroExchRate){
	    var pro=saveRecord(formId,url, 7);
	}

	function updateCustomerData(formId,url){
	    var pro=saveRecord(formId,url, 8);
	}



    function deleteProfile(formId,url,modelid){
		var pro=deleteRecord(formId,url,modelid);
		pro.success(function(obj){
			if(obj.status == 0)
			{
					swal('Somthing went wrong!');
					setTimeout(function(){
						
					location.reload();
						
					},2000) 
			}

			    if (obj.status == 1)
				{
					
					swal('Item deleted successfully!');
					setTimeout(function(){
						
					location.reload();
						
					},2000) 
								
				}
			
				
		});
	}


	function verifyOTP(formId,url){
	    var pro=saveRecord(formId,url, 9);
	}

	function saveNewPassword(formId,url){
	    var pro=saveRecord(formId,url, 10);
	}

	function updateLikes(userId,url,postID){
		var pro = saveLikes(userId,url,postID);
		pro.success(function (obj){
			var objr = JSON.parse(obj)
			if(objr.err == 1)
			{
				$(this).addClass('current');
			}
		});
	}

	function follwOrUnfollw(profile_id,url){
		var pro = savefollows(profile_id,url);
		pro.success(function (obj){
			var objr = JSON.parse(obj)
			if(objr.err == 1)
			{
				$(this).addClass('current');
			}
		});
	}

	function saveLanguage(formId,url){
	    var pro=saveRecord(formId,url, 11);
	}

	function saveUserCar(formId,url){
	    var pro=saveRecord(formId,url, 12);
	}

	function viewProfile(modelid,id,url)
	{

		var pro = viewDetails(id,url, modelid, 7, null);
		
	}

	function viewLineItem(modelid,id,cat,url)
	{
	    
	    $('#cart_category_id').val(cat);
	    $('#cart_category_sub_id').val(id);	
        showModal(modelid);
	
		
	}

	function openCostCat(modelid,costSheetId)
	{
		$('#category').find('option').remove();
		var pro = viewDetails(costSheetId,'costSheetCategory', modelid, 1, null);
	}

	function openMultiDos(modelid,id, values = null)
	{
		$('#lineItemID').val(id);
		$('#lineItemID2').val(id);
		$('#po_number').val(values);
		var imgURL = '';
		var output = $(".preview-images-zone");
		$('.preview-images-zone').find('.preview-image').remove();
		var pro = viewDetails(id,'getDocumentLineItem', modelid, 2, output);
	}

	function openMultiDosCostsheet(modelid,id)
	{
		var imgURL = '';
		$('#lineItemID').val(id);
		var output = $(".preview-images-zone");
		$('.preview-images-zone').find('.preview-image').remove();
		var pro = viewDetails(id,'getDocumentLineItem', modelid, 2, output);
	}
	function openCostSubCat(modelid,id)
	{
        $('#subcategory').find('option').remove();
		var pro = viewDetails(id,'openCostSubCat', modelid, 4, null);
	}
	function editCostSubCat(modelid,id)
	{
                	
        $('#subcategory').find('option').remove();
		var pro = viewDetails(id,'openCostSubCat', modelid, 3, null);
	}

	function get_price(id)
	{
		var pro = viewDetails(id,'get_price')
		pro.success(function(obj){
		res = JSON.parse(obj);
		if(res)
		{
			
		}
		
			
		});
	}


	
	function viewContactInfo(modelid,id,url)
	{
		//alert();
		$('.sv-profile-section-section-info').remove();
		var pro = viewDetails(id,url, modelid, 4, null);
	}

	function viewExpirence(modelid,url,id)
	{
		
		//$('.experienceTo').remove();
		//$('.preClass').remove();
		var table = 'experinces';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{
				$('#experience_id').val(id);
				$('#edit_title').val(res.data.title);
			    $('#edit_company').val(res.data.company);
				$('#edit_location').val(res.data.location);
				$('#editexpPresent').val(res.data.present);
				$('#edit_exp_form_from').val(res.data.fromDate);

				if(res.data.present == 1) {
					$("#editexpPresent").prop("checked", true);
					$('#toDate').hide()
					$('#present-user').show()

				} else {
					$("#editexpPresent").prop("checked", false);
					$('#present-user').hide()
					$('#toDate').show();
					$('#edit_exp_form_to').val(res.data.toDate);
					
				}
				
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
			
		});
	}

	function viewLicense(modelid,url,id)
	{
		
		var table = 'licensesAndCertifications';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{
				$('#license_id').val(id);
				$('#editlicenseName').val(res.data.licenseName);
			    $('#editlicenseIssueOrgiz').val(res.data.IssuingOrganization);
				$('#editlicenseCredentialId').val(res.data.credentialID);
				$('#editlicenseCredentialURL').val(res.data.credentialURL);
				$('#editlicensePresent').val(res.data.expire);
				$('#edit_license_form_from').val(res.data.IssueMonthFromDate);

				if(res.data.expire == 1) {
					$("#editlicensePresent").prop("checked", true);
					$('#ToLicenseDate').hide()
					$('#expire-license').show()

				} else {
					$("#editlicensePresent").prop("checked", false);
					$('#expire-license').hide()
					$('#ToLicenseDate').show();
					$('#edit_license_form_to').val(res.data.IssueMonthToDate);
					
				}
				
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
			
		});
	}

	function viewProject(modelid,url,id)
	{
		
		var table = 'projects';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{
				$('#project_id').val(id);
				$('#edit_project_name').val(res.data.projectName);
			    $('#edit_project_form_from').val(res.data.projectStartDate);	
				$('#edit_ongoing').val(res.data.onGoing);
				$('#edit_project_url').val(res.data.patentURL);
				$('#edit_project_desc').val(res.data.description);

				if(res.data.onGoing == 1) {
					$("#editongoing").prop("checked", true);
					$('#projectToDate').hide()
					$('#project-ongoing').show()

				} else {
					$("#editongoing").prop("checked", false);
					$('#project-ongoing').hide()
					$('#projectToDate').show();
					$('#edit_project_form_to').val(res.data.projectEndDate);
					
				}
				
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
			
		});
	}


	function viewPublication(modelid,url,id)
	{
		var table = 'publication';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{	
				$('#publication_id').val(id);
				$('#editPubliTitle').val(res.data.title);
				$('#editPublications').val(res.data.publication_Or_publisher);
				$('#editPublicationDate').val(res.data.publication_date);
				$('#editPublicationURL').val(res.data.publicationURL);
				$('#editPublicationDesc').val(res.data.Description);			
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
			
		});
	}

	function viewPatent(modelid,url,id)
	{
		var table = 'patent';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{	
				$('#patentid').val(id);
				$('#editPatentTitle').val(res.data.title);
				$('#editPatentOffice').val(res.data.patentoffice);
				$('#editApplicationNo').val(res.data.applicationNo);
				$('#editFillingDate').val(res.data.filingDate);
				$('#editPatentURL').val(res.data.patentURL);	
				$('#editPatentDesc').val(res.data.description);		
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
			
		});
	}

	function viewCourse(modelid,url,id)
	{
		var table = 'courses';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{	
				$('#courseid').val(id);
				$('#editCoursesName').val(res.data.courseName);
				$('#editCoursesNumber').val(res.data.number);
				$('#editCourseAssociate option[value='+res.data.associatedWith+']').attr('selected', 'selected');
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
			
		});
	}

	function viewHonorsAward(modelid,url,id)
	{
		var table = 'honorsAwards';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{	
				$('#honorid').val(id);
				$('#editAwardsName').val(res.data.nameOfaward);
				$('#editawardsType option[value='+res.data.awardType+']').attr('selected', 'selected');				
				$('#editHonorsGrade').val(res.data.grade);
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
			
		});
	}

	
	function viewCourseScore(modelid,url,id)
	{
		var table = 'course_and_test_score';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{	
				$('#recentid').val(id);
				$('#editRecentCource option[value='+res.data.courseType+']').attr('selected', 'selected');
				$('#editRecentTestName').val(res.data.testNname);				
				$('#editRecentTestScore').val(res.data.testScore);
				$('#editRecentCompletionDate').val(res.data.completionDate);
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
			
		});
	}

	function viewOrgnization(modelid,url,id)
	{
		var table = 'orgnizations';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{
				$('#org_id').val(id);
				$('#editOrgnizationName').val(res.data.organizationName);
			    $('#editPositionHeld').val(res.data.positionHeld);
				$('#editOrgnizationDesc').val(res.data.description);
				$('#editMembOngoing').val(res.data.membershipOngoing);
				$('#edit_org_form_from').val(res.data.membStartDate);

				if(res.data.membershipOngoing == 1) {
					$("#editMembOngoing").prop("checked", true);
					$('#orgToDate').hide()
					$('#membership-ongoing').show()

				} else {
					$("#editMembOngoing").prop("checked", false);
					$('#membership-ongoing').hide()
					$('#orgToDate').show();
					$('#edit_org_form_to').val(res.data.membEndDate);
					
				}
				
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
			
		});
	}

	function viewExtraActivities(modelid,url,id)
	{
		var table = 'extraCurricularActivities';
		var pro = viewAllDetails(id,url,table)
		pro.success(function(obj){
			var res = JSON.parse(obj)
			if(res.err==0)
			{	
				$('#extra_curricular_id').val(id);
				$('#editActAwardsType option[value='+res.data.awardType+']').attr('selected', 'selected');
				$('#editActGrade').val(res.data.grade);				
				$('#editActYear').val(res.data.year);
				$('#editActHoursWeek').val(res.data.hoursWeek);
				$('#editActWeeksYear').val(res.data.weeksYear);
				$('#editActDescription').val(res.data.description);
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
			
		});
	}

	function viewLanguage(modelid,id,url){
	    var pro=viewDetails(id,url, modelid, 5, null);
	}

	function viewCurrentGrade(modelid,id,url){
	    var pro=viewDetails(id,url, modelid, 6, null);
	}

	function stateList(countryID,stateID,cityID)
	{
	  var country_id = $('#'+countryID).val();
	  if(country_id != '')
	  {
	   $.ajax({
	    url:base_url+"fetch_state",
	    method:"POST",
	    data:{country_id:country_id},
	    success:function(data){
	     $('#'+stateID).html(data);
	     $('#'+cityID).html('<option value="">Select City</option>');
	    }
	   });
	  }
	  else{
	   	$('#'+stateID).html('<option value="">Select State</option>');
	   	$('#'+cityID).html('<option value="">Select City</option>');
	  }
	}
	function cityList(stateID,cityID)
	{
	  var state_id = $('#'+stateID).val();
	  if(state_id != ''){
	   $.ajax({
	    url:base_url+"fetch_city",
	    method:"POST",
	    data:{state_id:state_id},
	    success:function(data)
	    {
	     $('#'+cityID).html(data);
	    }
	   });
	  }
	  else{
	   $('#'+cityID).html('<option value="">Select City</option>');
	  }
	}

	function stateListEdit(countryID,stateID,cityID,selected)
	{
	  var country_id = $('#'+countryID).val();
	  if(country_id != '')
	  {
	   $.ajax({
	    url:base_url+"fetch_state_edit",
	    method:"POST",
	    data:{country_id:country_id,slect_option:selected},
	    success:function(data){
	     $('#'+stateID).html(data);
	     $('#'+cityID).html('<option value="">Select City</option>');
	    }
	   });
	  }
	  else{
	   	$('#'+stateID).html('<option value="">Select State</option>');
	   	$('#'+cityID).html('<option value="">Select City</option>');
	  }
	}
	function cityListEdit(stateID,cityID,selected)
	{
	  if(stateID != ''){
	   $.ajax({
	    url:base_url+"fetch_city_edit",
	    method:"POST",
	    data:{state_id:stateID,slect_option:selected},
	    success:function(data)
	    {
	     $('#'+cityID).html(data);
	    }
	   });
	  }
	  else{
	   $('#'+cityID).html('<option value="">Select City</option>');
	  }
	}



//-----------To send Contact Details-------------

