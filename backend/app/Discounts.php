<?php

namespace App;

use App\Vendors;
use App\Category;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Storage;
use Illuminate\Database\Eloquent\Model;

class Discounts extends Model
{
	public $table = "discounts";

    public $fillable = ['title', 'description', 'category_id', 'discount_photo', 'vendor_id', 'coupon', 'sign_date', 'status'];

    public static function getVendorInformationByID($id)
    {
    	if (@$id) {
    		$result = Vendors::where('id', $id)->first();
    	}else{
    		$result = "";
    	}

    	return $result;
    }

    /**
    * @param discount_id
    * @since 2020-12-08
    * This is a feature to upload a profile logo
    */
    public static function upload_photo($discount_id, $existings = null) {
        if(!request()->hasFile('discount_photo')) {
            return false;
        }

        Storage::disk('public_local')->put('uploads/', request()->file('discount_photo'));

        self::save_logo_img($discount_id, request()->file('discount_photo'));
    }

    /**
    * file upload
    * @param userid and photo file
    * @return boolean true or false
    * @since 2020-12-08
    * @author Nemanja
    */
    public static function save_logo_img($discount_id, $image) {
        $discount = Discounts::where('id', $discount_id)->first();

        if($discount) {
            Storage::disk('public_local')->delete('uploads/', $discount->discount_photo);
            $discount->discount_photo = $image->hashName();
            $discount->update();
        }
    }

    /**
    * get status name as string by status id
    * @param status id as integer
    * @return status as string
    * @since 2020-12-19
    * @author Nemanja
    */
    public static function getStatusBystatusID($status)
    {
        switch ($status) {
            case '1':
                $result = "General Discount";
                break;
            case '2':
                $result = "Featured Discount";
                break;

            default:
                $result = "General Discount";
                break;
        }

        return $result;
    }

    public static function getCategoryNameByID($cateid)
    {
        if (@$cateid) {
            $category = Category::where('id', $cateid)->first();
            if (@$category) {
                $arr = $category->category_name;
            }else{
                $arr = '';
            }
        }else{
            $arr = "";
        }

        return $arr;
    }
}
