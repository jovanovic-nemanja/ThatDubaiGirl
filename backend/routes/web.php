<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');


Route::get('/', 'Frontend\HomeController@index')->name('home');


Route::get('/admin', 'Admin\GeneralSettingsController@index')->name('dashboard.index');


Route::get('/admin/general', 'Admin\GeneralSettingsController@index')->name('admin.generalsetting');
Route::put('/admin/general/update/{generalsetting}', 'Admin\GeneralSettingsController@update')->name('admin.generalsetting.update');


Route::get('/admin/localization', 'Admin\LocalizationSettingsController@index')->name('admin.localizationsetting');
Route::put('/admin/localization/update/{localizationsetting}', 'Admin\LocalizationSettingsController@update')->name('admin.localizationsetting.update');


Route::resource('admin/category', 'Admin\CategoryController');
Route::get('/admin/category', 'Admin\CategoryController@index')->name('category.index');


Route::resource('admin/vendor', 'Admin\VendorsController');
Route::get('/admin/vendor', 'Admin\VendorsController@index')->name('vendor.index');


Route::resource('admin/users', 'Admin\UsersController');
Route::get('/admin/users', 'Admin\UsersController@index')->name('users.index');
Route::get('/users/resetpwd/{token}', 'Admin\UsersController@resetpwd')->name('users.resetpwd');
Route::POST('/users/resetUserpassword', 'Admin\UsersController@resetUserpassword')->name('users.resetUserpassword');


Route::resource('admin/discounts', 'Admin\DiscountsController');
Route::get('/admin/discounts', 'Admin\DiscountsController@index')->name('discounts.index');
Route::get('/admin/creatediscounts/{id}', 'Admin\DiscountsController@creatediscounts')->name('discounts.creatediscounts');
Route::POST('/admin/discounts/setfeature', 'Admin\DiscountsController@setfeature')->name('discounts.setfeature');
Route::POST('/admin/discounts/resetfeatured', 'Admin\DiscountsController@resetfeatured')->name('discounts.resetfeatured');



Route::resource('admin/video', 'Admin\VideoController');
Route::get('/admin/video', 'Admin\VideoController@index')->name('video.index');



Route::resource('admin/reviews', 'Admin\ReviewsController');
Route::get('/admin/reviews', 'Admin\ReviewsController@index')->name('reviews.index');



Route::get('/account', 'Frontend\AccountController@index')->name('account');
Route::get('/changepass', 'Frontend\AccountController@changepass')->name('changepass');
Route::put('/account/update', 'Frontend\AccountController@update')->name('account.update');
Route::put('/account/updatePassword', 'Frontend\AccountController@updatePassword')->name('account.updatePassword');