@extends('layouts.dashboards', ['menu' => 'general_settings'])

@section('content')
<div class="card">
  	<div class="card-body" style="padding: 5%;">
        <div class="row">
          	<div class="col-12">
				<form method='post' action="{{ route('admin.generalsetting.update', $general_setting->id) }}">
					@csrf

					<input type="hidden" name="_method" value="put">

					<div class="box">
					  <!-- /.box-header -->
					  <div class="box-body">
						<div class="form-group">
							<label>Site Name</label>
							<input type="text" name="site_name" class="form-control" placeholder="Site Name" value="{{ $general_setting->site_name }}" />
						</div>

                        <div class="form-group">
                            <label>App Name</label>
                            <input type="text" name="app_name" class="form-control" placeholder="App Name" value="{{ $general_setting->app_name }}" />
                        </div>

						<div class="form-group">
							<label>Site Title</label>
							<input type="text" name="site_title" class="form-control" placeholder="Site Title" value="{{ $general_setting->site_title }}" />
						</div>

						<div class="form-group">
							<label>Site Subtitle</label>
							<input type="text" name="site_subtitle" class="form-control" placeholder="Site Subtitle" value="{{ $general_setting->site_subtitle }}" />
						</div>

						<div class="form-group">
							<label>Site Description</label>
							<textarea name="site_desc" placeholder="Site Description" class="form-control" cols="30" rows="5">{{ $general_setting->site_desc }}</textarea>
						</div>

						<div class="form-group">
							<label>Site Footer</label>
							<input type="text" name="site_footer" class="form-control" placeholder="Site Footer" value="{{ $general_setting->site_footer }}" />
						</div>
					  </div>
					  <!-- /.box-body -->
					  <div class="box-footer">
					  	<button type="submit" class="btn btn-success pull-right">Update Setting</button>
					  </div>
					  <!-- box-footer -->
					</div>
					<!-- /.box -->
				</form>
			</div>
		</div>
	</div>
</div>
@endsection
