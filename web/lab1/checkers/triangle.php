<?php
include_once 'checkers/interface.php';
include_once 'vector.php';
class TrinangleHitChecker implements HitChecker {
        private $r;

        public function __construct($r) {
            $this->r = $r;
        }

        public function checkHit($x, $y)
    {
        $p1 = new Vector2D($this->r / 2, 0); // A
        $p2 = new Vector2D(0, 0); // B
        $p3 = new Vector2D(0, -$this->r); // C

        $p = new Vector2D($x, $y); // P

        $v1 = $p2->minus($p1); // B - A = AB
        $v2 = $p3->minus($p2); // C - B = BC
        $v3 = $p1->minus($p3); // A - C = CA

        $w1 = $p->minus($p1); // P - A = AP
        $w2 = $p->minus($p2); // P - B = BP
        $w3 = $p->minus($p3);

        $prod1 = $w1->product($v1);
        $prod2 = $w2->product($v2);
        $prod3 = $w3->product($v3);

        if (($prod1 >= 0 && $prod2 >= 0 && $prod3 >= 0) || ($prod1 <= 0 && $prod2 <= 0 && $prod3 <= 0)) {
            return true;
        }
        return false;
    }
}
?>