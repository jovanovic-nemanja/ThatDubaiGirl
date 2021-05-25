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
            </div>
            <br>
            <div class="row">
                <div class="col-12">
                    <div class="table-responsive">
                        <table id="order-listing" class="table">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Discount</th>
                                    <th>Mark</th>
                                    <th>Comment</th>
                                    <th>User Name</th>
                                    <th>Date</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                @foreach($reviews as $review)
                                    <tr>
                                        <?php 
                                            $discountInformation = App\Reviews::getDiscountInformationByID($review->discount_id);
                                            $userInformation = App\Reviews::getUserInformationByID($review->putter);
                                        ?>
                                        <td>{{ $review->id }}</td>
                                        <td>{{ $discountInformation->title }}</td>
                                        <td>
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
                                            <select class="test_star" id="example-fontawesome_1" name="rating">
                                                <option value="1" <?= $selected1; ?>>1</option>
                                                <option value="2" <?= $selected2; ?>>2</option>
                                                <option value="3" <?= $selected3; ?>>3</option>
                                                <option value="4" <?= $selected4; ?>>4</option>
                                                <option value="5" <?= $selected5; ?>>5</option>
                                            </select>
                                        </td>
                                        <td><?= nl2br($review->comments) ?></td>
                                        <td>{{ $userInformation->username }}</td>
                                        <td>{{ $review->put_date }}</td>
                                        <td>
                                            <a href="{{ route('reviews.show', $review->id) }}" class="btn btn-primary btn-sm btn-flat" title="Edit">
                                                <i class="fa fa-edit"></i>Edit
                                            </a>
                                            
                                            <button class="btn btn-danger btn-sm btn-flat" onclick="event.stopPropagation(); event.preventDefault(); showSwal('warning-message-and-cancel', 'delete-form-{{$review->id}}')" title="Delete"><i class="fa fa-trash"></i></button>

                                            <form id="delete-form-{{$review->id}}" action="{{ route('reviews.destroy', $review->id) }}" method="POST" style="display: none;">
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