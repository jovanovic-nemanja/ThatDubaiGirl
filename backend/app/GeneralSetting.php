<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class GeneralSetting extends Model
{
    public $fillable = ['site_name', 'app_name', 'site_title', 'site_subtitle', 'site_desc', 'site_footer'];
}
