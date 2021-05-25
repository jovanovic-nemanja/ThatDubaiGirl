<?php

namespace App\Http\Controllers\Admin;

use App\User;
use App\Video;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\DB;
use App\Http\Controllers\Controller;

class VideoController extends Controller
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
        $video = Video::first();
        return view('admin.video.index', compact('video'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('admin.video.create');
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
            'link' => 'required'
        ]);

        DB::beginTransaction();

        try {
            $video = Video::create([
                'link' => $request['link'],
                'active' => 1,
            ]);

            Video::upload_video($video->id);

            DB::commit();
        } catch (\Exception $e) {
            DB::rollback();

            throw $e;
        }  

        return redirect()->route('video.index')->with('flash', 'Successfully added Video.');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $video = Video::where('id', $id)->first();

        return view('admin.video.edit', compact('video'));
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
        $record = Video::where('id', $id)->first();
        if (@$record) {
            if (@$request->link) {
                $record->link = @$request->vendorname;

                $record->update();
            }
        }
        
        if (@$request->link) {
            Video::upload_video($record->id);
        }

        return redirect()->route('video.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $record = Video::where('id', $id)->delete();
        
        return redirect()->route('video.index');
    }
}
