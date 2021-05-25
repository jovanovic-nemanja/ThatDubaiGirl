<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

// Route::middleware('auth:api')->get('/user', function (Request $request) {
//     return $request->user();
// });

// getting all settings data
Route::get('/v1/generalsetting', 'API\SwiftApiController@getAllsettings');

// user email verify and register, login and logout by mobile iOS
Route::POST('/v1/emailverify', 'Admin\UsersController@emailverify');
Route::POST('/v1/validateCode', 'Admin\UsersController@validateCode');
Route::POST('/v1/register', 'Admin\UsersController@store');
Route::POST('/v1/loginUser', 'Admin\UsersController@loginUser');
Route::POST('/v1/loginwithApple', 'Admin\UsersController@loginUserwithApple');
Route::POST('/v1/loginwithGoogle', 'Admin\UsersController@loginUserwithGoogle');
Route::POST('/v1/logout', 'Admin\UsersController@logout');

// forgot password
Route::POST('/v1/forgotpassword', 'Admin\UsersController@forgotpassword');

// update user account information
Route::POST('/v1/updateAccount', 'Admin\UsersController@updateAccount');

// update user account information by apple id
Route::POST('/v1/updateAppleAccount', 'Admin\UsersController@updateAppleAccount');



// return url for video ads
Route::get('/v1/getvideolink', 'API\SwiftApiController@getvideolink');

// getting all category data
Route::get('/v1/categories', 'API\SwiftApiController@getAllcategories');

// getting all vendors data by categoryId
Route::get('/v1/vendros', 'API\SwiftApiController@getAllvendors');

// getting discount lists by vendor Id
Route::get('/v1/getDiscountlists', 'API\SwiftApiController@getDiscountlists');

// getting discount detail one item by Id
Route::get('/v1/getdetaildiscountbyid', 'API\SwiftApiController@getDetaildiscountById');

//register with facebook
Route::get('/v1/redirectfb', 'Auth\LoginController@redirectToProviderfacebook');
Route::get('/v1/callbackfb', 'Auth\LoginController@handleProviderCallbackfacebook');

//login with google
Route::get('/v1/redirect', 'Auth\LoginController@redirectToProvidergoogle');
Route::get('/v1/callback', 'Auth\LoginController@handleProviderCallbackgoogle');


// putting review data by API
Route::POST('/v1/putReviewsbyAPI', 'API\SwiftApiController@putReviewsbyAPI');

// stripe
Route::POST('/v1/stripe', 'API\SwiftApiController@stripePost');