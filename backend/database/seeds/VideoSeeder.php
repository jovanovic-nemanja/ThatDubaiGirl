<?php

use Illuminate\Database\Seeder;
use App\Video;

class VideoSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Video::create([
	        'link' => 'https://youtu.be/VSo41Y9i2Ug',
            'active' => 1,
		]);
    }
}
