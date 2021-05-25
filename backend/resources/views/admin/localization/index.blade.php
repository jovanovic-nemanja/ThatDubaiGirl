@extends('layouts.dashboards', ['menu' => 'local_settings'])

@section('content')
<div class="card">
  	<div class="card-body" style="padding: 5%;">
        <div class="row">
          	<div class="col-12">
				<form method='post' action="{{ route('admin.localizationsetting.update', $localization_setting->id) }}" class="form-horizontal">
					@csrf

					<input type="hidden" name="_method" value="put">

					<div class="box">
					  <!-- /.box-header -->
					  <div class="box-body">
						<div class="form-group row">
							<label class="col-md-1 control-label">Language</label>
							<div class="col-md-5">
								<input type="text" name="language" class="form-control" placeholder="Language" value="{{ $localization_setting->language }}" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-md-1 control-label">Currency</label>
							<div class="col-md-5">
								<input type="text" name="currency" class="form-control" placeholder="Currency" value="{{ $localization_setting->currency }}" />
							</div>
						</div>
					  </div>
					  <!-- /.box-body -->
					  <div class="box-footer">
					  		<button class="btn btn-success pull-right">Update Setting</button>
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