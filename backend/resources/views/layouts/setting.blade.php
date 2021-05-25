@extends('layouts.app')

@section('content')

<main class="ps-page--my-account">
  <section class="ps-section--account">
      <div class="container">
          <div class="row">
              <div class="col-lg-4">
                  <div class="ps-section__left">
                      <aside class="ps-widget--account-dashboard">
                          <div class="ps-widget__content">
                              <ul>
                                <li class="{{ isActiveRoute('account') }}">
                                  <a href="{{ route('account') }}">
                                    <i class="icon-user"></i> {{ __('Account') }}
                                  </a>
                                </li>
                                <li class="{{ isActiveRoute('changepass') }}">
                                  <a href="{{ route('changepass') }}">
                                    <i class="fa fa-lock"></i> {{ __('Change Password') }}
                                  </a>
                                </li>
                                <li class="{{ isActiveRoute('myreviews') }}">
                                  <a href="{{ route('myreviews') }}">
                                    <i class="fa fa-bars"></i> {{ __('Reviews') }}
                                  </a>
                                </li>
                              </ul>
                          </div>
                      </aside>
                  </div>
              </div>
              <div class="col-lg-8">
                @yield('section')
              </div>
          </div>
      </div>
  </section>
</main>
@endsection
