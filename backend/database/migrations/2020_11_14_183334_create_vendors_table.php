<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateVendorsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('vendors', function (Blueprint $table) {
            $table->increments('id');

            $table->string('vendorname');
            $table->string('email');
            // $table->text('category_id');
            $table->string('phone');
            $table->integer('status')->defaultValue(0)->nullable();
            $table->string('location');
            $table->string('photo', '256')->nullable();

            // Cached from Instagram
            $table->string('instagram_id')->nullable();

            // Cached from Facebook
            $table->string('facebook_id')->nullable();

            // website link
            $table->text('website_link')->nullable();

            $table->datetime('sign_date');
            $table->string('remarks_vendor')->nullable();

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
        Schema::dropIfExists('vendors');
    }
}
