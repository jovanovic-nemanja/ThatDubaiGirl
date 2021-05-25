<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<body>
    <div style="padding-left: 10%; padding-right: 10%;">
        <img src="https://tdguae.com/images/logo1.png" alt="" style="width: 130px;">
        <hr/>
        <h1 style="color: #476B91;">Hi, {{ $name }}</h1>
        <h4>{!! $body !!}</h4>

        <br>
        <a href="{{ $resetLink }}" style="text-decoration: none; background-color: #3097d1; border-top: 10px solid #3097d1; border-right: 18px solid #3097d1; border-bottom: 10px solid #3097d1; border-left: 18px solid #3097d1; box-sizing: border-box; border-radius: 3px; color: #fff;">Reset Password</a>
        <h4>{!! $pre_footer !!}</h4>

        <br>
        <hr>
        <p>{!! $footer !!}</p>
    </div>
</body>

</html>