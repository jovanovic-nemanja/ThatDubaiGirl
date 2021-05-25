@extends('layouts.dashboards', ['menu' => 'vendors'])

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
                    <form action="{{ route('vendor.update', $vendor->id) }}" method="POST" enctype="multipart/form-data">
                        @csrf

                        <input type="hidden" name="_method" value="put">

                        <div class="box">
                            <div class="box-body">
                                <div class="form-group {{ $errors->has('vendorname') ? 'has-error' : '' }}">
                                    <label>Name</label>
                                    <input required value="{{ $vendor->vendorname }}" type="text" name="vendorname" class="form-control vendorname" placeholder="Name" />

                                    @if ($errors->has('vendorname'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('vendorname') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <input type="text" value="<?= $size ?>" id="hidden_img" style="display: none;" />

                                <div class="form-group {{ $errors->has('photo') ? 'has-error' : '' }}">
                                    <label>Profile photo</label>
                                    <div class="controls">
                                        <span>
                                            <input type="file" name="photo" id="file" onchange="loadPreview(this, 'preview_img');" class="inputfile photo">
                                            <?php 
                                                if(@$vendor->photo) {
                                                    $path = asset('uploads/') . "/" . $vendor->photo;
                                                }else{
                                                    $path = "";
                                                }
                                            ?>

                                            <label for="file" @click="onClick" inputId="1" style="background-image: url(<?= $path ?>);" id='preview_img'>
                                                <i class="fa fa-plus-circle"></i>
                                            </label>
                                        </span>
                                    </div>

                                    @if ($errors->has('photo'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('photo') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('email') ? 'has-error' : '' }}">
                                    <label>Email</label>
                                    <input required="" type="email" name="email" class="form-control email" placeholder="Email" value="{{ $vendor->email }}" />

                                    @if ($errors->has('email'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('email') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <!-- <div class="form-group {{ $errors->has('category_id') ? 'has-error' : '' }}">
                                    <label>Category</label>
                                    <select class="form-control js-example-basic-multiple" multiple="multiple" required name="category_id[]">
                                        @foreach($categories as $cate)
                                            <?php 
                                                $diff = explode(",", $vendor->category_id);
                                                if (@$diff) {
                                                    $arr = [];
                                                    for ($i=0; $i < count($diff); $i++) { 
                                                        array_push($arr, $diff[$i]);
                                                    }
                                                } ?>
                                                    @if(in_array($cate->id, $arr))
                                                        <option value="{{ $cate->id }}" selected>{{ $cate->category_name }}</option>
                                                    @else
                                                        <option value="{{ $cate->id }}">{{ $cate->category_name }}</option>
                                                    @endif
                                            <?php  ?>
                                        @endforeach
                                    </select>

                                    @if ($errors->has('category_id'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('category_id') }}</strong>
                                        </span>
                                    @endif
                                </div> -->

                                <div class="form-group {{ $errors->has('phone') ? 'has-error' : '' }}">
                                    <label>Phone</label>
                                    <input required="" type="text" name="phone" class="form-control phone" placeholder="Phone" value="{{ $vendor->phone }}" />

                                    @if ($errors->has('phone'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('phone') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('location') ? 'has-error' : '' }}">
                                    <label>Location</label>
                                    <input required="" type="text" name="location" class="form-control location" placeholder="Location" value="{{ $vendor->location }}" />

                                    @if ($errors->has('location'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('location') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group">
                                    <label>Instagram Link</label>
                                    <input type="text" name="instagram_id" class="form-control" placeholder="Instagram" value="{{ $vendor->instagram_id }}" />
                                </div>

                                <div class="form-group">
                                    <label>Facebook Link</label>
                                    <input type="text" name="facebook_id" class="form-control" placeholder="Facebook" value="{{ $vendor->facebook_id }}" />
                                </div>

                                <div class="form-group">
                                    <label>Website Link</label>
                                    <input type="text" name="website_link" class="form-control" placeholder="Website Link" value="{{ $vendor->website_link }}" />
                                </div>

                                <div class="form-group">
                                    <label>Remarks</label>
                                    <textarea name="remarks_vendor" class="form-control" rows="8">{{ $vendor->remarks_vendor }}</textarea>
                                </div>
                            </div>

                            <div class="box-footer">
                                <button type="submit" class="btn btn-success pull-right submit_vendor" style="display: none;">Save Vendor</button>
                            </div>
                        </div>
                    </form>

                    <div class="box-footer">
                        <button type="submit" class="btn btn-success pull-right submit_vendor_edit">Save Vendor</button>
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