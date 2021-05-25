<div class="ps-product__thumbnail">
  <a href="{{ route('product.show', $product->slug) }}">
    <img src="{{ asset('uploads/') }}/{{ $product->thumbnailUrl() }}" alt="">
  </a>
</div>
<div class="ps-product__container">
    <div class="ps-product__content">
      <a class="ps-product__title" href="{{ route('product.show', $product->slug) }}">{{ str_limit($product->name, 20, '...') }}</a>
      <p class="text-muted"><?= nl2br($product->description); ?></p>
      <p class="ps-product__vendor">Seller Name:<a href="{{ url('/purchaseorders/userreview', $product->user_id) }}">{{ $product->getUsername($product->user_id) }}</a></p>
    </div>
    <div class="ps-product__shopping">
      <p class="ps-product__price">{{ number_format(round($product->price_from, 3, PHP_ROUND_HALF_UP), 2) }} ~ {{ number_format(round($product->price_to, 3, PHP_ROUND_HALF_UP), 2) }} {{ $localization_setting->currency }}</p>
      @auth
        @if(auth()->user()->hasRole('buyer'))
          <a class="ps-btn" href="{{ route('request.sendrequest', $product->id) }}">Request</a>
        @endif
      @endauth
    </div>
</div>