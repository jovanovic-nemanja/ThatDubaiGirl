@extends('layouts.dashboardsecond')

@section('content')

<!-- Map and From Area --> 
<div class="card">
    <div class="card-body">
        <div class="row">
            <div class="col-lg-3">
                <div class="border-bottom pb-4">
                    @if($user->company_logo)
                        <img src="{{ asset('uploads/') }}/{{ $user->company_logo }}" alt="profile" class="img-lg rounded-circle mb-3">
                    @endif
                    
                    <p>{{ $user->company_name }} </p>
                    <p>{{ $user->email }} </p>
                    <p>{{ $user->phone_number }} </p>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="d-flex justify-content-between">
                    <div>
                        <h3>{{ $user->name }}</h3>
                        <div class="d-flex align-items-center">
                            <h5 class="mb-0 mr-2 text-muted">Rating</h5>
                            <div class="br-wrapper br-theme-css-stars">
                                <?php 
                                    if (round($mark) == 0) { ?>
                                        <?php echo number_format($mark, 1); ?>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span> 
                                <?php }elseif (round($mark) == 1) { ?>
                                        <?php echo number_format($mark, 1); ?>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span> 
                                <?php }elseif (round($mark) == 2) { ?>
                                        <?php echo number_format($mark, 1); ?>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span> 
                                <?php }elseif (round($mark) == 3) { ?>
                                        <?php echo number_format($mark, 1); ?>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span> 
                                <?php }elseif (round($mark) == 4) { ?>
                                        <?php echo number_format($mark, 1); ?>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star"></span> 
                                <?php }elseif (round($mark) == 5) { ?>
                                        <?php echo number_format($mark, 1); ?>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span> 
                                <?php } ?>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mt-4 py-2 border-top border-bottom">
                    <ul class="nav profile-navbar">
                        <li class="nav-item">
                            <a class="nav-link">
                                <i class="icon-note"></i> Reviews
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="profile-feed">
                    @if($reviews)
                        @foreach($reviews as $review)
                            <div class="d-flex align-items-start profile-feed-item">
                                <!-- <img src="../../../assets/images/faces/face13.jpg" alt="profile" class="img-sm rounded-circle"> -->
                                <div class="ml-4">
                                    <h6> {{ $review->name }} 
                                        <small class="ml-4 text-muted"><i class="icon-clock mr-1"></i>{{ $review->sign_date }}</small>
                                    </h6>
                                    <p> {{ $review->description }} </p>
                                    <p class="small text-muted mt-2 mb-0">
                                        <span>
                                            <i class="icon-star mr-1"></i>{{ $review->mark }} 
                                        </span>
                                        <span class="ml-2">
                                            <i class="icon-paypal mr-1"></i>{{ number_format(round($review->total_price, 3, PHP_ROUND_HALF_UP), 2) }} {{ $localization_setting->currency }} 
                                        </span>
                                    </p>
                                </div>
                            </div>
                        @endforeach
                    @endif
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Map and From Area --> 
@stop