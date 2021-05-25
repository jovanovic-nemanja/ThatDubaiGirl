<?php

namespace App\Http\Controllers\Admin;

use App\User;
use App\Vendors;
use App\Category;
use App\Discounts;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Storage;

class DiscountsController extends Controller
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
        $discounts = Discounts::all();
        return view('admin.discounts.index', compact('discounts'));
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
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function creatediscounts($id)
    {
        $categories = Category::all();

        return view('admin.discounts.create', compact('id', 'categories'));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $this->validate(request(), [
            'title' => 'required',
            'description' => 'required',
            'category_id' => 'required',
            'discount_photo' => 'required',
            // 'coupon' => 'string|max:32',
            'vendor_id' => 'required'
        ]);

        DB::beginTransaction();

        try {
            if (@$request['title'] && @$request['description']) {
                $discount = Discounts::create([
                    'title' => $request['title'],
                    'description' => $request['description'],
                    'discount_photo' => $request['discount_photo'],
                    'category_id' => $request['category_id'],
                    'vendor_id' => $request['vendor_id'],
                    'coupon' => @$request['coupon'],
                    'status' => 1,
                    'sign_date' => date('Y-m-d h:i:s'),
                ]);
            }

            Discounts::upload_photo($discount->id);

            DB::commit();
        } catch (\Exception $e) {
            DB::rollback();

            throw $e;
        }  

        return redirect()->route('discounts.index')->with('flash', 'Successfully added Discount.');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $categories = Category::all();
        $discount = Discounts::where('id', $id)->first();
        $size = Storage::disk('public_local')->size('uploads/'.$discount->discount_photo);

        return view('admin.discounts.edit', compact('discount', 'categories', 'size'));
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
            'title' => 'required',
            'description' => 'required',
            'category_id' => 'required',
            // 'coupon' => 'string|max:32',
            'vendor_id' => 'required'
        ]);

        $record = Discounts::where('id', $id)->first();
        if (@$record) {
            $record->title = $request->title;
            $record->description = $request->description;
            $record->category_id = $request->category_id;
            if (@$request->discount_photo) {
                $record->discount_photo = $request->discount_photo;
            }
            // $record->status = 1;
            $record->coupon = $request->coupon;

            $record->update();
        }

        if (@$request->discount_photo) {
            Discounts::upload_photo($record->id);
        }
        
        return redirect()->route('discounts.index');
    }

    /**
     * Update the status from 1 to 2.
     * set featured discount by admin
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @author Nemanja
     * @since 2020-12-19
     * @return \Illuminate\Http\Response
     */
    public function setfeature(Request $request)
    {
        $this->validate(request(), [
            'discountID' => 'required'
        ]);

        $record = Discounts::where('id', $request->discountID)->first();
        if (@$record) {
            $record->status = 2;
            
            $record->update();
        }
        
        return redirect()->route('discounts.index');
    }

    /**
     * Update the status from 2 to 1.
     * reset feature discount by admin
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @author Nemanja
     * @since 2020-12-19
     * @return \Illuminate\Http\Response
     */
    public function resetfeatured(Request $request)
    {
        $this->validate(request(), [
            'discountID' => 'required'
        ]);

        $record = Discounts::where('id', $request->discountID)->first();
        if (@$record) {
            $record->status = 1;
            
            $record->update();
        }
        
        return redirect()->route('discounts.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $record = Discounts::where('id', $id)->delete();
        
        return redirect()->route('discounts.index');
    }
}
