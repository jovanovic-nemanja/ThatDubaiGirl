<?php

namespace App\Http\Controllers\Admin;

use App\Reviews;
use App\User;
use App\Vendors;
use App\Category;
use App\Discounts;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Http\Controllers\Controller;

class ReviewsController extends Controller
{
    public function __construct(){
        $this->middleware(['auth', 'admin']);
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $reviews = Reviews::all();
        return view('admin.reviews.index', compact('reviews'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $review = Reviews::where("id", $id)->first();
        $discount = Reviews::getDiscountInformationByID($review->discount_id);
        $user = Reviews::getUserInformationByID($review->putter);

        return view('admin.reviews.edit', compact('discount', 'review', 'user'));
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $this->validate(request(), [
            'mark' => 'required',
            'comments' => 'required',
        ]);

        $record = Reviews::where('id', $id)->first();
        if (@$record) {
            $record->comments = @$request->comments;
            $record->mark = @$request->mark;
            $record->update();
        }
        
        return redirect()->route('reviews.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $record = Reviews::where('id', $id)->delete();
        
        return redirect()->route('reviews.index');
    }
}
