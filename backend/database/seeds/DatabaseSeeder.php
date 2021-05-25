<?php

use Illuminate\Database\Seeder;
use App\Category;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
     $this->call([
      UserSeeder::class
      ]);

     // Setting Seeder
     $this->call([
      SettingSeeder::class,
     ]);

     $this->call([
      VideoSeeder::class,
     ]);
    }
}
