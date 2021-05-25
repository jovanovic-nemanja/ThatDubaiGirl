<?php

use Illuminate\Database\Seeder;
use App\User;
use App\Role;
use App\RoleUser;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
		User::create([
			'id'   => 1,
	        'username' => 'Admin',
	        'email' => 'admin@gmail.com',
            'email_verified_at' => 1,
            'password' => '$2y$10$vyj9SUowNZ9tVQWs.dWqzu7B5FXiJBwPC.0cN/OMaotbk32k5hMFC', // aaaa1234!!!!
            'birthday' => '1985-10-19',
            'block' => 0,
            'remember_token' => str_random(10),
            'address' => 'Dubai 11',
            'remarks' => 'administrator',
            'sign_date' => date('y-m-d h:m:s'),
		]);

        Role::create([
            'id' => 1,
            'name' => 'admin'
        ]);
        Role::create([
            'id' => 2,
            'name' => 'vendor'
        ]);
        Role::create([
            'id' => 3,
            'name' => 'buyer'
        ]);

        RoleUser::create([
            'id' => 1,
            'user_id' => 1,
            'role_id' => 1,
        ]);
    }
}
