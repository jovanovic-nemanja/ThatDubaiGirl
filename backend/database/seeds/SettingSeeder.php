<?php

use Illuminate\Database\Seeder;
use App\GeneralSetting;
use App\LocalizationSetting;

class SettingSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        GeneralSetting::create([
        	'id' => 1,
        	'site_name' => 'Dubai Girl',
            'app_name' => 'Dubai Girl',
        	'site_title' => 'Dubai Girl',
        	'site_subtitle' => 'Your Awesome Marketplace',
        	'site_desc' => 'Buy . Sell . Admin',
            'site_footer' => '© Copyright 2020 - City of UAE Dubai. All rights reserved.'
        ]);

        LocalizationSetting::create([
            'id' => 1,
            'language' => 'aed',
            'currency' => 'AED',
        ]);
    }
}
