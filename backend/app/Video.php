<?php

namespace App;

use App\Video;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Storage;
use Illuminate\Database\Eloquent\Model;

class Video extends Model
{
    public $table = 'video';
	
    public $fillable = ['link', 'active'];

    /**
    * @param video_id
    * This is a feature to upload a profile logo
    */
    public static function upload_video($video_id, $existings = null) {
        if(!request()->hasFile('link')) {
            return false;
        }

        Storage::disk('public_local')->put('uploads/', request()->file('link'));

        self::save_video($video_id, request()->file('link'));
    }

    /**
    * file upload
    * @param userid and photo file
    * @return boolean true or false
    * @since 2020-10-16
    * @author Nemanja
    */
    public static function save_video($video_id, $image) {
        $video = Video::where('id', $video_id)->first();

        if($video) {
            Storage::disk('public_local')->delete('uploads/', $video->link);
            $video->link = $image->hashName();
            $video->update();
        }
    }
}
