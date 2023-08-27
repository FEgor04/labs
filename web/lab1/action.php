<?php
require_once 'checkers/circle.php';
require_once 'checkers/rectangle.php';
require_once 'checkers/triangle.php';
require_once 'checkers/interface.php';

class HitResponse {
    public $success;
    public $executionTime;
    public $currentTime;
    public $x;
    public $y;
    public $r;

    public function __construct($success, $executionTime, $currentTime, $x, $y, $r)
    {
        $this->success = $success;
        $this->executionTime = $executionTime;
        $this->currentTime = $currentTime;
        $this->x = $x;
        $this->y = $y;
        $this->r = $r;
    } 
}

function checkHit($x, $y, $r)
{
    $checkers = [new RectangleHitChecker($r), new CircleHitChecker($r), new TrinangleHitChecker($r)];
    foreach ($checkers as $kek) {
        if($kek->checkHit($x, $y)) {
            return true;
        }
    }
    return false;
}


$x = $_GET["x"];
$y = $_GET["y"];
$r = $_GET["r"];


$startTime = hrtime(true);
$success = checkHit($x, $y, $r);
$endTime = hrtime(true);
$executionTime = $endTime - $startTime;

// hrtime is not aligned with system time, therefore we need to use simple time() function
$response = new HitResponse($success, $executionTime, time(), $x, $y, $r);

echo json_encode($response);


