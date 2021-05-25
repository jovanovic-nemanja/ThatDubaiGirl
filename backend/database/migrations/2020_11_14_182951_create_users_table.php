<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->increments('id');
            $table->string('username')->nullable();
            $table->string('email')->unique()->nullable();
            $table->text('photo')->nullable();

            // Cached from Instagram
            $table->string('instagram_id')->nullable();
            // Cached from Facebook
            $table->string('facebook_id')->nullable();
            // Cached from Google
            $table->string('google_id')->nullable();
            // Cached from Apple
            $table->string('apple_id')->nullable();

            $table->integer('email_verified_at')->nullable();
            $table->string('password')->nullable();
            $table->integer('block')->defaultValue(0)->nullable();
            $table->date('birthday')->nullable();
            $table->string('address')->nullable();
            $table->datetime('sign_date');
            $table->string('remarks')->nullable();
            $table->rememberToken();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}
