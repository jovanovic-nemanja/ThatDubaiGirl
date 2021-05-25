@extends('layouts.dashboards', ['menu' => 'reviews'])

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
                    <form action="{{ route('reviews.update', $review->id) }}" method="POST" enctype="multipart/form-data">
                        @csrf
                        <input type="hidden" name="_method" value="put">

                        <div class="box">
                            <div class="box-body">
                                <div class="form-group">
                                    <label>Discount</label>
                                    <input required value="{{ $discount->title }}" type="text" name="title" class="form-control" placeholder="Title" />
                                </div>

                                <div class="form-group {{ $errors->has('mark') ? 'has-error' : '' }}">
                                    <label>Mark</label>
                                    <?php 
                                        switch ($review->mark) {
                                            case '1':
                                                $selected1 = "selected";
                                                $selected2 = $selected3 = $selected4 = $selected5 = "";
                                                break;
                                            case '2':
                                                $selected2 = "selected";
                                                $selected1 = $selected3 = $selected4 = $selected5 = "";
                                                break;
                                            case '3':
                                                $selected3 = "selected";
                                                $selected2 = $selected1 = $selected4 = $selected5 = "";
                                                break;
                                            case '4':
                                                $selected4 = "selected";
                                                $selected2 = $selected3 = $selected1 = $selected5 = "";
                                                break;
                                            case '5':
                                                $selected5 = "selected";
                                                $selected2 = $selected3 = $selected4 = $selected1 = "";
                                                break;
                                            
                                            default:
                                                $selected1 = "selected";
                                                $selected2 = $selected3 = $selected4 = $selected5 = "";
                                                break;
                                        }
                                    ?>
                                    <select id="example-fontawesome" name="mark" required>
                                        <option value="1" <?= $selected1; ?>>1</option>
                                        <option value="2" <?= $selected2; ?>>2</option>
                                        <option value="3" <?= $selected3; ?>>3</option>
                                        <option value="4" <?= $selected4; ?>>4</option>
                                        <option value="5" <?= $selected5; ?>>5</option>
                                    </select>

                                    @if ($errors->has('mark'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('mark') }}</strong>
                                        </span>
                                    @endif
                                </div>

                                <div class="form-group {{ $errors->has('comments') ? 'has-error' : '' }}">
                                    <label>Comment</label>
                                    <textarea name="comments" class="form-control" required rows="8">{{ $review->comments }}</textarea>

                                    @if ($errors->has('comments'))
                                        <span class="help-block">
                                            <strong>{{ $errors->first('comments') }}</strong>
                                        </span>
                                    @endif
                                </div>
                            </div>

                            <div class="box-footer">
                                <button type="submit" class="btn btn-success pull-right">Update</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
@stop