@extends('layouts.dashboards', ['menu' => 'category'])

@section('content')

<div class="card">
  	<div class="card-body" style="padding: 5%;">
        <div class="row">
          	<div class="col-12">
          		<form action="{{ route('category.update', $category->id) }}" method="POST" enctype="multipart/form-data">
					@csrf

					<input type="hidden" name="_method" value="put">

					<div class="box">
						<div class="box-body">
							<div class="form-group">
								<label>Name</label>
								<input required type="text" name="category_name" class="form-control category_name" placeholder="Name" value="{{ $category->category_name }}" />
							</div>

                            <input type="text" value="<?= $size ?>" id="hidden_img" style="display: none;" />

							<div class="form-group {{ $errors->has('category_photo') ? 'has-error' : '' }}">
                                <label>Photo</label>
                                <div class="controls">
                                    <span>
                                        <input type="file" name="category_photo" id="file" onchange="loadPreview(this, 'preview_img');" class="inputfile category_photo">
                                        <?php 
                                            if(@$category->category_photo) {
                                                $path = asset('uploads/') . "/" . $category->category_photo;
                                            }else{
                                                $path = "";
                                            }
                                        ?>

                                        <label for="file" @click="onClick" inputId="1" style="background-image: url(<?= $path ?>);" id='preview_img'>
                                            <i class="fa fa-plus-circle"></i>
                                        </label>
                                    </span>
                                </div>

                                @if ($errors->has('category_photo'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('category_photo') }}</strong>
                                    </span>
                                @endif
                            </div>
						</div>

						<div class="box-footer">
							<button class="btn btn-success pull-right submit_category" style="display: none;">Update Category</button>
						</div>
					</div>
				</form>

                <div class="box-footer">
                    <button type="submit" class="btn btn-success pull-right submit_category_edit">Save Category</button>
                </div>
          	</div>
      	</div>
  	</div>
</div>
@stop

<style type="text/css">
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
        /*background-color: #E9ECEF;*/
        padding: 50px;
        display: inline-block;
        cursor: pointer;
        background-size: contain;
        width: 100%;
        background-repeat: no-repeat;
    }

    .inputfile:focus + label,
    .inputfile + label:hover {
        /*background-color: #38C172ed;*/
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