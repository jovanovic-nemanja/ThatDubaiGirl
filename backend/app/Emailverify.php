<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Emailverify extends Model
{
	public $table = 'email_verify';
	
    public $fillable = ['email', 'verify_code'];
}
