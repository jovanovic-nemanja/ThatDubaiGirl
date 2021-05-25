@extends('layouts.app')

@section('content')

<div class="ps-page--my-account">
    <div class="ps-my-account">
        <div class="container">
            <div class="ps-form--account-padding"></div>
            <form class="ps-form--account ps-tab-root" enctype="multipart/form-data" action="{{ url(config('adminlte.register_url', 'register')) }}" method="POST" style="border: 1px solid;">
                <ul class="ps-tab-list">
                    <li class="active"><a href="#sign-in">Join as Buyer</a></li>
                </ul>
                <div class="ps-tabs">
                    <div class="ps-tab active" id="sign-in">
                        <div class="ps-form__content">
                            <h5>Join as Buyer</h5>
                            {!! csrf_field() !!}

                            <div class="form-group">
                                <input type="email" class="form-control" id="email" name="email" placeholder="Email*" value="{{ $useremail }}" required readonly>
                                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                                @if ($errors->has('email'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('email') }}</strong>
                                    </span>
                                @endif
                            </div>
                            <div class="form-group {{ $errors->has('name') ? 'has-error' : '' }}">
                                <input type="text" class="form-control" id="name" name="name" placeholder="Name*" value="{{ old('name') }}" required>
                                <span class="glyphicon glyphicon-user form-control-feedback"></span>
                                @if ($errors->has('name'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('name') }}</strong>
                                    </span>
                                @endif
                            </div>
                            <input type="hidden" name="role" id="role" value="1" />

                            <div class="form-group {{ $errors->has('company_name') ? 'has-error' : '' }}">
                                <input required type="text" class="form-control" id="company_name" name="company_name" placeholder="Company Name*" value="{{ old('company_name') }}">
                                <span class="glyphicon glyphicon-user form-control-feedback"></span>
                                @if ($errors->has('company_name'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('company_name') }}</strong>

                                    </span>
                                @endif
                            </div>

                            <div class="form-group {{ $errors->has('phone_number') ? 'has-error' : '' }}">
                                <input required type="text" class="form-control" id="phone_number" name="phone_number" placeholder="Phone number*" value="{{ old('phone_number') }}">
                                <span class="glyphicon glyphicon-user form-control-feedback"></span>
                                @if ($errors->has('phone_number'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('phone_number') }}</strong>

                                    </span>
                                @endif
                            </div>

                            <div class="form-group u-form-email u-form-group u-form-group-2">
                                <label for="name" class="col-sm-2 col-form-label">{{ __('Company Logo') }}</label>
                                <div class="col-md-10">
                                    <span>
                                        <input type="file" name="company_logo" id="file" onchange="loadPreview(this, 'preview_img');" class="inputfile">
                                        <label for="file" @click="onClick" inputId="1" style="" id='preview_img'><i class="fa fa-plus-circle"></i></label>
                                    </span>
                                </div>
                            </div>

                            <div class="form-group submtit">
                                <button class="ps-btn ps-btn--fullwidth">Join now</button>
                            </div>
                        </div>
                        <div class="ps-form__footer">
                            <p class="u-text u-text-2">Already have an account?&nbsp;
                                <a href="{{ url(config('adminlte.login_url', 'login')) }}" class="ml-2 u-btn u-button-style u-none u-text-palette-1-base u-btn-2">Login</a>
                            </p>
                        </div>
                    </div>
                </div>
            </form>
            <div style="padding-bottom: 130px;"></div>
        </div>
    </div>
</div>
@stop

@section('adminlte_js')
    @yield('js')
@stop

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