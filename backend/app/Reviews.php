<?php

namespace App;

use App\User;
use App\Discounts;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Database\Eloquent\Model;

class Reviews extends Model
{
    public $table = "reviews";

    public $fillable = ['putter', 'discount_id', 'mark', 'comments', 'put_date'];

    /**
    * @param discount id of reviews table
    * @author Nemanja
    * @since 2021-01-22
    * @return discount information
    */
    public static function getDiscountInformationByID($review_discount_id)
    {
    	if (@$review_discount_id) {
		    $discount =  Discounts::where('id', $review_discount_id)->first();
    	}else{
    		$discount = [];
    	}

    	return $discount;
    }

    /**
    * @param putter id of reviews table
    * @author Nemanja
    * @since 2021-01-22
    * @return user information
    */
    public static function getUserInformationByID($review_putter_id)
    {
    	if (@$review_putter_id) {
		    $user =  User::where('id', $review_putter_id)->first();
    	}else{
    		$user = [];
    	}

    	return $user;
    }
}
