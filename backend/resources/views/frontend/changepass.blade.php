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
            <h3>{{ __('Change Password') }}</h3>
            <br>
		    <form method="post" action="{{ route('account.updatePassword', $user->id) }}">
		      @csrf

			  <input type="hidden" name="_method" value="put">
              
              <div class="form-group row">
		        <label for="old_password" class="col-sm-3 col-form-label">{{ __('Current Password') }}</label>
		        <div class="col-sm-9">
		          <input type="password" class="form-control" id="old_password" name="old_password" placeholder="{{ __('Current Password') }}" />
		        </div>
              </div>
              
		      <div class="form-group row">
		        <label for="password" class="col-sm-3 col-form-label">{{ __('New Password') }}</label>
		        <div class="col-sm-9">
		          <input type="password" class="form-control" id="password" name="password" required placeholder="{{ __('New Password') }}" />
		        </div>
		      </div>

		      <div class="form-group row">
		        <label for="password_confirmation" class="col-sm-3 col-form-label">{{ __('Repeat New Password') }}</label>
		        <div class="col-sm-9">
		          <input type="password" class="form-control" id="password_confirmation" required name="password_confirmation" placeholder="{{ __('Repeat New Password') }}" />
		        </div>
		      </div>

              <!-- Save Button -->
              <div class="form-group row" style="display: block; text-align: right; padding-right: 3%;">
                  <button type="submit" class="ps-btn">{{ __('Update Password') }}</button>
              </div>
              <!-- /Save Button -->

			</form>
        </div>
    </div>
@endsection
