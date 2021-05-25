@extends('layouts.dashboards', ['menu' => 'users'])

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
                    <form action="{{ route('users.update', $user->id) }}" method="POST" enctype="multipart/form-data">
                        @csrf

                        <input type="hidden" name="_method" value="put">

                        <div class="box">
                            <div class="box-body">
                                <div class="form-group {{ $errors->has('username') ? 'has-error' : '' }}">
                                    <label>Name</label>
                                    <input required value="{{ $user->username }}" type="text" name="username" class="form-control" placeholder="Name" />

                                    @if ($errors->has('username'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('username') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('email') ? 'has-error' : '' }}">
                                    <label>Email</label>
                                    <input required="" type="email" name="email" class="form-control" placeholder="Email" value="{{ $user->email }}" />

                                    @if ($errors->has('email'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('email') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('photo') ? 'has-error' : '' }}">
                                    <label>Profile photo</label>
                                    <div class="controls">
                                        <span>
                                            <input type="file" name="photo" id="file" onchange="loadPreview(this, 'preview_img');" class="inputfile">
                                            <?php 
                                                if(@$user->photo) {
                                                    $path = asset('uploads/') . "/" . $user->photo;
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

                                <div class="form-group {{ $errors->has('birthday') ? 'has-error' : '' }}">
                                    <label>Birthday</label>
                                    <input type="date" name="birthday" class="form-control" placeholder="Birthday" value="{{ $user->birthday }}" />

                                    @if ($errors->has('birthday'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('birthday') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('address') ? 'has-error' : '' }}">
                                    <label>Address</label>
                                    <input type="text" name="address" class="form-control" placeholder="Address" value="{{ $user->address }}" />

                                    @if ($errors->has('address'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('address') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group">
                                    <label>Instagram</label>
                                    <input type="text" name="instagram_id" class="form-control" value="{{ $user->instagram_id }}" disabled readonly />
                                </div>

                                <div class="form-group">
                                    <label>Apple</label>
                                    <input type="text" name="apple_id" class="form-control" value="{{ $user->apple_id }}" disabled readonly />
                                </div>

                                <div class="form-group">
                                    <label>Block</label>
                                    <select class="form-control" name="block">
                                        <option value="-1">Normal</option>
                                        <option value="1">Block</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Remarks</label>
                                    <textarea name="remarks" class="form-control" rows="8">{{ $user->remarks }}</textarea>
                                </div>
                            </div>

                            <div class="box-footer">
                                <button type="submit" class="btn btn-success pull-right">Update User</button>
                            </div>
                        </div>
                    </form>
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