<footer class="footer container">
    <br>
    <div class="d-sm-flex justify-content-center justify-content-sm-between">
        <?php
        $date = getdate();
        $year = $date['year'];
        ?>

        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Copyright © <?= $year; ?> </span>
        <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center"> <a href="https://solarisdubai.com/">Powered by Solaris</a></span>
    </div>
</footer>
