<div class="ps" style="border: 1px solid; padding: 5%; margin-bottom: 4%; border-radius: 3px;">
  <div class="ps-product__thumbnail">
    <a href="{{ route('product.show', $product->slug) }}">
      <img src="{{ asset('uploads/') }}/{{ $product->thumbnailUrl() }}" alt="" style="width: 100%; height: 145px; object-fit: cover;">
    </a>
  </div>
  <div class="ps-product__container">
    <a class="ps-product__vendor" href="{{ url('/purchaseorders/userreview', $product->user_id) }}" style="color: blue;">{{ $product->getUsername($product->user_id) }}</a>
    <hr>
    <div class="ps-product__content">
      <a class="ps-product__title" href="{{ route('product.show', $product->slug) }}">{{ str_limit($product->name, 20, '...') }}</a>
      <p class="ps-product__price">{{ number_format(round($product->price_from, 3, PHP_ROUND_HALF_UP), 2) }} ~ {{ number_format(round($product->price_to, 3, PHP_ROUND_HALF_UP), 2) }} {{ $localization_setting->currency }}</p>
      <p class="ps-product__price">{{ $product->MOQ }} piece (Min order)</p>
    </div>

    <input type="hidden" class="hidden_product_name" value="{{ $product->name }}">

    @auth
      @if(auth()->user()->hasRole('buyer'))
      <div style="text-align: center;">
        <a class="ps-btn rfq" href="{{ route('request.sendrequest', $product->id) }}">Request</a>
      </div>
      @endif
    @endauth
  </div>
</div>