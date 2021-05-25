@extends('layouts.dashboards', ['menu' => 'discounts'])

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
                    <form action="{{ route('discounts.store') }}" method="POST" enctype="multipart/form-data">
                        @csrf

                        <div class="box">
                            <div class="box-body">
                                <div class="form-group {{ $errors->has('title') ? 'has-error' : '' }}">
                                    <label>Title</label>
                                    <input required="" type="text" name="title" class="form-control title" placeholder="Title" />

                                    @if ($errors->has('title'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('title') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('description') ? 'has-error' : '' }}">
                                    <label>Description</label>
                                    <textarea required class="form-control description" name="description" rows="8"></textarea>

                                    @if ($errors->has('description'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('description') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('category_id') ? 'has-error' : '' }}">
                                    <label>Category</label>
                                    <!-- <select class="form-control js-example-basic-multiple" multiple="multiple" required name="category_id[]"> -->
                                    <select class="form-control" required name="category_id">
                                        @foreach($categories as $cate)
                                            <option value="{{ $cate->id }}">{{ $cate->category_name }}</option>
                                        @endforeach
                                    </select>

                                    @if ($errors->has('category_id'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('category_id') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('discount_photo') ? 'has-error' : '' }}">
                                    <label>Photo</label>
                                    <div class="controls">
                                        <span>
                                            <input type="file" name="discount_photo" id="file" onchange="loadPreview(this, 'preview_img');" class="inputfile discount_photo" required>
                                            <label for="file" @click="onClick" inputId="1" style="" id='preview_img'><i class="fa fa-plus-circle"></i></label>
                                        </span>
                                    </div>

                                    @if ($errors->has('discount_photo'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('discount_photo') }}</strong>
                                        </span>
                                    @endif
                                </div>
                                
                                <div class="form-group {{ $errors->has('coupon') ? 'has-error' : '' }}">
                                    <label>Coupon<span style="color: red;"> 32 characters</span></label>
                                    <input type='text' class="form-control" name="coupon" maxlength="32">

                                    @if ($errors->has('coupon'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('coupon') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <br>
                                <input type="hidden" name="vendor_id" value="{{ $id }}" class="form-control" />
                            </div>

                            <div class="box-footer">
                                <button style="display: none;" type="submit" class="btn btn-success pull-right submit_discount">Save Discount</button>
                            </div>
                        </div>
                    </form>

                    <div class="box-footer">
                        <button type="submit" class="btn btn-success pull-right submit_discount_h">Save Discount</button>
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
        background-color: #E9ECEF;
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

        // $('#file').bind('change', function() {
        //     //this.files[0].size gets the size of your file.
        //     var file_size = this.files[0].size;
        //     if (file_size > 400000) {
        //         $.toast({
        //             heading: 'Error',
        //             text: "Please choose the photo size as than 400 KB smaller.",
        //             position: String("top-right"),
        //             icon: 'error',
        //             stack: false,
        //             loaderBg: '#f96868'
        //         });
        //     }
        // });
    </script>
@endsection