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
                <a href="{{ route('vendor.create') }}" class="btn btn-success">Add Vendor</a>    
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
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Location</th>
                                    <th>Photo</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                @foreach($vendors as $vendor)
                                <tr>
                                    <td>{{ $vendor->id }}</td>
                                    <td>{{ $vendor->vendorname }}</td>
                                    <td>{{ $vendor->email }}</td>
                                    <!-- <td>{{ App\Vendors::getCategoryNameByID($vendor->category_id) }}</td> -->
                                    <td>{{ $vendor->phone }}</td>
                                    <td>{{ $vendor->location }}</td>
                                    <!-- <td><a href="{{ $vendor->instagram_id }}">{{ $vendor->instagram_id }}</a></td>
                                    <td><a href="{{ $vendor->facebook_id }}">{{ $vendor->facebook_id }}</a></td> -->
                                    <?php 
                                        if(@$vendor->photo) {
                                            $path = asset('uploads/') . "/" . $vendor->photo;
                                        }else{
                                            $path = "";
                                        }
                                    ?>
                                    <td><img src="<?= $path ?>" style="border-radius: unset; height: unset;" /></td>
                                    <!-- <td>{{ $vendor->sign_date }}</td> -->
                                    <td>
                                        <a href="{{ route('discounts.creatediscounts', $vendor->id) }}" class="btn btn-success btn-sm btn-flat">
                                            Add Discount
                                        </a>
                                        <a href="{{ route('vendor.show', $vendor->id) }}" class="btn btn-primary btn-sm btn-flat">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                        <button class="btn btn-danger btn-sm btn-flat" onclick="event.stopPropagation(); event.preventDefault(); showSwal('warning-message-and-cancel', 'delete-form-{{$vendor->id}}')" title="Delete"><i class="fa fa-trash"></i></button>

                                        <form id="delete-form-{{$vendor->id}}" action="{{ route('vendor.destroy', $vendor->id) }}" method="POST" style="display: none;">
                                              <input type="hidden" name="_method" value="delete">
                                              @csrf
                                        </form>
                                    </td>
                                </tr>
                                @endforeach
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
@stop