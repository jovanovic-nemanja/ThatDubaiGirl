@extends('layouts.setting')

@section('section')

	@if ($errors->any())
	  <div class="alert alert-danger">
	      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
	        <span aria-hidden="true">&times;</span>
	      </button>
	      <ul>
	          @foreach ($errors->all() as $error)
	              <li>{{ $error }}</li>
	          @endforeach
	      </ul>
	  </div>
	@endif

	<div class="card rounded-0">
		<div class="card-body" style="padding: 3%;">
            <h3>{{ __('Account') }}</h3>
            <br>
		    <form method="post" enctype="multipart/form-data" action="{{ route('account.update', $user->id) }}">
		      	@csrf

			  	<input type="hidden" name="_method" value="put">

		      	<div class="form-group row">
		        	<label for="name" class="col-sm-3 col-form-label">{{ __('Name') }}</label>
			        <div class="col-sm-9">
			          	<input type="text" class="form-control" id="name" name="name" placeholder="Name" value="{{ $user->name }}" required />
			        </div>
		      	</div>

		      	<div class="form-group row">
			        <label for="email" class="col-sm-3 col-form-label">{{ __('Email') }}</label>
			        <div class="col-sm-9">
			          	<input type="email" class="form-control" id="email" name="email" placeholder="Email" value="{{ $user->email }}" required />
			        </div>
			  	</div>

		      	<div class="form-group row">
			        <label for="name" class="col-sm-3 col-form-label">{{ __('Company Name') }}</label>
			        <div class="col-sm-9">
			          	<input type="text" class="form-control" id="company_name" name="company_name" placeholder="Company Name" value="{{ $user->company_name }}" required />
			        </div>
		      	</div>

		      	<div class="form-group row">
			        <label for="email" class="col-sm-3 col-form-label">{{ __('Company Logo') }}</label>
			        <div class="col-sm-9">
			          	<span>
	                    	<input type="file" name="company_logo" id="file" onchange="loadPreview(this, 'preview_img');" class="inputfile">
		                    <?php 
		                      	if(@$user->company_logo) {
		                        	$path = asset('uploads/') . "/" . $user->company_logo;
		                      	}else{
		                        	$path = "";
		                      	}
		                    ?>
		                    <label for="file" @click="onClick" inputId="1" style="background-image: url(<?= $path ?>);" id='preview_img'>
		                    	<i class="fa fa-plus-circle"></i>
		                    </label>
	                  	</span>
			        </div>
			  	</div>
			  
			  	<div class="form-group row">
			        <label for="phone_number" class="col-sm-3 col-form-label">{{ __('Phone Number') }}</label>
			        <div class="col-sm-9">
			          	<input type="text" class="form-control" id="phone_number" name="phone_number" placeholder="Phone Number" required value="{{ $user->phone_number }}" />
			        </div>
		      	</div>

              	<!-- Save Button -->
              	<div class="form-group row" style="display: block; text-align: right; padding-right: 3%;">
                  	<button type="submit" class="ps-btn">{{ __('Update Account') }}</button>
              	</div>
              	<!-- /Save Button -->
			</form>

			<div class="row">
				<label for="rating" class="col-sm-3 col-form-label">{{ __('Rating') }}</label>
				@if(!auth()->user()->hasRole('admin'))
					<div class="col-sm-9">
						<?php 
							if (round($marks) == 0) { ?>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span> ( <?php echo number_format($marks, 1); ?> )
						<?php }elseif (round($marks) == 1) { ?>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span> ( <?php echo number_format($marks, 1); ?> )
						<?php }elseif (round($marks) == 2) { ?>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span> ( <?php echo number_format($marks, 1); ?> )
						<?php }elseif (round($marks) == 3) { ?>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star"></span>
								<span class="fa fa-star"></span> ( <?php echo number_format($marks, 1); ?> )
						<?php }elseif (round($marks) == 4) { ?>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star"></span> ( <?php echo number_format($marks, 1); ?> )
						<?php }elseif (round($marks) == 5) { ?>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span>
								<span class="fa fa-star checked"></span> ( <?php echo number_format($marks, 1); ?> )
						<?php } ?>
					</div>
				@endif
			</div>
        </div>
    </div>
@endsection

<style>
  .inputfile {
    width: 0.1px;
    height: 0.1px;
    opacity: 0;
    overflow: hidden;
    position: absolute;
    z-index: -1;
  }

  .inputfile + label {
    font-size: 1.25em;
    font-weight: 700;
    color: white;
    background-color: #E9ECEF;
    padding: 50px;
    display: inline-block;
    cursor: pointer;
    background-size: cover;
  }

  .inputfile:focus + label,
  .inputfile + label:hover {
    background-color: #38C172ed;
  }

  .hidden {
    display: none !important;
  }
</style>

@section('script')
<script>
  function loadPreview(input, id) {
    id = "#" + id;
    if (input.files && input.files[0]) {
      var reader = new FileReader();

      reader.onload = function (e) {
        var path = "background-image: " + "url('" + e.target.result + "')";
        $(id).attr('style', path);
      };

      reader.readAsDataURL(input.files[0]);
    }
  }
</script>
@endsection