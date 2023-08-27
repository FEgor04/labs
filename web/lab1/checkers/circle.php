<?php
include_once 'checkers/interface.php';
include_once 'vector.php';
class CircleHitChecker implements HitChecker
{
    private $r;

    public function __construct($r)
    {
        $this->r = $r;
    }

    public function checkHit($x, $y)
    {
        if ($x <= 0 && $y >= 0) { // Point is in II quarter
            return $x * $x + $y * $y <= $this->r * $this->r;
        }
        return false;
    }
}
?>