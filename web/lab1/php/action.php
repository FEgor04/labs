<?php
require_once 'checkers/circle.php';
require_once 'checkers/rectangle.php';
require_once 'checkers/triangle.php';
require_once 'checkers/interface.php';

class HitResponse {
    public $hit;
    public $executionTime;
    public $currentTime;
    public $x;
    public $y;
    public $r;

    public function __construct($hit, $executionTime, $currentTime, $x, $y, $r)
    {
        $this->hit = $hit;
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

if(!(is_numeric($x) && is_numeric($y) && is_numeric($r))) {
    echo "Bad request\r\n";
    http_response_code(400);
    return 400;
}

if($x < -5 || $x > 3) {
    echo "Bad request: x should be in [-5; 3]\r\n";
    http_response_code(400);
    return 400;
}

if($y < -3 || $y > 3) {
    echo "Bad request: y should be in [-3; 3]\r\n";
    http_response_code(400);
    return 400;
}

if($r < 1 || $r > 5) {
    echo "Bad request: r should be in [1; 5]\r\n";
    http_response_code(400);
    return 400;
}

$startTime = hrtime(true);
$hit = checkHit($x, $y, $r);
$endTime = hrtime(true);
$executionTime = $endTime - $startTime;

// hrtime is not aligned with system time, therefore we need to use simple time() function
$response = new HitResponse($hit, $executionTime, time(), $x, $y, $r);

echo json_encode($response) . "\r\n";


