@extends('layouts.dashboards', ['menu' => 'video'])

@section('content')
<div class="card">
    <div class="card-body" style="padding: 5%;">
        <div class="row">
        	<?php if (@$video) { ?>
        		
        	<?php }else{ ?>
        		<a href="{{ route('video.create') }}" class="btn btn-success">Add Video</a> 
    		<?php } ?>
        </div>
        <br>
        <div class="row">
            <div class="col-12">
                <div class="table-responsive">
                    <table id="order-listing" class="table">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Name</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            @if($video)
                            <tr>
                                <td>{{ $video->id }}</td>
                                <td>{{ $video->link }}</td>
                                <td>
                                    <a href="{{ route('video.show', $video->id) }}" class="btn btn-primary btn-sm btn-flat">
                                        <i class="fa fa-edit"></i>
                                    </a>

                                    <button class="btn btn-danger btn-sm btn-flat" onclick="event.stopPropagation(); event.preventDefault(); showSwal('warning-message-and-cancel', 'delete-form-{{$video->id}}')" title="Delete"><i class="fa fa-trash"></i></button>

                                    <form id="delete-form-{{$video->id}}" action="{{ route('video.destroy', $video->id) }}" method="POST" style="display: none;">
                                          <input type="hidden" name="_method" value="delete">
                                          @csrf
                                    </form>
                                </td>
                            </tr>
                            @endif
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
