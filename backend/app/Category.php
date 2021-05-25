<?php

namespace App;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Storage;
use Illuminate\Database\Eloquent\Model;

class Category extends Model
{

    ///////////////////////////// sub category part ///////////////////////////////////

    public $fillable = ['category_name', 'category_photo', 'parent', 'slug', 'sign_date'];

    ///////////////////////////// sub category part ///////////////////////////////////

    public function products(){
        return $this->hasMany('App\Product');
    }

    public function scopeSearchId($query, $param) {
    	$category = $query->where('slug', $param)
    					  ->orWhere('name', 'like', '%'.$param.'%')
    					  ->first();

    	return $category ? $category->id : false;
    }

    /**
    * @param category_id
    * This is a feature to upload a profile logo
    */
    public static function upload_photo($category_id, $existings = null) {
        if(!request()->hasFile('category_photo')) {
            return false;
        }

        Storage::disk('public_local')->put('uploads/', request()->file('category_photo'));

        self::save_logo_img($category_id, request()->file('category_photo'));
    }

    /**
    * file upload
    * @param userid and photo file
    * @return boolean true or false
    * @since 2020-12-08
    * @author Nemanja
    */
    public static function save_logo_img($category_id, $image) {
        $category = Category::where('id', $category_id)->first();

        if($category) {
            Storage::disk('public_local')->delete('uploads/', $category->category_photo);
            $category->category_photo = $image->hashName();
            $category->update();
        }
    }
}
