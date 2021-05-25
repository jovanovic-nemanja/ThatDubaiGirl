<?php

namespace App\Http\Controllers\Admin;

use Mail;
use App\User;
use App\Role;
use App\Vendors;
use App\RoleUser;
use App\Emailverify;

use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Validator;

class UsersController extends Controller
{
    public function __construct(){
        $this->middleware(['auth', 'admin'])->except(['store', 'loginUser', 'logout', 'emailverify', 'validateCode', 'forgotpassword', 'resetpwd', 'resetUserpassword', 'updateAccount', 'loginUserwithApple', 'loginUserwithGoogle', 'updateAppleAccount']);
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $users = User::where('username', '!=', 'Admin')->get();
        return view('admin.users.index', compact('users'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('admin.users.create');
    }

    /**
     * Swift API : User emailverify by iOS mobile.
     *
     * @since 2020-11-16
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function emailverify(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|string|email|max:255|unique:users'
        ]);

        if ($validator->fails()) {
            $messages = $validator->messages();

            //pass validator errors as errors object for ajax response
            return response()->json(['status' => "failed", 'msg' => $messages->first()]);
        }

        DB::beginTransaction();

        $str = rand(100000, 999999);
        $data = [];
        $data['name'] = 'Welcome User,';
        $data['body'] = 'Hello! Welcome to That Dubai Girl. Thank you for registering with us. To complete your sign up process please verify your email address by entering the following code (' . $str . ') on the home screen.';

        $useremail = $request['email'];
        $username = 'That Dubai Girl';
        $subject = "Verify your email for That Dubai Girl";

        try {
            Mail::send('frontend.mail.mail', $data, function($message) use ($username, $useremail, $subject) {
                $message->to($useremail, $username)->subject($subject);
                $message->from('solaris.dubai@gmail.com', 'Administrator');
            });

            $verifyuser = Emailverify::where('email', $useremail)->first();
            if (@$verifyuser) {
                $verifyuser->verify_code = $str;
                $verifyuser->update();
            }else{
                $verifyuser = Emailverify::create([
                    'email' => $request['email'],
                    'verify_code' => $str,
                ]);
            }

            $result = $verifyuser['email'];

            DB::commit();
        } catch (\Exception $e) {
            DB::rollback();

            throw $e;
        }  

        return response()->json(['status' => "success", 'data' => $result, 'msg' => 'Successfully sent now. Please check a code in your email.']);
    }

    /**
     * Swift API : User forgot password.
     *
     * @since 2020-12-17
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function forgotpassword(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|string|email|exists:users'
        ]);

        if ($validator->fails()) {
            $messages = $validator->messages();

            //pass validator errors as errors object for ajax response
            return response()->json(['status' => "failed", 'msg' => $messages->first()]);
        }

        DB::beginTransaction();

        $token = substr("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", mt_rand(0, 51), 1).substr(md5(time()), 1);

        $data = [];
        $data['name'] = 'User!';
        $data['resetLink'] = env('APP_URL') . 'users/resetpwd/' . $token;
        $data['body'] = 'You are receiving this email because we received a password reset request for your account.';
        $data['pre_footer'] = 'If you did not request a password reset, no further action is required. <br> Regards, <br> MamboDubai';
        $data['footer'] = 'If you’re having trouble clicking the "Reset Password" button, copy and paste the URL below <br> into your web browser: <a href="' . $data['resetLink'] . '" target="_blank">' . $data['resetLink'] . '</a>';
        
        // $data['email'] = $request['email'];

        $useremail = $request['email'];
        $username = 'That Dubai Girl';
        $subject = "That Dubai Girl : Reset Password";

        try {
            Mail::send('frontend.mail.mail_forgotpassword', $data, function($message) use ($username, $useremail, $subject) {
                $message->to($useremail, $username)->subject($subject);
                $message->from('mambodubai@solaris.com', 'Administrator');
            });

            DB::commit();
        } catch (\Exception $e) {
            DB::rollback();

            throw $e;
        }  

        return response()->json(['status' => "success", 'msg' => 'Successfully sent now. Please check your inbox.']);
    }

    /**
     * Resource page render reset password.
     *
     * @since 2020-12-17
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function resetpwd($token)
    {
        return view('admin.users.reset', compact('token'));
    }

    /**
     * Swift API : Reset password.
     *
     * @since 2020-12-17
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function resetUserpassword(Request $request)
    {
        $this->validate(request(), [
            'email' => 'required|email|exists:users',
            'password' => 'required|min:6|confirmed'
        ]);

        $user = User::where('email', $request->email)->first();
        if (@$user) {
            $user->password = Hash::make(request('password'));
            $user->save();
        }
            
        return redirect()->route('users.resetpwd', $request->_token)->with('flash', 'Password has been successfully reset.');
    }

    /**
     * Swift API : Update user account information.
     *
     * @since 2020-12-17
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function updateAccount(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required'
        ]);

        if ($validator->fails()) {
            $messages = $validator->messages();

            //pass validator errors as errors object for ajax response
            return response()->json(['status' => "failed", 'msg' => $messages->first()]);
        }

        $user = User::where('email', $request->email)->first();
        if (@$user) {
            // if (@$request->password) {
            //     $user->password = Hash::make($request->password);
            // }
            if (@$request->birthday) {
                $user->birthday = $request->birthday;
            }
            if (@$request->username) {
                $user->username = $request->username;
            }
            if (@$request->photo) {
                $user->photo = $request->photo;
            }
            if (@$request->address) {
                $user->address = $request->address;
            }

            $user->save();

            User::generateuserUniqueID($user->id);
        }

        if (@$request->photo) {
            User::upload_photo($user->id);
        }

        $result = [];
        $result = User::where('id', $user->id)->first();
            
        return response()->json(['status' => "success", 'data' => $result, 'msg' => 'Successfully updated your account information.']);
    }

    /**
     * Swift API : Update user account information by apple.
     *
     * @since 2021-01-14
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function updateAppleAccount(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'apple_id' => 'required'
        ]);

        if ($validator->fails()) {
            $messages = $validator->messages();

            //pass validator errors as errors object for ajax response
            return response()->json(['status' => "failed", 'msg' => $messages->first()]);
        }

        $user = User::where('apple_id', $request->apple_id)->first();
        if (@$user) {
            // if (@$request->password) {
            //     $user->password = Hash::make($request->password);
            // }
            if (@$request->birthday) {
                $user->birthday = $request->birthday;
            }
            if (@$request->username) {
                $user->username = $request->username;
            }
            if (@$request->photo) {
                $user->photo = $request->photo;
            }
            if (@$request->address) {
                $user->address = $request->address;
            }

            $user->save();

            User::generateuserUniqueID($user->id);
        }

        if (@$request->photo) {
            User::upload_photo($user->id);
        }

        $result = [];
        $result = User::where('id', $user->id)->first();
            
        return response()->json(['status' => "success", 'data' => $result, 'msg' => 'Successfully updated your account information.']);
    }

    /**
     * Swift API : validate verify code.
     *
     * @since 2020-11-16
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function validateCode(Request $request)
    {
        $useremail = $request['email'];
        $verify_codes = $request['code'];
        $validate = Emailverify::where('email', $useremail)->first();

        if (@$validate) {
            if ($validate->verify_code == $verify_codes) {
                return response()->json(['status' => "success", 'data' => $useremail, 'msg' => 'Successfully validated now.']);
            }else{
                $msg = "Your code is invalid. ";
                return response()->json(['status' => "failed", 'data' => $useremail, 'msg' => $msg]);
            }
        }else{
            $msg = "Not found your email address in our records. ";
            return response()->json(['status' => "failed", 'data' => $useremail, 'msg' => $msg]);
        }
    }

    /**
     * Swift API : User register by iOS mobile.
     *
     * @since 2020-11-16
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'username' => 'required|string|max:255',
            'email' => 'required|string|email|max:255', //|unique:users
            'password' => 'required|string|min:6|confirmed'
        ]);

        if(@$request->birthday) {
            $current_year = date('Y');
            $dt = date('Y', strtotime($request->birthday));
            $diff = $current_year - $dt;
            if($diff < 18) {   //18years-
                $msg = "Unfortunately, Your age is not 18 years over. You can't signup to our app now.";
                return response()->json(['status' => "failed", 'msg' => $msg]);    
            }
        }

        $path = env('APP_URL')."uploads/";

        if ($validator->fails()) {
            $messages = $validator->messages();

            //pass validator errors as errors object for ajax response
            return response()->json(['status' => "failed", 'msg' => $messages->first(), 'path' => $path]);
        }

        DB::beginTransaction();

        try {
            $user = User::create([
                'username' => $request['username'],
                'instagram_id' => @$request['instagram_id'],
                'birthday' => @$request['birthday'],
                'email' => $request['email'],
                'photo' => @$request['photo'],
                'block' => 0,
                'password' => Hash::make($request['password']),
                'address' => @$request['address'],
                'remarks' => @$request['remarks'],
                'sign_date' => date('Y-m-d h:i:s'),
            ]);

            User::generateuserUniqueID($user->id);

            RoleUser::create([
                'user_id' => $user->id,
                'role_id' => 3,
            ]);

            if (@$request->photo) {
                User::upload_photo($user->id);
            }

            $result = [];
            $result = User::where('id', $user->id)->first();

            DB::commit();
        } catch (\Exception $e) {
            DB::rollback();

            throw $e;
        }  

        return response()->json(['status' => "success", 'data' => $result, 'msg' => 'Successfully registered.', 'path' => $path]);
    }

    /**
     * Swift API : User login by iOS mobile.
     *
     * @since 2020-11-16
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function loginUser(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|string|email',
            'password' => 'required|string'
        ]);

        if ($validator->fails()) {
            $messages = $validator->messages();

            //pass validator errors as errors object for ajax response
            return response()->json(['status' => "failed", 'msg' => $messages->first()]);
        }

        $credentials = request(['email', 'password']);

        if (!Auth::attempt($credentials))
            return response()->json([
                'status' => "failed",
                'msg' => 'Unauthorized Access, please confirm credentials or verify your email.'
            ]);

        $user = $request->user();
        
        return response()->json(['status' => 'success', 'data' => $user, 'msg' => 'Successfully Logged In.']);
    }

    /**
     * Swift API : User login by apple device.
     *
     * @since 2020-11-16
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function loginUserwithApple(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'apple_id' => 'required|string'
        ]);

        if ($validator->fails()) {
            $messages = $validator->messages();

            //pass validator errors as errors object for ajax response
            return response()->json(['status' => "failed", 'msg' => $messages->first()]);
        }

        $apple_id = $request->apple_id;
        $user = User::where('apple_id', $apple_id)->first();
        $result = [];

        if (!$user) {   //register
            $user = User::create([
                'block' => 0,
                'apple_id' => $request['apple_id'],
                'email' => @$request['user_mail'],
                'username' => @$request['user_name'],
                'sign_date' => date('Y-m-d h:i:s'),
            ]);

            User::generateuserUniqueID($user->id);

            RoleUser::create([
                'user_id' => $user->id,
                'role_id' => 3,
            ]);
            
            $result = User::where('id', $user->id)->first();
            $msg = 'Successfully Logged In.';
            $newUser = 1;
        }else{
            $result = $user;
            $msg = 'Successfully Logged In.';
            $newUser = 0;
        }

        return response()->json(['status' => 'success', 'data' => $result, 'msg' => $msg, 'isnewUser' => $newUser]);
    }

    /**
     * Swift API : User login by Google.
     *
     * @since 2021-02-25
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function loginUserwithGoogle(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'google_id' => 'required|string'
        ]);

        if ($validator->fails()) {
            $messages = $validator->messages();

            //pass validator errors as errors object for ajax response
            return response()->json(['status' => "failed", 'msg' => $messages->first()]);
        }

        $google_id = $request->google_id;
        $user = User::where('google_id', $google_id)->first();
        $user_by_email = User::where('email', $request->user_mail)->first();
        $result = [];

        if (!$user && !$user_by_email) {   //register
            $user = User::create([
                'block' => 0,
                'google_id' => $request['google_id'],
                'email' => @$request['user_mail'],
                'username' => @$request['user_name'],
                'sign_date' => date('Y-m-d h:i:s'),
            ]);

            User::generateuserUniqueID($user->id);

            RoleUser::create([
                'user_id' => $user->id,
                'role_id' => 3,
            ]);
            
            $result = User::where('id', $user->id)->first();
            $msg = 'Successfully Logged In.';
            $newUser = 1;
        } if ($user) {
            $result = $user;
            $msg = 'Successfully Logged In.';
            $newUser = 0;
        } if ($user_by_email) {
            $result = $user_by_email;
            $msg = 'Successfully Logged In.';
            $newUser = 0;
        }

        return response()->json(['status' => 'success', 'data' => $result, 'msg' => $msg, 'isnewUser' => $newUser]);
    }
    
    /**
     * Swift API : User logout by iOS mobile.
     *
     * @since 2020-11-16
     * @author Nemanja
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */  
    public function logout(Request $request)
    {
        Auth::logout();

        return response()->json([
            'success' => true,
            'message' => 'Successfully logged out'
        ]);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $user = User::where('id', $id)->first();

        return view('admin.users.edit', compact('user'));
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $record = User::where('id', $id)->first();
        if (@$record) {
            $record->username = @$request->username;
            $record->email = @$request->email;
            if (@$request->photo) {
                $record->photo = $request->photo;
            }

            $record->block = $request->block;
            $record->birthday = @$request->birthday;
            $record->address = @$request->address;
            $record->remarks = @$request->remarks;

            $record->update();
        }

        User::generateuserUniqueID($record->id);

        if (@$request->photo) {
            User::upload_photo($record->id);
        }

        return redirect()->route('users.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $rec = RoleUser::where('user_id', $id)->delete();
        $record = User::where('id', $id)->delete();
        
        return redirect()->route('users.index');
    }
}
