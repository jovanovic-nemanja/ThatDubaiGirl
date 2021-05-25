<?php

namespace App\Http\Controllers\Auth;

use Session;
use App\User;
use App\Role;
use Socialite;
use App\Category;
use App\RoleUser;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;
use Illuminate\Foundation\Auth\AuthenticatesUsers;

class LoginController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles authenticating users for the application and
    | redirecting them to your home screen. The controller uses a trait
    | to conveniently provide its functionality to your applications.
    |
    */

    use AuthenticatesUsers;

    /**
     * Where to redirect users after login.
     *
     * @var string
     */
    protected $redirectTo = '/admin/vendor';

    public function redirectTo(){
        // User role
        if (auth()->user()->hasRole('admin')) {
            return '/admin/vendor';
        }
    }

    /**
      * Redirect the user to the Google authentication page.
      *
      * @return \Illuminate\Http\Response
      */
    public function redirectToProvidergoogle()
    {
        return Socialite::driver('google')->redirect();
    }

    /**
     * Obtain the user information from Google.
     *
     * @return \Illuminate\Http\Response
     */
    public function handleProviderCallbackgoogle()
    {
        try {
            $user = Socialite::driver('google')->user();
        } catch (\Exception $e) {
            return response()->json(['status' => "error", 'msg' => "Failed Google Redirected."]);
        }

        // check if they're an existing user
        $existingUser = User::where('email', $user->email)->first();
        if($existingUser){
            // log them in
            auth()->login($existingUser, true);
        } else {
            // create a new user
            $newUser                  = new User;
            $newUser->username        = $user->name;
            $newUser->email           = $user->email;
            $newUser->sign_date       = date('Y-m-d h:i:s');
            $newUser->google_id     = $user->id;
            $newUser->save();

            RoleUser::create([
                'user_id' => $newUser->id,
                'role_id' => 3,
            ]);

            auth()->login($newUser, true);
        }
        $result = Category::all();

        return response()->json(['status' => "success", 'msg' => "Successfully Logged In.", "data" => $result]);
    }

    /**
      * Redirect the user to the Facebook authentication page.
      *
      * @return \Illuminate\Http\Response
      */
    public function redirectToProviderfacebook()
    {
        return Socialite::driver('facebook')->redirect();
    }

    /**
     * Obtain the user information from Google.
     *
     * @return \Illuminate\Http\Response
     */
    public function handleProviderCallbackfacebook()
    {
        try {
            $user = Socialite::driver('facebook')->user();
        } catch (\Exception $e) {
            return response()->json(['status' => "error", 'msg' => "Failed Facebook Redirected."]);
        }

        // check if they're an existing user
        $existingUser = User::where('email', $user->email)->first();
        if($existingUser){
            // log them in
            auth()->login($existingUser, true);
        } else {
            // create a new user
            $newUser                  = new User;
            $newUser->username        = $user->name;
            $newUser->email           = $user->email;
            $newUser->sign_date       = date('Y-m-d h:i:s');
            $newUser->facebook_id     = $user->id;
            $newUser->save();

            RoleUser::create([
                'user_id' => $newUser->id,
                'role_id' => 3,
            ]);

            auth()->login($newUser, true);
        }
        $result = Category::all();

        return response()->json(['status' => "success", 'msg' => "Successfully Logged In.", "data" => $result]);
    }

    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest')->except('logout');
    }
}
