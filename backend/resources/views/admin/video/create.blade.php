@extends('layouts.dashboards', ['menu' => 'video'])

@section('content')
	@if(session('flash'))
		<div class="alert alert-primary">
			{{ session('flash') }}
		</div>
	@endif

	<div class="card">
        <div class="card-body" style="padding: 5%;">
            <div class="row">
                <div class="col-12">
                    <form action="{{ route('video.store') }}" method="POST" enctype="multipart/form-data">
                        @csrf

                        <div class="box">
                            <div class="box-body">
                                <div class="form-group {{ $errors->has('link') ? 'has-error' : '' }}">
                                    <label>Choose Video</label>
                                    <div class="controls">
                                        <input type="file" name="link" required class="form-control">
                                    </div>

                                    @if ($errors->has('link'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('link') }}</strong>
                                        </span>
                                    @endif
                                </div>
                            </div>

                            <div class="box-footer">
                                <button type="submit" class="btn btn-success pull-right">Save</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
@stop