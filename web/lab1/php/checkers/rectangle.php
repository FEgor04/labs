<?php
include_once 'checkers/interface.php';
class RectangleHitChecker implements HitChecker {

    private $r;

    public function __construct($r)
    {
        $this->r = $r;
    }


    public function checkHit($x, $y)
    {
        if ($x <= 0 && $y <= 0) {
            return (-$this->r / 2 <= $x && $x <= 0) && (-$this->r <= $y && $y <= 0);
        }
    }
}
?>
