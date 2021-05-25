@extends('layouts.dashboards')

@section('content')

<div class="ps-page--my-account">
    <div class="ps-my-account">
        <div class="container">
            <div class="ps-form--account-padding"></div>
            <form class="ps-form--account ps-tab-root" action="{{ url(config('adminlte.login_url', 'login')) }}" method="post" style="border: 1px solid;">
                {!! csrf_field() !!}

                <?php if(@$msg) { ?>
                    <div class="alert alert-danger">
                        {{ $msg }}
                    </div>
                <?php } ?>
                <div class="form-group {{ $errors->has('email') ? 'has-error' : '' }}">
                    <input required type="email" class="form-control" id="email" name="email" placeholder="Email" value="{{ old('email') }}">
                    <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                    @if ($errors->has('email'))
                        <span class="help-block">
                            <strong>{{ $errors->first('email') }}</strong>
                        </span>
                    @endif
                </div>
                <div class="form-group {{ $errors->has('password') ? 'has-error' : '' }} form-forgot">
                    <input required type="password" class="form-control" id="password" name="password" placeholder="Password">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    @if ($errors->has('password'))
                        <span class="help-block">
                            <strong>{{ $errors->first('password') }}</strong>
                        </span>
                    @endif
                </div>
                <div class="form-group">
                    <div class="ps-checkbox">
                        <input class="form-control" type="checkbox" id="remember-me" name="remember-me">
                        <label for="remember-me">Rememeber me</label>
                    </div>
                </div>
                <div class="form-group submtit">
                    <button type="submit" class="ps-btn ps-btn--fullwidth">Login</button>
                </div>
            </form>
            <div style="padding-bottom: 130px;"></div>
        </div>
    </div>
</div>
@stop
