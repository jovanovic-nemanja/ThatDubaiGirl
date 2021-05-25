<?php

namespace App;

use App\Reviews;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Storage;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'username', 'email', 'photo', 'instagram_id', 'facebook_id', 'google_id', 'apple_id', 'password', 'address', 'birthday', 'sign_date', 'block', 'email_verified_at', 'remarks'
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public function roles() {
    	return $this->belongsToMany('App\Role');
    }

    public function hasRole($role_name) {
        foreach ($this->roles()->get() as $role)
        {
            if ($role->name == $role_name)
            {
                return true;
            }
        }

        return false;
    }

    public function getBlockstatus($id) {
        if (@$id) {
            if ($id == 0) { //normal status
                $result = "Normal";
            }else if ($id == 1) {   //user blocked status
                $result = "Blocked";
            }
        }else{
            $result = "Normal";
        }

        return $result;
    }

    public function getUsername($id) {
        if (@$id) {
            $user = User::where('id', $id)->first();
            $name = $user->username;
        }

        return $name;
    }

    /**
    * @param user_id
    * This is a feature to upload a profile logo
    */
    public static function upload_photo($user_id, $existings = null) {
        if(!request()->hasFile('photo')) {
            return false;
        }

        Storage::disk('public_local')->put('uploads/', request()->file('photo'));

        self::save_logo_img($user_id, request()->file('photo'));
    }

    /**
    * file upload
    * @param userid and photo file
    * @return boolean true or false
    * @since 2020-10-16
    * @author Nemanja
    */
    public static function save_logo_img($user_id, $image) {
        $user = User::where('id', $user_id)->first();

        if($user) {
            Storage::disk('public_local')->delete('uploads/', $user->photo);
            $user->photo = $image->hashName();
            $user->update();
        }
    }

    /**
    * generate Unique user ID
    * @param user table id
    * @return Unique ID
    * @since 2020-12-12
    * @author Nemanja
    */
    public static function generateuserUniqueID($id)
    {
        if (@$id) {
            $record = User::where('id', $id)->first();
            if (@$record) {
                $record->userUniqueId = "7" . $id;
                $record->update();
            }
        }

        return true;
    }
}
