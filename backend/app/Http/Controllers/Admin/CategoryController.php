<?php

namespace App\Http\Controllers\Admin;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Category;
use Illuminate\Support\Facades\Storage;

class CategoryController extends Controller
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
        $categories = Category::all();
        return view('admin.category.index', compact('categories'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('admin.category.create');
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
            'category_name' => 'required',
            'category_photo' => 'required'
        ]);

        $category = Category::create([
            'category_name' => $request->category_name,
            'category_photo' => @$request->category_photo,
            'slug' => createSlug(request('category_name')),
            'sign_date' => date('y-m-d h:i:s'),
        ]);

        Category::upload_photo($category->id);

        return redirect()->route('category.index')->with('flash', 'Category has been successfully created');
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit(Category $category)
    {
        $size = Storage::disk('public_local')->size('uploads/'.$category->category_photo);
        
        return view('admin.category.edit', compact('category', 'size'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Category $category)
    {
        $category->category_name = $request->category_name;
        if (@$request->category_photo) {
            $category->category_photo = $request->category_photo;
        }
        $category->update();

        if (@$request->category_photo) {
            Category::upload_photo($category->id);
        }

        return redirect()->route('category.index')->with('flash', 'Category has successfully updated');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Category $category)
    {
        $category->delete();

        return redirect()->route('category.index')->with('flash', 'Category has successfully deleted');
    }
}
