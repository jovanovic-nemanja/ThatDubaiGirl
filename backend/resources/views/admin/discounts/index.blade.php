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
            </div>
            <br>
            <div class="row">
                <div class="col-12">
                    <div class="table-responsive">
                        <table id="order-listing" class="table">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Title</th>
                                    <th>Status</th>
                                    <th>Description</th>
                                    <th>Photo</th>
                                    <th>Vendor</th>
                                    <th>Category</th>
                                    <th>Coupon</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody id="lightgallery" class="lightGallery">
                                @foreach($discounts as $discount)
                                <?php 
                                    if(@$discount->discount_photo) {
                                        $path = asset('uploads/') . "/" . $discount->discount_photo;
                                    }else{
                                        $path = "";
                                    }
                                ?>
                                <tr href="<?= $path ?>">
                                    <?php 
                                        $vendor_infor = App\Discounts::getVendorInformationByID($discount->vendor_id);
                                        $category = App\Vendors::getCategoryNameByID($vendor_infor->category_id);
                                    ?>
                                    <td>{{ $discount->id }}</td>
                                    <td>{{ $discount->title }}</td>
                                    <td>{{ App\Discounts::getStatusBystatusID($discount->status) }}</td>
                                    <td><?= nl2br($discount->description) ?></td>
                                    
                                    <td>
                                        <a href="<?= $path ?>" class="image-tile">
                                            <img src="<?= $path ?>" style="border-radius: unset; height: unset;" class="lightGallery" />    
                                        </a>
                                    </td>
                                    <td><?= $vendor_infor->vendorname ?></td>
                                    <td>{{ App\Discounts::getCategoryNameByID($discount->category_id) }}</td>
                                    <td>{{ $discount->coupon }}</td>
                                    <td>
                                        <a href="{{ route('discounts.show', $discount->id) }}" class="btn btn-primary btn-sm btn-flat" title="Edit" onclick="event.stopPropagation();">
                                            <i class="fa fa-edit"></i>Edit
                                        </a>

                                        <button class="btn btn-danger btn-sm btn-flat" onclick="event.stopPropagation(); event.preventDefault(); showSwal('warning-message-and-cancel', 'delete-form-{{$discount->id}}')" title="Delete"><i class="fa fa-trash"></i>Delete</button>

                                        <form id="delete-form-{{$discount->id}}" action="{{ route('discounts.destroy', $discount->id) }}" method="POST" style="display: none;">
                                            <input type="hidden" name="_method" value="delete">
                                            @csrf
                                        </form>

                                        @if($discount->status == 1)
                                            <a href="" onclick="event.stopPropagation(); event.preventDefault();
                                                 document.getElementById('setfeature-form-{{$discount->id}}').submit();" class="btn btn-success btn-sm btn-flat" title="Set as featured" style="background-color: #6060ff;">
                                                <i class="fa fa-check"></i>Set as featured
                                            </a>

                                            <form id="setfeature-form-{{$discount->id}}" action="{{ route('discounts.setfeature') }}" method="POST" style="display: none;">
                                                <input type="hidden" name="_method" value="POST">
                                                @csrf

                                                <input type="hidden" name="discountID" value="{{ $discount->id }}">
                                            </form>
                                        @elseif($discount->status == 2)
                                            <a href="" onclick="event.stopPropagation(); event.preventDefault();
                                                 document.getElementById('resetfeatured-form-{{$discount->id}}').submit();" class="btn btn-success btn-sm btn-flat" title="Reset as general" style="background-color: #6060ff;">
                                                <i class="fa fa-times"></i>Reset as general
                                            </a>

                                            <form id="resetfeatured-form-{{$discount->id}}" action="{{ route('discounts.resetfeatured') }}" method="POST" style="display: none;">
                                                <input type="hidden" name="_method" value="POST">
                                                @csrf

                                                <input type="hidden" name="discountID" value="{{ $discount->id }}">
                                            </form>
                                        @endif
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